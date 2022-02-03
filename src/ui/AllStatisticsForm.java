package ui;

import connection.DatabaseConnection;
import entities.*;
import table_models.*;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AllStatisticsForm extends JFrame{
    private JPanel mainPanel;
    private JComboBox league_comboBox;
    private JTabbedPane sql_tabedPane;
    private JLabel league_label;
    private JTable standings_table;
    private JButton goBack_button;
    private JTable bombardier_table;
    private JTable assistants_table;
    private JTable clean_sheets_table;
    private JTable yellow_cards_table;
    private JTable red_cards_table;
    private JLabel logo_label;
    private JPanel leaguePanel;
    private JPanel logoImagePanel;
    private JButton gelLeagueStat_button;

    private DefaultTableCellRenderer centerRenderer;

    public AllStatisticsForm(String windowTitle) {
        super(windowTitle);
        this.setContentPane(mainPanel);
        this.setBounds(350, 100, 850, 580);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setModelForLeagueBox();
        league_comboBox.setSelectedItem("Английская Премьер-лига");
        logo_label.setIcon(new ImageIcon("C:\\ForPictures\\apl.jpg"));
        centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment( JLabel.CENTER );
        createStandingsTable();
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                UIManager.put("OptionPane.yesButtonText", "Да");
                UIManager.put("OptionPane.noButtonText", "Нет");
                int result = JOptionPane.showConfirmDialog(
                        getContentPane(),
                        "Выйти из системы?",
                        "Окно подтверждения",
                        JOptionPane.YES_NO_OPTION);
                if (result == JOptionPane.YES_OPTION) {
                    dispose();
                    new SignInForm("Вход в \"Football Statistics\"");
                }
            }
        });
        goBack_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new UserMenuForm("Меню пользователя");
            }
        });
        league_comboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    Object item = e.getItem();
                    createStandingsTable();
                    if (item.toString().equals("Английская Премьер-лига")) {
                        logo_label.setIcon(new ImageIcon("C:\\ForPictures\\apl.jpg"));
                    }
                    else if (item.toString().equals("Бундеслига")) {
                        logo_label.setIcon(new ImageIcon("C:\\ForPictures\\bundesliga.jpg"));
                    }
                    else if (item.toString().equals("Ла Лига")) {
                        logo_label.setIcon(new ImageIcon("C:\\ForPictures\\laLiga.png"));
                    }
                    else if (item.toString().equals("Лига 1")) {
                        logo_label.setIcon(new ImageIcon("C:\\ForPictures\\ligue1.png"));
                    }
                    else if (item.toString().equals("Серия А")) {
                        logo_label.setIcon(new ImageIcon("C:\\ForPictures\\seriaA.jpg"));
                    }
                }
            }
        });
        this.setVisible(true);
    }

    private void setModelForLeagueBox()
    {
        DefaultComboBoxModel<String> comboBoxModel = new DefaultComboBoxModel<String>();
        try {
            ResultSet resultSet = DatabaseConnection.getStatement().executeQuery("SELECT LEAGUE_NAME FROM LEAGUE");
            while(resultSet.next())
            {
                comboBoxModel.addElement(resultSet.getString("LEAGUE_NAME"));
            }
            league_comboBox.setModel(comboBoxModel);
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private void createStandingsTable() {
        ArrayList<ClubStatistics> clubStatisticsArrayList = new ArrayList<ClubStatistics>();
        try {
            PreparedStatement preparedStatement = DatabaseConnection.getConnection().prepareStatement("SELECT * FROM CLUB_STATISTICS WHERE CLUB_NAME IN (SELECT CLUB_NAME FROM FOOTBALL_CLUB WHERE LEAGUE_NAME=?) ORDER BY TOURNAMENT_POSITION");
            preparedStatement.setString(1, league_comboBox.getSelectedItem().toString());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                clubStatisticsArrayList.add(new ClubStatistics(resultSet.getString("CLUB_NAME"),
                        resultSet.getInt("TOURNAMENT_POSITION"), resultSet.getInt("COUNT_OF_MATCHES_PLAYED"),
                        resultSet.getInt("WINS"), resultSet.getInt("DRAWS"), resultSet.getInt("LOSSES"),
                        resultSet.getInt("GOAL_FOR"), resultSet.getInt("GOAL_AGAINST"), resultSet.getInt("GOAL_DIFFERENCE"), resultSet.getInt("POINTS")));
            }
            StandingsTableModel standingsTableModel = new StandingsTableModel(clubStatisticsArrayList);
            standings_table.setModel(standingsTableModel);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        standings_table.setDefaultRenderer(String.class, centerRenderer);
        standings_table.setDefaultRenderer(Integer.class, centerRenderer);
        setResizableColumns(standings_table);
        setPreferredSizeForTable(standings_table);
        setColumnHints(standings_table);
        createBombardiersTable();
    }

    private void createBombardiersTable()
    {

        ArrayList<Bombardier> bombardiersArrayList = new ArrayList<Bombardier>();
        try {
            PreparedStatement preparedStatement = DatabaseConnection.getConnection().prepareStatement("SELECT top 10 " +
                    "PLAYER.NAME_OF_PLAYER, PLAYER.SURNAME_OF_PLAYER, PLAYER.CLUB_NAME, PLAYER.POSITION, PLAYER_STATISTICS.COUNT_OF_MATCHES, PLAYER_STATISTICS.GOALS FROM PLAYER " +
                    "INNER JOIN PLAYER_STATISTICS ON (PLAYER.PLAYER_ID = PLAYER_STATISTICS.PLAYER_ID) WHERE PLAYER.PLAYER_ID IN " +
                    "(SELECT PLAYER_ID FROM PLAYER WHERE CLUB_NAME IN (SELECT CLUB_NAME FROM FOOTBALL_CLUB WHERE LEAGUE_NAME=?)) ORDER BY PLAYER_STATISTICS.GOALS DESC, PLAYER_STATISTICS.COUNT_OF_MATCHES");
            preparedStatement.setString(1, league_comboBox.getSelectedItem().toString());
            ResultSet resultSet = preparedStatement.executeQuery();
            int prevCountOfGoals = 0;
            int place = 0;
            int difference = 1;
            while (resultSet.next()) {
                if (resultSet.getInt("GOALS") == prevCountOfGoals) {
                    difference++;
                }
                else {
                    place += difference;
                    difference = 1;
                }
                bombardiersArrayList.add(new Bombardier(place, resultSet.getString("SURNAME_OF_PLAYER"), resultSet.getString("NAME_OF_PLAYER"),
                        resultSet.getString("CLUB_NAME"), resultSet.getString("POSITION"),
                        resultSet.getInt("COUNT_OF_MATCHES"), resultSet.getInt("GOALS")));
                prevCountOfGoals = resultSet.getInt("GOALS");
            }
            BombardierTableModel bombardierTableModel = new BombardierTableModel(bombardiersArrayList);
            bombardier_table.setModel(bombardierTableModel);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        bombardier_table.setDefaultRenderer(String.class, centerRenderer);
        bombardier_table.setDefaultRenderer(Integer.class, centerRenderer);
        setResizableColumns(bombardier_table);
        setPreferredSizeForTable(bombardier_table);
        setColumnHints(bombardier_table);
        createAssistantsTable();
    }

    private void createAssistantsTable()
    {

        ArrayList<Assistant> assistantsArrayList = new ArrayList<Assistant>();
        try {
            PreparedStatement preparedStatement = DatabaseConnection.getConnection().prepareStatement("SELECT top 10 " +
                    "PLAYER.NAME_OF_PLAYER, PLAYER.SURNAME_OF_PLAYER, PLAYER.CLUB_NAME, PLAYER.POSITION, PLAYER_STATISTICS.COUNT_OF_MATCHES, PLAYER_STATISTICS.ASSISTS FROM PLAYER " +
                    "INNER JOIN PLAYER_STATISTICS ON (PLAYER.PLAYER_ID = PLAYER_STATISTICS.PLAYER_ID) WHERE PLAYER.PLAYER_ID IN " +
                    "(SELECT PLAYER_ID FROM PLAYER WHERE CLUB_NAME IN (SELECT CLUB_NAME FROM FOOTBALL_CLUB WHERE LEAGUE_NAME=?)) ORDER BY PLAYER_STATISTICS.ASSISTS DESC, PLAYER_STATISTICS.COUNT_OF_MATCHES");
            preparedStatement.setString(1, league_comboBox.getSelectedItem().toString());
            ResultSet resultSet = preparedStatement.executeQuery();
            int prevCountOfAssists = 0;
            int place = 0;
            int difference = 1;
            while (resultSet.next()) {
                if (resultSet.getInt("ASSISTS") == prevCountOfAssists) {
                    difference++;
                }
                else {
                    place += difference;
                    difference = 1;
                }
                assistantsArrayList.add(new Assistant(place, resultSet.getString("SURNAME_OF_PLAYER"), resultSet.getString("NAME_OF_PLAYER"),
                        resultSet.getString("CLUB_NAME"), resultSet.getString("POSITION"),
                        resultSet.getInt("COUNT_OF_MATCHES"), resultSet.getInt("ASSISTS")));
                prevCountOfAssists = resultSet.getInt("ASSISTS");
            }
            AssistantTableModel assistantTableModel = new AssistantTableModel(assistantsArrayList);
            assistants_table.setModel(assistantTableModel);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        assistants_table.setDefaultRenderer(String.class, centerRenderer);
        assistants_table.setDefaultRenderer(Integer.class, centerRenderer);
        setResizableColumns(assistants_table);
        setPreferredSizeForTable(assistants_table);
        setColumnHints(assistants_table);
        createGoalkeepersTable();
    }

    private void createGoalkeepersTable()
    {

        ArrayList<Goalkeeper> goalkeepersArrayList = new ArrayList<Goalkeeper>();
        try {
            PreparedStatement preparedStatement = DatabaseConnection.getConnection().prepareStatement("SELECT top 7 " +
                    "PLAYER.NAME_OF_PLAYER, PLAYER.SURNAME_OF_PLAYER, PLAYER.CLUB_NAME, PLAYER.POSITION, PLAYER_STATISTICS.COUNT_OF_MATCHES, PLAYER_STATISTICS.CLEAN_SHEETS " +
                    "FROM PLAYER INNER JOIN PLAYER_STATISTICS ON (PLAYER.PLAYER_ID = PLAYER_STATISTICS.PLAYER_ID) WHERE PLAYER.PLAYER_ID IN " +
                    "(SELECT PLAYER_ID FROM PLAYER WHERE POSITION='вратарь' AND CLUB_NAME IN (SELECT CLUB_NAME FROM FOOTBALL_CLUB WHERE LEAGUE_NAME=?)) " +
                    "ORDER BY PLAYER_STATISTICS.CLEAN_SHEETS DESC, PLAYER_STATISTICS.COUNT_OF_MATCHES");
            preparedStatement.setString(1, league_comboBox.getSelectedItem().toString());
            ResultSet resultSet = preparedStatement.executeQuery();
            int prevCountOfCleanSheets= 0;
            int place = 0;
            int difference = 1;
            while (resultSet.next()) {
                if (resultSet.getInt("CLEAN_SHEETS") == prevCountOfCleanSheets) {
                    difference++;
                }
                else {
                    place += difference;
                    difference = 1;
                }
                goalkeepersArrayList.add(new Goalkeeper(place, resultSet.getString("SURNAME_OF_PLAYER"), resultSet.getString("NAME_OF_PLAYER"),
                        resultSet.getString("CLUB_NAME"), resultSet.getString("POSITION"),
                        resultSet.getInt("COUNT_OF_MATCHES"), resultSet.getInt("CLEAN_SHEETS")));
                prevCountOfCleanSheets = resultSet.getInt("CLEAN_SHEETS");
            }
            GoalkeeperTableModel goalkeeperTableModel = new GoalkeeperTableModel(goalkeepersArrayList);
            clean_sheets_table.setModel(goalkeeperTableModel);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        clean_sheets_table.setDefaultRenderer(String.class, centerRenderer);
        clean_sheets_table.setDefaultRenderer(Integer.class, centerRenderer);
        setResizableColumns(clean_sheets_table);
        setPreferredSizeForTable(clean_sheets_table);
        setColumnHints(clean_sheets_table);
        createOwnersOfYellowCardsTable();
    }

    private void createOwnersOfYellowCardsTable()
    {

        ArrayList<OwnerOfYellowCards> ownersOfYellowCardsArrayList = new ArrayList<OwnerOfYellowCards>();
        try {
            PreparedStatement preparedStatement = DatabaseConnection.getConnection().prepareStatement("SELECT top 5 " +
                    "PLAYER.NAME_OF_PLAYER, PLAYER.SURNAME_OF_PLAYER, PLAYER.CLUB_NAME, PLAYER.POSITION, PLAYER_STATISTICS.COUNT_OF_MATCHES, PLAYER_STATISTICS.YELLOW_CARDS FROM PLAYER " +
                    "INNER JOIN PLAYER_STATISTICS ON (PLAYER.PLAYER_ID = PLAYER_STATISTICS.PLAYER_ID) WHERE PLAYER.PLAYER_ID IN " +
                    "(SELECT PLAYER_ID FROM PLAYER WHERE CLUB_NAME IN (SELECT CLUB_NAME FROM FOOTBALL_CLUB WHERE LEAGUE_NAME=?)) ORDER BY PLAYER_STATISTICS.YELLOW_CARDS DESC, PLAYER_STATISTICS.COUNT_OF_MATCHES");
            preparedStatement.setString(1, league_comboBox.getSelectedItem().toString());
            ResultSet resultSet = preparedStatement.executeQuery();
            int prevCountOfYellowCards = 0;
            int place = 0;
            int difference = 1;
            while (resultSet.next()) {
                if (resultSet.getInt("YELLOW_CARDS") == prevCountOfYellowCards) {
                    difference++;
                }
                else {
                    place += difference;
                    difference = 1;
                }
                ownersOfYellowCardsArrayList.add(new OwnerOfYellowCards(place, resultSet.getString("SURNAME_OF_PLAYER"), resultSet.getString("NAME_OF_PLAYER"),
                        resultSet.getString("CLUB_NAME"), resultSet.getString("POSITION"),
                        resultSet.getInt("COUNT_OF_MATCHES"), resultSet.getInt("YELLOW_CARDS")));
                prevCountOfYellowCards = resultSet.getInt("YELLOW_CARDS");
            }
            OwnerYCTableModel ownerYCTableModel = new OwnerYCTableModel(ownersOfYellowCardsArrayList);
            yellow_cards_table.setModel(ownerYCTableModel);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        yellow_cards_table.setDefaultRenderer(String.class, centerRenderer);
        yellow_cards_table.setDefaultRenderer(Integer.class, centerRenderer);
        setResizableColumns(yellow_cards_table);
        setPreferredSizeForTable(yellow_cards_table);
        setColumnHints(yellow_cards_table);
        createOwnersOfRedCardsTable();
    }

    private void createOwnersOfRedCardsTable()
    {

        ArrayList<OwnerOfRedCards> ownersOfRedCardsArrayList = new ArrayList<OwnerOfRedCards>();
        try {
            PreparedStatement preparedStatement = DatabaseConnection.getConnection().prepareStatement("SELECT top 5 " +
                    "PLAYER.NAME_OF_PLAYER, PLAYER.SURNAME_OF_PLAYER, PLAYER.CLUB_NAME, PLAYER.POSITION, PLAYER_STATISTICS.COUNT_OF_MATCHES, PLAYER_STATISTICS.RED_CARDS FROM PLAYER " +
                    "INNER JOIN PLAYER_STATISTICS ON (PLAYER.PLAYER_ID = PLAYER_STATISTICS.PLAYER_ID) WHERE PLAYER.PLAYER_ID IN " +
                    "(SELECT PLAYER_ID FROM PLAYER WHERE CLUB_NAME IN (SELECT CLUB_NAME FROM FOOTBALL_CLUB WHERE LEAGUE_NAME=?)) ORDER BY PLAYER_STATISTICS.RED_CARDS DESC, PLAYER_STATISTICS.COUNT_OF_MATCHES");
            preparedStatement.setString(1, league_comboBox.getSelectedItem().toString());
            ResultSet resultSet = preparedStatement.executeQuery();
            int prevCountOfRedCards = 0;
            int place = 0;
            int difference = 1;
            while (resultSet.next()) {
                if (resultSet.getInt("RED_CARDS") == prevCountOfRedCards) {
                    difference++;
                }
                else {
                    place += difference;
                    difference = 1;
                }
                ownersOfRedCardsArrayList.add(new OwnerOfRedCards(place, resultSet.getString("SURNAME_OF_PLAYER"), resultSet.getString("NAME_OF_PLAYER"),
                        resultSet.getString("CLUB_NAME"), resultSet.getString("POSITION"),
                        resultSet.getInt("COUNT_OF_MATCHES"), resultSet.getInt("RED_CARDS")));
                prevCountOfRedCards = resultSet.getInt("RED_CARDS");
            }
            OwnerRCTableModel ownerRCTableModel = new OwnerRCTableModel(ownersOfRedCardsArrayList);
            red_cards_table.setModel(ownerRCTableModel);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        red_cards_table.setDefaultRenderer(String.class, centerRenderer);
        red_cards_table.setDefaultRenderer(Integer.class, centerRenderer);
        setResizableColumns(red_cards_table);
        setPreferredSizeForTable(red_cards_table);
        setColumnHints(red_cards_table);
    }

    //запрет на изменение размера ширины столбцов
    private void setResizableColumns(JTable table)
    {
        for (int i = 0; i < table.getColumnModel().getColumnCount(); i++){
            table.getColumnModel().getColumn(i).setResizable(false);
        }
    }

    //установка размеров столбцов по размерам значений строк
    private void setPreferredSizeForTable(JTable table)
    {
        for (int column = 0; column < table.getColumnCount(); column++)
        {
            TableColumn tableColumn = table.getColumnModel().getColumn(column);
            int preferredWidth = tableColumn.getMinWidth();
            int maxWidth = tableColumn.getMaxWidth();

            for (int row = 0; row < table.getRowCount(); row++)
            {
                TableCellRenderer cellRenderer = table.getCellRenderer(row, column);
                Component c = table.prepareRenderer(cellRenderer, row, column);
                int width = c.getPreferredSize().width + table.getIntercellSpacing().width;
                preferredWidth = Math.max(preferredWidth, width);
                if (preferredWidth >= maxWidth)
                {
                    preferredWidth = maxWidth;
                    break;
                }
            }

            tableColumn.setPreferredWidth( preferredWidth );
        }
    }

    //установка подсказок для шапки таблицы
    private void setColumnHints(JTable table)
    {
        final TableCellRenderer header = table.getTableHeader().getDefaultRenderer();
        table.getTableHeader().setDefaultRenderer(new TableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table,
                                                           Object value, boolean isSelected, boolean hasFocus, int row,
                                                           int column) {
                Component tableCellRendererComponent = header.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                String columnHeader = table.getColumnModel().getColumn(column)
                        .getHeaderValue().toString();
                ((JComponent) tableCellRendererComponent).setToolTipText(columnHeader);
                return tableCellRendererComponent;
            }
        });
    }
}
