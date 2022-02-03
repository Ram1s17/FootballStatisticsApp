package entities;

public class Bombardier extends Footballer {
    private int goals;

    public Bombardier(int place, String surname_of_player, String name_of_player, String club_name, String position,
                      int count_of_matches, int goals)
    {
        super(place, surname_of_player, name_of_player, club_name, position, count_of_matches);
        this.goals = goals;
    }

    public int getGoals() {
        return goals;
    }

    public void setGoals(int goals) {
        this.goals = goals;
    }
}
