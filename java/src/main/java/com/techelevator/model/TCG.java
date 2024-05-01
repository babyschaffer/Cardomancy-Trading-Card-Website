package com.techelevator.model;

import javax.validation.constraints.NotBlank;

public class TCG {
    private int id;
    @NotBlank
    private String name;

    public TCG() {
    }

/*
Full constructor used for testing purposes.
 */

    public TCG(String name) {
        this.name = name;
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
}
