package entities;

public class Goalkeeper extends Footballer{
    private int clean_sheets;

    public Goalkeeper(int place, String surname_of_player, String name_of_player, String club_name,
                      String position, int count_of_matches, int clean_sheets)
    {
        super(place, surname_of_player, name_of_player, club_name, position, count_of_matches);
        this.clean_sheets = clean_sheets;
    }

    public int getClean_sheets() {
        return clean_sheets;
    }

    public void setClean_sheets(int clean_sheets) {
        this.clean_sheets = clean_sheets;
    }
}
