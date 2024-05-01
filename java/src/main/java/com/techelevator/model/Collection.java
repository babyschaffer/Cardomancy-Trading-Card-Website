package com.techelevator.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

public class Collection {
    private int id;
    @NotBlank
    private String name;

    @Positive
    private int tcgId;

    public Collection(int id, String name, int tcgId) {
        this.id = id;
        this.name = name;
        this.tcgId = tcgId;
    }

    public Collection() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTcgId() {
        return tcgId;
    }

    public void setTcgId(int tcgId) {
        this.tcgId = tcgId;
    }


}
