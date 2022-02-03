package ui;

import connection.DatabaseConnection;
import entities.*;
import table_models.*;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class ViewForm extends JFrame{
    private JPanel mainPanel;
    private JButton goBack_button;
    private JTable viewTable;
    private JPanel subPanel;
    private JButton showLeague_button;
    private JButton showPlayerStatistics_button;
    private JButton showClubStatistics_button;
    private JButton showClub_button;
    private JButton showPlayer_button;
    private JTextField filtration_textField;
    private JLabel view_label;

    public ViewForm(String windowTitle) {
        super(windowTitle);
        this.setContentPane(mainPanel);
        this.setBounds(300, 150, 1000, 500);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment( JLabel.CENTER );
        viewTable.setDefaultRenderer(String.class, centerRenderer);
        viewTable.setDefaultRenderer(Integer.class, centerRenderer);
        viewTable.setDefaultRenderer(LocalDate.class, centerRenderer);
        this.addWindowListener(new WindowAdapter() {
            //I skipped unused callbacks for readability

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
        showLeague_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<League> leagueArrayList = new ArrayList<League>();
                try {
                    ResultSet resultSet = DatabaseConnection.getStatement().executeQuery("SELECT * FROM LEAGUE");
                    while (resultSet.next()) {
                        leagueArrayList.add(new League(resultSet.getString("LEAGUE_NAME"), resultSet.getInt("COUNT_OF_TEAMS"),
                                resultSet.getInt("COUNT_OF_TOURS_PLAYED"), resultSet.getString("COUNTRY")));
                    }
                    LeagueTableModel leagueTableModel = new LeagueTableModel(leagueArrayList);
                    viewTable.setModel(leagueTableModel);
                    viewTable.setRowSorter(new TableRowSorter(leagueTableModel));
                    filterTable();
                    setResizableColumns();
                    setColumnHints();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });
        showClub_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<FootballClub> footballClubsArrayList = new ArrayList<FootballClub>();
                try {
                    ResultSet resultSet = DatabaseConnection.getStatement().executeQuery("SELECT * FROM FOOTBALL_CLUB");
                    while (resultSet.next()) {
                        footballClubsArrayList.add(new FootballClub(resultSet.getString("CLUB_NAME"), resultSet.getString("LEAGUE_NAME"),
                                resultSet.getInt("COUNT_OF_PLAYERS"), resultSet.getString("SURNAME_OF_COACH"),
                                resultSet.getString("NAME_OF_COACH")));
                    }
                    FootballClubTableModel footballClubTableModel = new FootballClubTableModel(footballClubsArrayList);
                    viewTable.setModel(footballClubTableModel);
                    viewTable.setRowSorter(new TableRowSorter(footballClubTableModel));
                    filterTable();
                    setResizableColumns();
                    setColumnHints();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });
        showClubStatistics_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(SignInForm.isAdmin())
                {
                    ArrayList<ClubStatisticsForAdmin> clubStatisticsForAdminArrayList = new ArrayList<ClubStatisticsForAdmin>();
                    try {
                        ResultSet resultSet = DatabaseConnection.getStatement().executeQuery("SELECT * FROM CLUB_STATISTICS");
                        while (resultSet.next()) {
                            clubStatisticsForAdminArrayList.add(new ClubStatisticsForAdmin(resultSet.getInt("CLUB_STATISTICS_ID"), resultSet.getString("CLUB_NAME"),
                                    resultSet.getInt("TOURNAMENT_POSITION"), resultSet.getInt("COUNT_OF_MATCHES_PLAYED"),
                                    resultSet.getInt("WINS"), resultSet.getInt("DRAWS"), resultSet.getInt("LOSSES"),
                                    resultSet.getInt("GOAL_FOR"), resultSet.getInt("GOAL_AGAINST"), resultSet.getInt("GOAL_DIFFERENCE"), resultSet.getInt("POINTS")));
                        }
                        ClubStatisticsForAdminTableModel clubStatisticsForAdminTableModel = new ClubStatisticsForAdminTableModel(clubStatisticsForAdminArrayList);
                        viewTable.setModel(clubStatisticsForAdminTableModel);
                        viewTable.setRowSorter(new TableRowSorter(clubStatisticsForAdminTableModel));
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }
                else
                {
                    ArrayList<ClubStatistics> clubStatisticsArrayList = new ArrayList<ClubStatistics>();
                    try {
                        ResultSet resultSet = DatabaseConnection.getStatement().executeQuery("SELECT * FROM CLUB_STATISTICS");
                        while (resultSet.next()) {
                            clubStatisticsArrayList.add(new ClubStatistics(resultSet.getString("CLUB_NAME"),
                                    resultSet.getInt("TOURNAMENT_POSITION"), resultSet.getInt("COUNT_OF_MATCHES_PLAYED"), resultSet.getInt("WINS"),
                                    resultSet.getInt("DRAWS"), resultSet.getInt("LOSSES"), resultSet.getInt("GOAL_FOR"),
                                    resultSet.getInt("GOAL_AGAINST"), resultSet.getInt("GOAL_DIFFERENCE"), resultSet.getInt("POINTS")));
                        }
                        ClubStatisticsTableModel clubStatisticsTableModel = new ClubStatisticsTableModel(clubStatisticsArrayList);
                        viewTable.setModel(clubStatisticsTableModel);
                        viewTable.setRowSorter(new TableRowSorter(clubStatisticsTableModel));
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }
                filterTable();
                setResizableColumns();
                setPreferredSizeForTable();
                setColumnHints();
            }
        });
        showPlayer_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(SignInForm.isAdmin())
                {
                    ArrayList<PlayerForAdmin> playerForAdminArrayList = new ArrayList<PlayerForAdmin>();
                    try {
                        ResultSet resultSet = DatabaseConnection.getStatement().executeQuery("SELECT * FROM PLAYER");
                        while (resultSet.next()) {
                            playerForAdminArrayList.add(new PlayerForAdmin(resultSet.getInt("PLAYER_ID"), resultSet.getString("SURNAME_OF_PLAYER"),
                                    resultSet.getString("NAME_OF_PLAYER"), resultSet.getDate("DATE_OF_BIRTH").toLocalDate(), resultSet.getString("NATIONALITY"),
                                    resultSet.getString("CLUB_NAME"), resultSet.getInt("JERSEY_NUMBER"), resultSet.getString("POSITION")));
                        }
                        PlayerForAdminTableModel playerForAdminTableModel = new PlayerForAdminTableModel(playerForAdminArrayList);
                        viewTable.setModel(playerForAdminTableModel);
                        viewTable.setRowSorter(new TableRowSorter(playerForAdminTableModel));
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }
                else
                {
                    ArrayList<Player> playerArrayList = new ArrayList<Player>();
                    try {
                        ResultSet resultSet = DatabaseConnection.getStatement().executeQuery("SELECT * FROM PLAYER");
                        while (resultSet.next()) {
                            playerArrayList.add(new Player(resultSet.getString("SURNAME_OF_PLAYER"),
                                    resultSet.getString("NAME_OF_PLAYER"), resultSet.getDate("DATE_OF_BIRTH").toLocalDate(), resultSet.getString("NATIONALITY"),
                                    resultSet.getString("CLUB_NAME"), resultSet.getInt("JERSEY_NUMBER"), resultSet.getString("POSITION")));
                        }
                        PlayerTableModel playerTableModel = new PlayerTableModel(playerArrayList);
                        viewTable.setModel(playerTableModel);
                        viewTable.setRowSorter(new TableRowSorter(playerTableModel));
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }
                filterTable();
                setResizableColumns();
                setPreferredSizeForTable();
                setColumnHints();
            }
        });
        showPlayerStatistics_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(SignInForm.isAdmin())
                {
                    ArrayList<PlayerStatisticsForAdmin> playerStatisticsForAdminArrayList = new ArrayList<PlayerStatisticsForAdmin>();
                    try {
                        ResultSet resultSet = DatabaseConnection.getStatement().executeQuery("SELECT * FROM PLAYER_STATISTICS");
                        while (resultSet.next()) {
                            playerStatisticsForAdminArrayList.add(new PlayerStatisticsForAdmin(resultSet.getInt("PLAYER_STATISTICS_ID"), resultSet.getInt("PLAYER_ID"),
                                    resultSet.getInt("COUNT_OF_MATCHES"), resultSet.getInt("COUNT_OF_MIN_PER_MATCH"), resultSet.getInt("GOALS"),
                                    resultSet.getInt("ASSISTS"), resultSet.getInt("MISSED_GOALS"), resultSet.getInt("CLEAN_SHEETS"),
                                    resultSet.getInt("YELLOW_CARDS"), resultSet.getInt("RED_CARDS")));
                        }
                        PlayerStatisticsForAdminTableModel playerStatisticsForAdminTableModel = new PlayerStatisticsForAdminTableModel(playerStatisticsForAdminArrayList);
                        viewTable.setModel(playerStatisticsForAdminTableModel);
                        viewTable.setRowSorter(new TableRowSorter(playerStatisticsForAdminTableModel));
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }
                else
                {
                    ArrayList<PlayerStatistics> playerStatisticsArrayList = new ArrayList<PlayerStatistics>();
                    try {
                        ResultSet resultSet = DatabaseConnection.getStatement().executeQuery("SELECT * FROM PLAYER_STATISTICS");
                        while (resultSet.next()) {
                            playerStatisticsArrayList.add(new PlayerStatistics(resultSet.getInt("COUNT_OF_MATCHES"), resultSet.getInt("COUNT_OF_MIN_PER_MATCH"),
                                    resultSet.getInt("GOALS"), resultSet.getInt("ASSISTS"), resultSet.getInt("MISSED_GOALS"),
                                    resultSet.getInt("CLEAN_SHEETS"), resultSet.getInt("YELLOW_CARDS"), resultSet.getInt("RED_CARDS")));
                        }
                        PlayerStatisticsTableModel playerStatisticsTableModel = new PlayerStatisticsTableModel(playerStatisticsArrayList);
                        viewTable.setModel(playerStatisticsTableModel);
                        viewTable.setRowSorter(new TableRowSorter(playerStatisticsTableModel));
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }
                filterTable();
                setResizableColumns();
                setPreferredSizeForTable();
                setColumnHints();
            }
        });
        this.setVisible(true);
    }

    //фильтрация таблицы
    private void filterTable()
    {
        filtration_textField.setText("");
        TableRowSorter<TableModel> rowSorter = new TableRowSorter<>(viewTable.getModel());
        viewTable.setRowSorter(rowSorter);
        filtration_textField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                String text = filtration_textField.getText();

                if (text.trim().length() == 0) {
                    rowSorter.setRowFilter(null);
                } else {
                    rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                String text = filtration_textField.getText();

                if (text.trim().length() == 0) {
                    rowSorter.setRowFilter(null);
                } else {
                    rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                throw new UnsupportedOperationException("Not supported");
            }
        });
    }

    //запрет на изменение размера ширины столбцов
    private void setResizableColumns()
    {
        for (int i = 0; i < viewTable.getColumnModel().getColumnCount(); i++){
            viewTable.getColumnModel().getColumn(i).setResizable(false);
        }
    }

    //установка размеров столбцов по размерам значений строк
    private void setPreferredSizeForTable()
    {
        for (int column = 0; column < viewTable.getColumnCount(); column++)
        {
            TableColumn tableColumn = viewTable.getColumnModel().getColumn(column);
            int preferredWidth = tableColumn.getMinWidth();
            int maxWidth = tableColumn.getMaxWidth();

            for (int row = 0; row < viewTable.getRowCount(); row++)
            {
                TableCellRenderer cellRenderer = viewTable.getCellRenderer(row, column);
                Component c = viewTable.prepareRenderer(cellRenderer, row, column);
                int width = c.getPreferredSize().width + viewTable.getIntercellSpacing().width;
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
    private void setColumnHints()
    {
        final TableCellRenderer header = viewTable.getTableHeader().getDefaultRenderer();
        viewTable.getTableHeader().setDefaultRenderer(new TableCellRenderer() {
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
