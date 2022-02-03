package entities;

public class League {
    private String league_name;
    private int count_of_teams;
    private int count_of_tours_played;
    private String country;

    public League(String league_name, int count_of_teams, int count_of_tours_played, String country)
    {
        this.league_name = league_name;
        this.count_of_teams = count_of_teams;
        this.count_of_tours_played = count_of_tours_played;
        this.country = country;
    }

    public String getLeague_name() {
        return league_name;
    }

    public void setLeague_name(String league_name) {
        this.league_name = league_name;
    }

    public int getCount_of_teams() {
        return count_of_teams;
    }

    public void setCount_of_teams(int count_of_teams) {
        this.count_of_teams = count_of_teams;
    }

    public int getCount_of_tours_played() {
        return count_of_tours_played;
    }

    public void setCount_of_tours_played(int count_of_tours_played) {
        this.count_of_tours_played = count_of_tours_played;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
