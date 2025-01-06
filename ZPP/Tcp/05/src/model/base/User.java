package model.base;

import java.io.Serializable;
import java.time.LocalDateTime;

public class User implements Serializable {
    private String name, surname;
    private LocalDateTime date = LocalDateTime.now();

    public User(String name, String surname) {
        this.name = name;
        this.surname = surname;
    }

    @Override
    public String toString() {
        return "User [name=" + name + ", surname=" + surname + ", date=" + date + "]";
    }
}
