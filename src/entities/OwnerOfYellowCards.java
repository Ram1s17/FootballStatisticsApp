package entities;

public class OwnerOfYellowCards extends Footballer{
    private int yellow_cards;

    public OwnerOfYellowCards(int place, String surname_of_player, String name_of_player, String club_name,
                      String position, int count_of_matches, int yellow_cards)
    {
        super(place, surname_of_player, name_of_player, club_name, position, count_of_matches);
        this.yellow_cards = yellow_cards;
    }

    public int getYellow_cards() {
        return yellow_cards;
    }

    public void setYellow_cards(int yellow_cards) {
        this.yellow_cards = yellow_cards;
    }
}
