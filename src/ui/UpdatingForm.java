package ui;

import connection.DatabaseConnection;
import entities.*;
import parsing.Parser;

import javax.swing.*;
import java.awt.event.*;
import java.sql.*;

public class UpdatingForm extends JFrame{
    private JPanel mainPanel;
    private JButton goBack_button;
    private JPanel butPanel;
    private JButton updateAPL_button;
    private JButton updateBundesliga_button;
    private JButton updateLaLiga_button;
    private JButton updateLiga1_button;
    private JButton updateSeriaA_button;

    public UpdatingForm(String windowTitle) {
        super(windowTitle);
        this.setContentPane(mainPanel);
        this.setBounds(550, 300, 300, 250);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
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
                new AdminMenuForm("Меню администратора");
            }
        });
        updateAPL_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Parser parserAPL = new Parser("Английская Премьер-лига");
                insertDataToDB(parserAPL);
            }
        });
        updateBundesliga_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Parser parserBundesliga = new Parser("Бундеслига");
                insertDataToDB(parserBundesliga);
            }
        });
        updateLaLiga_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Parser parserLaLiga = new Parser("Ла Лига");
                insertDataToDB(parserLaLiga);
            }
        });
        updateLiga1_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Parser parserLiga1 = new Parser("Лига 1");
                insertDataToDB(parserLiga1);
            }
        });
        updateSeriaA_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Parser parserSeriaA = new Parser("Серия А");
                insertDataToDB(parserSeriaA);
            }
        });
        this.setVisible(true);
    }

    private void insertDataToDB(Parser parser) {
        String insertLeague = "IF EXISTS(SELECT * FROM LEAGUE WHERE LEAGUE_NAME=?) " +
                "UPDATE LEAGUE SET COUNT_OF_TEAMS=?, COUNT_OF_TOURS_PLAYED=?, COUNTRY=? WHERE LEAGUE_NAME=? " +
                "ELSE INSERT INTO LEAGUE (LEAGUE_NAME, COUNT_OF_TEAMS, COUNT_OF_TOURS_PLAYED, COUNTRY) VALUES(?, ?, ?, ?)";
        String insertFootballClub = "IF EXISTS(SELECT * FROM FOOTBALL_CLUB WHERE CLUB_NAME=?) " +
                "UPDATE FOOTBALL_CLUB SET LEAGUE_NAME=?, COUNT_OF_PLAYERS=?, SURNAME_OF_COACH=?, NAME_OF_COACH=? WHERE CLUB_NAME=? " +
                "ELSE INSERT INTO FOOTBALL_CLUB (CLUB_NAME, LEAGUE_NAME, COUNT_OF_PLAYERS, SURNAME_OF_COACH, NAME_OF_COACH) VALUES(?, ?, ?, ?, ?)";
        String insertClubStatistics = "IF EXISTS(SELECT * FROM CLUB_STATISTICS WHERE CLUB_NAME=?) " +
                "UPDATE CLUB_STATISTICS SET TOURNAMENT_POSITION=?, COUNT_OF_MATCHES_PLAYED=?, WINS=?, DRAWS=?, LOSSES=?, " +
                "GOAL_FOR=?, GOAL_AGAINST=?, GOAL_DIFFERENCE=?, POINTS=? WHERE CLUB_NAME=? " +
                "ELSE INSERT INTO CLUB_STATISTICS (CLUB_NAME, TOURNAMENT_POSITION, COUNT_OF_MATCHES_PLAYED, WINS, DRAWS, LOSSES, GOAL_FOR, GOAL_AGAINST, GOAL_DIFFERENCE, POINTS) " +
                "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        String insertPlayer = "IF EXISTS(SELECT * FROM PLAYER WHERE SURNAME_OF_PLAYER=? AND NAME_OF_PLAYER=? AND POSITION=?) " +
                "UPDATE PLAYER SET DATE_OF_BIRTH=?, NATIONALITY=?, CLUB_NAME=?, JERSEY_NUMBER=? WHERE SURNAME_OF_PLAYER=? AND NAME_OF_PLAYER=? AND POSITION=? " +
                "ELSE INSERT INTO PLAYER (SURNAME_OF_PLAYER, NAME_OF_PLAYER, DATE_OF_BIRTH, NATIONALITY, CLUB_NAME, JERSEY_NUMBER, POSITION) VALUES(?, ?, ?, ?, ?, ?, ?)";
        String insertPlayerStatistics = "IF EXISTS(SELECT * FROM PLAYER_STATISTICS WHERE PLAYER_ID=?) " +
                "UPDATE PLAYER_STATISTICS SET COUNT_OF_MATCHES=?, COUNT_OF_MIN_PER_MATCH=?, GOALS=?, ASSISTS=?, " +
                "MISSED_GOALS=?, CLEAN_SHEETS=?, YELLOW_CARDS=?, RED_CARDS=? WHERE PLAYER_ID=? " +
                "ELSE INSERT INTO PLAYER_STATISTICS (PLAYER_ID, COUNT_OF_MATCHES, COUNT_OF_MIN_PER_MATCH, GOALS, ASSISTS, MISSED_GOALS, CLEAN_SHEETS, YELLOW_CARDS, RED_CARDS) " +
                "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement preparedStatement = null;
        PreparedStatement preparedStatementForPlayerID = null;
        try {
            preparedStatement = DatabaseConnection.getConnection().prepareStatement(insertLeague);
            preparedStatement.setString(1, parser.getLeaguesArrayList().get(0).getLeague_name());
            preparedStatement.setInt(2, parser.getLeaguesArrayList().get(0).getCount_of_teams());
            preparedStatement.setInt(3, parser.getLeaguesArrayList().get(0).getCount_of_tours_played());
            preparedStatement.setString(4, parser.getLeaguesArrayList().get(0).getCountry());
            preparedStatement.setString(5, parser.getLeaguesArrayList().get(0).getLeague_name());
            preparedStatement.setString(6, parser.getLeaguesArrayList().get(0).getLeague_name());
            preparedStatement.setInt(7, parser.getLeaguesArrayList().get(0).getCount_of_teams());
            preparedStatement.setInt(8, parser.getLeaguesArrayList().get(0).getCount_of_tours_played());
            preparedStatement.setString(9, parser.getLeaguesArrayList().get(0).getCountry());
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        for(FootballClub footballClub: parser.getFootballClubsArrayList())
        {
            try {
                preparedStatement = DatabaseConnection.getConnection().prepareStatement(insertFootballClub);
                preparedStatement.setString(1, footballClub.getClub_name());
                preparedStatement.setString(2, footballClub.getLeague_name());
                preparedStatement.setInt(3, footballClub.getCount_of_players());
                preparedStatement.setString(4, footballClub.getSurname_of_coach());
                preparedStatement.setString(5, footballClub.getName_of_coach());
                preparedStatement.setString(6, footballClub.getClub_name());
                preparedStatement.setString(7, footballClub.getClub_name());
                preparedStatement.setString(8, footballClub.getLeague_name());
                preparedStatement.setInt(9, footballClub.getCount_of_players());
                preparedStatement.setString(10, footballClub.getSurname_of_coach());
                preparedStatement.setString(11, footballClub.getName_of_coach());
                preparedStatement.executeUpdate();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        for(ClubStatistics clubStatistics: parser.getClubStatisticsArrayList())
        {
            try {
                preparedStatement = DatabaseConnection.getConnection().prepareStatement(insertClubStatistics);
                preparedStatement.setString(1, clubStatistics.getClub_name());
                preparedStatement.setInt(2, clubStatistics.getTournament_position());
                preparedStatement.setInt(3, clubStatistics.getCount_of_matches_played());
                preparedStatement.setInt(4, clubStatistics.getWins());
                preparedStatement.setInt(5, clubStatistics.getDraws());
                preparedStatement.setInt(6, clubStatistics.getLosses());
                preparedStatement.setInt(7, clubStatistics.getGoal_for());
                preparedStatement.setInt(8, clubStatistics.getGoal_against());
                preparedStatement.setInt(9, clubStatistics.getGoal_difference());
                preparedStatement.setInt(10, clubStatistics.getPoints());
                preparedStatement.setString(11, clubStatistics.getClub_name());
                preparedStatement.setString(12, clubStatistics.getClub_name());
                preparedStatement.setInt(13, clubStatistics.getTournament_position());
                preparedStatement.setInt(14, clubStatistics.getCount_of_matches_played());
                preparedStatement.setInt(15, clubStatistics.getWins());
                preparedStatement.setInt(16, clubStatistics.getDraws());
                preparedStatement.setInt(17, clubStatistics.getLosses());
                preparedStatement.setInt(18, clubStatistics.getGoal_for());
                preparedStatement.setInt(19, clubStatistics.getGoal_against());
                preparedStatement.setInt(20, clubStatistics.getGoal_difference());
                preparedStatement.setInt(21, clubStatistics.getPoints());
                preparedStatement.executeUpdate();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        PlayerStatistics currentPlayerStatistics = null;
        ResultSet resultSet = null;
        int indexOfPlayer = 0;
        int player_id = 0;
        for(Player player: parser.getPlayerArrayList())
        {
            try {
                preparedStatement = DatabaseConnection.getConnection().prepareStatement(insertPlayer);
                preparedStatement.setString(1, player.getSurname_of_player());
                preparedStatement.setString(2, player.getName_of_player());
                preparedStatement.setString(3, player.getPosition());
                preparedStatement.setDate(4, Date.valueOf(player.getDate_of_birth()));
                preparedStatement.setString(5, player.getNationality());
                preparedStatement.setString(6, player.getClub_name());
                preparedStatement.setInt(7, player.getJersey_number());
                preparedStatement.setString(8, player.getSurname_of_player());
                preparedStatement.setString(9, player.getName_of_player());
                preparedStatement.setString(10, player.getPosition());
                preparedStatement.setString(11, player.getSurname_of_player());
                preparedStatement.setString(12, player.getName_of_player());
                preparedStatement.setDate(13, Date.valueOf(player.getDate_of_birth()));
                preparedStatement.setString(14, player.getNationality());
                preparedStatement.setString(15, player.getClub_name());
                preparedStatement.setInt(16, player.getJersey_number());
                preparedStatement.setString(17, player.getPosition());
                preparedStatement.executeUpdate();

                preparedStatementForPlayerID = DatabaseConnection.getConnection().prepareStatement("SELECT PLAYER_ID FROM PLAYER WHERE SURNAME_OF_PLAYER=? AND NAME_OF_PLAYER=? AND POSITION=?");
                preparedStatementForPlayerID.setString(1, player.getSurname_of_player());
                preparedStatementForPlayerID.setString(2, player.getName_of_player());
                preparedStatementForPlayerID.setString(3, player.getPosition());
                resultSet = preparedStatementForPlayerID.executeQuery();
                while(resultSet.next()) {
                    player_id = resultSet.getInt("PLAYER_ID");
                }
                currentPlayerStatistics = parser.getPlayerStatisticsArrayList().get(indexOfPlayer);

                preparedStatement = DatabaseConnection.getConnection().prepareStatement(insertPlayerStatistics);
                preparedStatement.setInt(1, player_id);
                preparedStatement.setInt(2, currentPlayerStatistics.getCount_of_matches());
                preparedStatement.setInt(3, currentPlayerStatistics.getCount_of_min_per_match());
                preparedStatement.setInt(4, currentPlayerStatistics.getGoals());
                preparedStatement.setInt(5, currentPlayerStatistics.getAssists());
                if (currentPlayerStatistics.getMissed_goals() == null)
                {
                    preparedStatement.setNull(6, Types.INTEGER);
                    preparedStatement.setNull(7, Types.INTEGER);
                }
                else {
                    preparedStatement.setInt(6, currentPlayerStatistics.getMissed_goals());
                    preparedStatement.setInt(7, currentPlayerStatistics.getClean_sheets());
                }
                preparedStatement.setInt(8, currentPlayerStatistics.getYellow_cards());
                preparedStatement.setInt(9, currentPlayerStatistics.getRed_cards());
                preparedStatement.setInt(10, player_id);
                preparedStatement.setInt(11, player_id);
                preparedStatement.setInt(12, currentPlayerStatistics.getCount_of_matches());
                preparedStatement.setInt(13, currentPlayerStatistics.getCount_of_min_per_match());
                preparedStatement.setInt(14, currentPlayerStatistics.getGoals());
                preparedStatement.setInt(15, currentPlayerStatistics.getAssists());
                if (currentPlayerStatistics.getMissed_goals() == null)
                {
                    preparedStatement.setNull(16, Types.INTEGER);
                    preparedStatement.setNull(17, Types.INTEGER);
                }
                else {
                    preparedStatement.setInt(16, currentPlayerStatistics.getMissed_goals());
                    preparedStatement.setInt(17, currentPlayerStatistics.getClean_sheets());
                }
                preparedStatement.setInt(18, currentPlayerStatistics.getYellow_cards());
                preparedStatement.setInt(19, currentPlayerStatistics.getRed_cards());
                preparedStatement.executeUpdate();

                indexOfPlayer++;
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }
}
