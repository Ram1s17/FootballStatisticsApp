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
import java.time.LocalDate;
import java.util.ArrayList;

public class SearchingForm extends JFrame{
    private JButton goBack_button;
    private JLabel searching_label;
    private JPanel league_tab;
    private JPanel club_tab;
    private JPanel clubStatistics_tab;
    private JPanel player_tab;
    private JPanel playerStatistics_tab;
    private JTabbedPane searchingTabedPane;
    private JPanel mainPanel;
    private JTable league_table;
    private JTable club_table;
    private JTable clubStat_tabel;
    private JComboBox club_comboBox;
    private JComboBox position_comboBox;
    private JComboBox player_comboBox;
    private JTable playerStat_table;
    private JLabel league_label;
    private JLabel club_label;
    private JLabel position_label;
    private JLabel player_label;
    private JComboBox league_comboBox;
    private JPanel playerPanel;
    private JPanel leaguePanel;
    private JPanel clubPanel;
    private JPanel positionPanel;
    private JPanel comboBoxesPanel;
    private JTable player_table;
    private JButton searching_button;

    private DefaultTableCellRenderer centerRenderer;

    public SearchingForm(String windowTitle) {
        super(windowTitle);
        this.setContentPane(mainPanel);
        this.setBounds(350, 200, 860, 450);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setModelForLeagueBox();
        league_comboBox.setSelectedItem("Английская Премьер-лига");
        centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment( JLabel.CENTER );
        league_table.setRowHeight(league_table.getRowHeight() + 20);
        club_table.setRowHeight(club_table.getRowHeight() + 20);
        clubStat_tabel.setRowHeight(clubStat_tabel.getRowHeight() + 20);
        player_table.setRowHeight(player_table.getRowHeight() + 20);
        playerStat_table.setRowHeight(playerStat_table.getRowHeight() + 20);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                UIManager.put("OptionPane.yesButtonText", "Да"    );
                UIManager.put("OptionPane.noButtonText", "Нет"   );
                int result = JOptionPane.showConfirmDialog(
                        getContentPane(),
                        "Выйти из системы?",
                        "Окно подтверждения",
                        JOptionPane.YES_NO_OPTION);
                if(result == JOptionPane.YES_OPTION){
                    dispose();
                    new SignInForm("Вход в \"Football Statistics\"");
                }
            }
        });
        goBack_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                if (SignInForm.isAdmin())
                    new AdminMenuForm("Меню администратора");
                else
                    new UserMenuForm("Меню пользователя");
            }
        });
        league_comboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    Object item = e.getItem();
                    setModelForClubBox(item);
                    setModelForPlayerBox(club_comboBox.getSelectedItem(), position_comboBox.getSelectedItem());
                }
            }
        });
        club_comboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    Object clubItem = e.getItem();
                    setModelForPlayerBox(clubItem, position_comboBox.getSelectedItem());
                }
            }
        });
        position_comboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    Object  positionItem = e.getItem();
                    setModelForPlayerBox(club_comboBox.getSelectedItem(), positionItem);
                }
            }
        });
        searching_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createLeagueTable();
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
            setModelForClubBox(league_comboBox.getSelectedItem());
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private void setModelForClubBox(Object item)
    {
        DefaultComboBoxModel<String> comboBoxModel = new DefaultComboBoxModel<String>();
        try {
            PreparedStatement preparedStatement = DatabaseConnection.getConnection().prepareStatement("SELECT CLUB_NAME FROM FOOTBALL_CLUB WHERE LEAGUE_NAME=?");
            preparedStatement.setString(1, item.toString());
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next())
            {
                comboBoxModel.addElement(resultSet.getString("CLUB_NAME"));
            }
            club_comboBox.setModel(comboBoxModel);
            setModelForPositionBox();
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private void setModelForPositionBox() {
        position_comboBox.setEnabled(true);
        DefaultComboBoxModel<String> comboBoxModel = new DefaultComboBoxModel<String>();
        comboBoxModel.addElement("нападающий");
        comboBoxModel.addElement("полузащитник");
        comboBoxModel.addElement("защитник");
        comboBoxModel.addElement("вратарь");
        position_comboBox.setModel(comboBoxModel);
        setModelForPlayerBox(club_comboBox.getSelectedItem(), position_comboBox.getSelectedItem());
    }

    private void setModelForPlayerBox(Object clubItem, Object positionItem)
    {
        DefaultComboBoxModel<String> comboBoxModel = new DefaultComboBoxModel<String>();
        try {
            PreparedStatement preparedStatement = DatabaseConnection.getConnection().prepareStatement("SELECT SURNAME_OF_PLAYER FROM PLAYER WHERE CLUB_NAME=? AND POSITION=?");
            preparedStatement.setString(1, clubItem.toString());
            preparedStatement.setString(2, positionItem.toString());
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next())
            {
                comboBoxModel.addElement(resultSet.getString("SURNAME_OF_PLAYER"));
            }
            player_comboBox.setModel(comboBoxModel);
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private void createLeagueTable()
    {
        ArrayList<League> leagueArrayList = new ArrayList<League>();
        try {
            PreparedStatement preparedStatement = DatabaseConnection.getConnection().prepareStatement("SELECT * FROM LEAGUE WHERE LEAGUE_NAME=?");
            preparedStatement.setString(1, league_comboBox.getSelectedItem().toString());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                leagueArrayList.add(new League(resultSet.getString("LEAGUE_NAME"), resultSet.getInt("COUNT_OF_TEAMS"),
                        resultSet.getInt("COUNT_OF_TOURS_PLAYED"), resultSet.getString("COUNTRY")));
            }
            LeagueTableModel leagueTableModel = new LeagueTableModel(leagueArrayList);
            league_table.setModel(leagueTableModel);
            league_table.setDefaultRenderer(String.class, centerRenderer);
            league_table.setDefaultRenderer(Integer.class, centerRenderer);
            setResizableColumns(league_table);
            setColumnHints(league_table);
            createClubTable();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private void createClubTable()
    {
        ArrayList<FootballClub> footballClubsArrayList = new ArrayList<FootballClub>();
        try {
            PreparedStatement preparedStatement = DatabaseConnection.getConnection().prepareStatement("SELECT * FROM FOOTBALL_CLUB WHERE CLUB_NAME=?");
            preparedStatement.setString(1, club_comboBox.getSelectedItem().toString());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                footballClubsArrayList.add(new FootballClub(resultSet.getString("CLUB_NAME"), resultSet.getString("LEAGUE_NAME"),
                        resultSet.getInt("COUNT_OF_PLAYERS"), resultSet.getString("SURNAME_OF_COACH"),
                        resultSet.getString("NAME_OF_COACH")));
            }
            FootballClubTableModel footballClubTableModel = new FootballClubTableModel(footballClubsArrayList);
            club_table.setModel(footballClubTableModel);
            club_table.setDefaultRenderer(String.class, centerRenderer);
            club_table.setDefaultRenderer(Integer.class, centerRenderer);
            setResizableColumns(club_table);
            setColumnHints(club_table);
            createClubStatisticsTable();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private void createClubStatisticsTable()
    {
        if(SignInForm.isAdmin())
        {
            ArrayList<ClubStatisticsForAdmin> clubStatisticsForAdminArrayList = new ArrayList<ClubStatisticsForAdmin>();
            try {
                PreparedStatement preparedStatement = DatabaseConnection.getConnection().prepareStatement("SELECT * FROM CLUB_STATISTICS WHERE CLUB_NAME=?");
                preparedStatement.setString(1, club_comboBox.getSelectedItem().toString());
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    clubStatisticsForAdminArrayList.add(new ClubStatisticsForAdmin(resultSet.getInt("CLUB_STATISTICS_ID"), resultSet.getString("CLUB_NAME"),
                            resultSet.getInt("TOURNAMENT_POSITION"), resultSet.getInt("COUNT_OF_MATCHES_PLAYED"),
                            resultSet.getInt("WINS"), resultSet.getInt("DRAWS"), resultSet.getInt("LOSSES"),
                            resultSet.getInt("GOAL_FOR"), resultSet.getInt("GOAL_AGAINST"), resultSet.getInt("GOAL_DIFFERENCE"), resultSet.getInt("POINTS")));
                }
                ClubStatisticsForAdminTableModel clubStatisticsForAdminTableModel = new ClubStatisticsForAdminTableModel(clubStatisticsForAdminArrayList);
                clubStat_tabel.setModel(clubStatisticsForAdminTableModel);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        else
        {
            ArrayList<ClubStatistics> clubStatisticsArrayList = new ArrayList<ClubStatistics>();
            try {
                PreparedStatement preparedStatement = DatabaseConnection.getConnection().prepareStatement("SELECT * FROM CLUB_STATISTICS WHERE CLUB_NAME=?");
                preparedStatement.setString(1, club_comboBox.getSelectedItem().toString());
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    clubStatisticsArrayList.add(new ClubStatistics(resultSet.getString("CLUB_NAME"),
                            resultSet.getInt("TOURNAMENT_POSITION"), resultSet.getInt("COUNT_OF_MATCHES_PLAYED"), resultSet.getInt("WINS"),
                            resultSet.getInt("DRAWS"), resultSet.getInt("LOSSES"), resultSet.getInt("GOAL_FOR"),
                            resultSet.getInt("GOAL_AGAINST"), resultSet.getInt("GOAL_DIFFERENCE"), resultSet.getInt("POINTS")));
                }
                ClubStatisticsTableModel clubStatisticsTableModel = new ClubStatisticsTableModel(clubStatisticsArrayList);
                clubStat_tabel.setModel(clubStatisticsTableModel);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        clubStat_tabel.setDefaultRenderer(String.class, centerRenderer);
        clubStat_tabel.setDefaultRenderer(Integer.class, centerRenderer);
        setResizableColumns(clubStat_tabel);
        setPreferredSizeForTable(clubStat_tabel);
        setColumnHints(clubStat_tabel);
        createPlayerTable();
    }

    private void createPlayerTable()
    {
        if(SignInForm.isAdmin())
        {
            ArrayList<PlayerForAdmin> playerForAdminArrayList = new ArrayList<PlayerForAdmin>();
            try {
                PreparedStatement preparedStatement = DatabaseConnection.getConnection().prepareStatement("SELECT * FROM PLAYER WHERE SURNAME_OF_PLAYER=? AND CLUB_NAME=? AND POSITION=?");
                preparedStatement.setString(1, player_comboBox.getSelectedItem().toString());
                preparedStatement.setString(2, club_comboBox.getSelectedItem().toString());
                preparedStatement.setString(3, position_comboBox.getSelectedItem().toString());
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    playerForAdminArrayList.add(new PlayerForAdmin(resultSet.getInt("PLAYER_ID"), resultSet.getString("SURNAME_OF_PLAYER"),
                            resultSet.getString("NAME_OF_PLAYER"), resultSet.getDate("DATE_OF_BIRTH").toLocalDate(), resultSet.getString("NATIONALITY"),
                            resultSet.getString("CLUB_NAME"), resultSet.getInt("JERSEY_NUMBER"), resultSet.getString("POSITION")));
                    createPlayerStatisticsTable(resultSet.getInt("PLAYER_ID"));
                }
                PlayerForAdminTableModel playerForAdminTableModel = new PlayerForAdminTableModel(playerForAdminArrayList);
                player_table.setModel(playerForAdminTableModel);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        else
        {
            ArrayList<Player> playerArrayList = new ArrayList<Player>();
            try {
                PreparedStatement preparedStatement = DatabaseConnection.getConnection().prepareStatement("SELECT * FROM PLAYER WHERE SURNAME_OF_PLAYER=? AND CLUB_NAME=? AND POSITION=?");
                preparedStatement.setString(1, player_comboBox.getSelectedItem().toString());
                preparedStatement.setString(2, club_comboBox.getSelectedItem().toString());
                preparedStatement.setString(3, position_comboBox.getSelectedItem().toString());
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    playerArrayList.add(new Player(resultSet.getString("SURNAME_OF_PLAYER"),
                            resultSet.getString("NAME_OF_PLAYER"), resultSet.getDate("DATE_OF_BIRTH").toLocalDate(), resultSet.getString("NATIONALITY"),
                            resultSet.getString("CLUB_NAME"), resultSet.getInt("JERSEY_NUMBER"), resultSet.getString("POSITION")));
                    createPlayerStatisticsTable(resultSet.getInt("PLAYER_ID"));
                }
                PlayerTableModel playerTableModel = new PlayerTableModel(playerArrayList);
                player_table.setModel(playerTableModel);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        player_table.setDefaultRenderer(String.class, centerRenderer);
        player_table.setDefaultRenderer(Integer.class, centerRenderer);
        player_table.setDefaultRenderer(LocalDate.class, centerRenderer);
        setResizableColumns(player_table);
        setPreferredSizeForTable(player_table);
        setColumnHints(player_table);

    }

    private void createPlayerStatisticsTable(int playerID)
    {
        if(SignInForm.isAdmin())
        {
            ArrayList<PlayerStatisticsForAdmin> playerStatisticsForAdminArrayList = new ArrayList<PlayerStatisticsForAdmin>();
            try {
                PreparedStatement preparedStatement = DatabaseConnection.getConnection().prepareStatement("SELECT * FROM PLAYER_STATISTICS WHERE PLAYER_ID=?");
                preparedStatement.setInt(1, playerID);
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    playerStatisticsForAdminArrayList.add(new PlayerStatisticsForAdmin(resultSet.getInt("PLAYER_STATISTICS_ID"), resultSet.getInt("PLAYER_ID"),
                            resultSet.getInt("COUNT_OF_MATCHES"), resultSet.getInt("COUNT_OF_MIN_PER_MATCH"), resultSet.getInt("GOALS"),
                            resultSet.getInt("ASSISTS"), resultSet.getInt("MISSED_GOALS"), resultSet.getInt("CLEAN_SHEETS"),
                            resultSet.getInt("YELLOW_CARDS"), resultSet.getInt("RED_CARDS")));
                }
                PlayerStatisticsForAdminTableModel playerStatisticsForAdminTableModel = new PlayerStatisticsForAdminTableModel(playerStatisticsForAdminArrayList);
                playerStat_table.setModel(playerStatisticsForAdminTableModel);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        else
        {
            ArrayList<PlayerStatistics> playerStatisticsArrayList = new ArrayList<PlayerStatistics>();
            try {
                PreparedStatement preparedStatement = DatabaseConnection.getConnection().prepareStatement("SELECT * FROM PLAYER_STATISTICS WHERE PLAYER_ID=?");
                preparedStatement.setInt(1, playerID);
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    playerStatisticsArrayList.add(new PlayerStatistics(resultSet.getInt("COUNT_OF_MATCHES"), resultSet.getInt("COUNT_OF_MIN_PER_MATCH"),
                            resultSet.getInt("GOALS"), resultSet.getInt("ASSISTS"), resultSet.getInt("MISSED_GOALS"),
                            resultSet.getInt("CLEAN_SHEETS"), resultSet.getInt("YELLOW_CARDS"), resultSet.getInt("RED_CARDS")));
                }
                PlayerStatisticsTableModel playerStatisticsTableModel = new PlayerStatisticsTableModel(playerStatisticsArrayList);
                playerStat_table.setModel(playerStatisticsTableModel);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        playerStat_table.setDefaultRenderer(String.class, centerRenderer);
        playerStat_table.setDefaultRenderer(Integer.class, centerRenderer);
        setResizableColumns(playerStat_table);
        setPreferredSizeForTable(playerStat_table);
        setColumnHints(playerStat_table);
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
