package entities;

public class Assistant extends Footballer {
    private int assists;

    public Assistant(int place, String surname_of_player, String name_of_player, String club_name, String position,
                      int count_of_matches, int assists)
    {
        super(place, surname_of_player, name_of_player, club_name, position, count_of_matches);
        this.assists = assists;
    }

    public int getAssists() {
        return assists;
    }

    public void setAssists(int assists) {
        this.assists = assists;
    }
}
