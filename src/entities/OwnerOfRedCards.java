package entities;

public class OwnerOfRedCards extends Footballer{
    private int red_cards;

    public OwnerOfRedCards(int place, String surname_of_player, String name_of_player, String club_name,
                              String position, int count_of_matches, int red_cards)
    {
        super(place, surname_of_player, name_of_player, club_name, position, count_of_matches);
        this.red_cards = red_cards;
    }

    public int getRed_cards() {
        return red_cards;
    }

    public void setRed_cards(int red_cards) {
        this.red_cards = red_cards;
    }
}
