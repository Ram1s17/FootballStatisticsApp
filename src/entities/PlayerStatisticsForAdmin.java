package entities;

public class PlayerStatisticsForAdmin extends PlayerStatistics{
    private int player_statistics_id;
    private int player_id;

    public PlayerStatisticsForAdmin(int player_statistics_id, int player_id, int count_of_matches, int count_of_min_per_match, int goals,
                            int assists, Integer missed_goals, Integer clean_sheets,
                            int yellow_cards, int red_cards)
    {
        super(count_of_matches, count_of_min_per_match, goals, assists, missed_goals, clean_sheets, yellow_cards, red_cards);
        this.player_statistics_id = player_statistics_id;
        this.player_id = player_id;
    }

    public int getPlayer_statistics_id() {
        return player_statistics_id;
    }

    public void setPlayer_statistics_id(int player_statistics_id) {
        this.player_statistics_id = player_statistics_id;
    }

    public int getPlayer_id() {
        return player_id;
    }

    public void setPlayer_id(int player_id) {
        this.player_id = player_id;
    }
}
