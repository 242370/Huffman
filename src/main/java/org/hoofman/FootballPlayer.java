package org.hoofman;

import java.io.Serializable;

public class FootballPlayer implements Serializable {
    private String name;
    private String surname;
    private int number;

    public FootballPlayer(String name, String surname, int number) {
        this.name = name;
        this.surname = surname;
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public int getNumber() {
        return number;
    }
}
