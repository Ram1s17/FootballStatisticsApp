package entities;

public class ClubStatisticsForAdmin extends ClubStatistics{
    private int club_statistics_id;

    public ClubStatisticsForAdmin(int club_statistics_id, String club_name, int tournament_position, int count_of_matches_played,
                          int wins, int draws, int losses, int goal_for,
                          int goal_against, int goal_difference, int points)
    {
        super(club_name, tournament_position, count_of_matches_played, wins, draws, losses,
                goal_for, goal_against, goal_difference, points);
        this.club_statistics_id = club_statistics_id;
    }

    public int getClub_statistics_id() {
        return club_statistics_id;
    }

    public void setClub_statistics_id(int club_statistics_id) {
        this.club_statistics_id = club_statistics_id;
    }
}
