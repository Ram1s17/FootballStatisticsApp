package entities;

public class PlayerStatistics {
    private int count_of_matches;
    private int count_of_min_per_match;
    private int goals;
    private int assists;
    private Integer missed_goals;
    private Integer clean_sheets;
    private int yellow_cards;
    private int red_cards;

    public PlayerStatistics(int count_of_matches, int count_of_min_per_match, int goals,
                            int assists, Integer missed_goals, Integer clean_sheets,
                            int yellow_cards, int red_cards)
    {
        this.count_of_matches = count_of_matches;
        this.count_of_min_per_match  = count_of_min_per_match;
        this.goals = goals;
        this.assists = assists;
        this.missed_goals = missed_goals;
        this.clean_sheets = clean_sheets;
        this.yellow_cards = yellow_cards;
        this.red_cards = red_cards;
    }

    public int getCount_of_matches() {
        return count_of_matches;
    }

    public void setCount_of_matches(int count_of_matches) {
        this.count_of_matches = count_of_matches;
    }

    public int getCount_of_min_per_match() {
        return count_of_min_per_match;
    }

    public void setCount_of_min_per_match(int count_of_min_per_match) {
        this.count_of_min_per_match = count_of_min_per_match;
    }

    public int getGoals() {
        return goals;
    }

    public void setGoals(int goals) {
        this.goals = goals;
    }

    public int getAssists() {
        return assists;
    }

    public void setAssists(int assists) {
        this.assists = assists;
    }

    public Integer getMissed_goals() {
        return missed_goals;
    }

    public void setMissed_goals(Integer missed_goals) {
        this.missed_goals = missed_goals;
    }

    public Integer getClean_sheets() {
        return clean_sheets;
    }

    public void setClean_sheets(Integer clean_sheets) {
        this.clean_sheets = clean_sheets;
    }

    public int getYellow_cards() {
        return yellow_cards;
    }

    public void setYellow_cards(int yellow_cards) {
        this.yellow_cards = yellow_cards;
    }

    public int getRed_cards() {
        return red_cards;
    }

    public void setRed_cards(int red_cars) {
        this.red_cards = red_cars;
    }
}
