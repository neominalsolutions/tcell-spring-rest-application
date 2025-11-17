package com.mertalptekin.springrestapplication.sample.model;

// veritanbanı nesnesi

import java.time.LocalDate;

public class User {

    private Integer id;
    private String name;
    private LocalDate birthday;

    public  User(LocalDate birthday) {
        this.birthday = birthday;
    }

    public User(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }
}
