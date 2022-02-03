package entities;

public class Footballer {
    private int place;
    private String surname_of_player;
    private String name_of_player;
    private String club_name;
    private String position;
    private int count_of_matches;

    public Footballer(int place, String surname_of_player, String name_of_player, String club_name, String position, int count_of_matches)
    {
        this.place = place;
        this.surname_of_player = surname_of_player;
        this.name_of_player = name_of_player;
        this.club_name = club_name;
        this.position = position;
        this.count_of_matches = count_of_matches;
    }

    public int getPlace() {
        return place;
    }

    public void setPlace(int place) {
        this.place = place;
    }

    public String getSurname_of_player() {
        return surname_of_player;
    }

    public void setSurname_of_player(String surname_of_player) {
        this.surname_of_player = surname_of_player;
    }

    public String getName_of_player() {
        return name_of_player;
    }

    public void setName_of_player(String name_of_player) {
        this.name_of_player = name_of_player;
    }

    public String getClub_name() {
        return club_name;
    }

    public void setClub_name(String club_name) {
        this.club_name = club_name;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public int getCount_of_matches() {
        return count_of_matches;
    }

    public void setCount_of_matches(int count_of_matches) {
        this.count_of_matches = count_of_matches;
    }
}
