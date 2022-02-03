package entities;

import java.time.LocalDate;

public class PlayerForAdmin extends Player{
    private int player_id;

    public  PlayerForAdmin(int player_id, String surname_of_player, String name_of_player, LocalDate date_of_birth,
                   String nationality, String club_name, int jersey_number, String position)
    {
        super(surname_of_player, name_of_player, date_of_birth, nationality, club_name, jersey_number, position);
        this.player_id = player_id;
    }

    public int getPlayer_id() {
        return player_id;
    }

    public void setPlayer_id(int player_id) {
        this.player_id = player_id;
    }
}
