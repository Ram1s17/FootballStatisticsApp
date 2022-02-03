package entities;

public class FootballClub {
    private String club_name;
    private String league_name;
    private int count_of_players;
    private String surname_of_coach;
    private String name_of_coach;

    public FootballClub(String club_name, String league_name, int count_of_players,
                        String surname_of_coach, String name_of_coach) {
        this.club_name = club_name;
        this.league_name = league_name;
        this.count_of_players = count_of_players;
        this.surname_of_coach = surname_of_coach;
        this.name_of_coach = name_of_coach;
    }

    public String getClub_name() {
        return club_name;
    }

    public void setClub_name(String club_name) {
        this.club_name = club_name;
    }

    public String getLeague_name() {
        return league_name;
    }

    public void setLeague_name(String league_name) {
        this.league_name = league_name;
    }

    public int getCount_of_players() {
        return count_of_players;
    }

    public void setCount_of_players(int count_of_players) {
        this.count_of_players = count_of_players;
    }

    public String getSurname_of_coach() {
        return surname_of_coach;
    }

    public void setSurname_of_coach(String surname_of_coach) {
        this.surname_of_coach = surname_of_coach;
    }

    public String getName_of_coach() {
        return name_of_coach;
    }

    public void setName_of_coach(String name_of_coach) {
        this.name_of_coach = name_of_coach;
    }
}
