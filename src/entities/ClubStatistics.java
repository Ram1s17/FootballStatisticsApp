package entities;

public class ClubStatistics {
    private String club_name;
    private int tournament_position;
    private int count_of_matches_played;
    private int wins;
    private int draws;
    private int losses;
    private int goal_for;
    private int goal_against;
    private int goal_difference;
    private int points;

    public ClubStatistics(String club_name, int tournament_position, int count_of_matches_played,
                          int wins, int draws, int losses, int goal_for,
                          int goal_against, int goal_difference, int points)
    {
        this.club_name = club_name;
        this.tournament_position = tournament_position;
        this.count_of_matches_played = count_of_matches_played;
        this.wins = wins;
        this.draws = draws;
        this.losses = losses;
        this.goal_for = goal_for;
        this.goal_against = goal_against;
        this.goal_difference = goal_difference;
        this.points = points;
    }

    public String getClub_name() {
        return club_name;
    }

    public void setClub_name(String club_name) {
        this.club_name = club_name;
    }

    public int getTournament_position() {
        return tournament_position;
    }

    public void setTournament_position(int tournament_position) {
        this.tournament_position = tournament_position;
    }

    public int getCount_of_matches_played() {
        return count_of_matches_played;
    }

    public void setCount_of_matches_played(int count_of_matches_played) {
        this.count_of_matches_played = count_of_matches_played;
    }

    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public int getDraws() {
        return draws;
    }

    public void setDraws(int draws) {
        this.draws = draws;
    }

    public int getLosses() {
        return losses;
    }

    public void setLosses(int losses) {
        this.losses = losses;
    }

    public int getGoal_for() {
        return goal_for;
    }

    public void setGoal_for(int goal_for) {
        this.goal_for = goal_for;
    }

    public int getGoal_against() {
        return goal_against;
    }

    public void setGoal_against(int goal_against) {
        this.goal_against = goal_against;
    }

    public int getGoal_difference() {
        return goal_difference;
    }

    public void setGoal_difference(int goal_difference) {
        this.goal_difference = goal_difference;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}
