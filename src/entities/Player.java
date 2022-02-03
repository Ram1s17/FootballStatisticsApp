package entities;

import java.time.LocalDate;

public class Player {
    private String surname_of_player;
    private String name_of_player;
    private LocalDate date_of_birth;
    private String nationality;
    private String club_name;
    private int jersey_number;
    private String position;

    public  Player(String surname_of_player, String name_of_player, LocalDate date_of_birth,
                   String nationality, String club_name, int jersey_number, String position)
    {
        this.surname_of_player = surname_of_player;
        this.name_of_player = name_of_player;
        this.date_of_birth = date_of_birth;
        this.nationality = nationality;
        this.club_name = club_name;
        this.jersey_number = jersey_number;
        this.position = position;
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

    public LocalDate getDate_of_birth() {
        return date_of_birth;
    }

    public void setDate_of_birth(LocalDate date_of_birth) {
        this.date_of_birth = date_of_birth;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getClub_name() {
        return club_name;
    }

    public void setClub_name(String club_name) {
        this.club_name = club_name;
    }

    public int getJersey_number() {
        return jersey_number;
    }

    public void setJersey_number(int jersey_number) {
        this.jersey_number = jersey_number;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
}
