package com.mertalptekin.springrestapplication.sample.model;

// veritanbanı nesnesi

import java.time.LocalDate;

public class User {

    private Integer id;
    private String name;
    private LocalDate birthday;

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
