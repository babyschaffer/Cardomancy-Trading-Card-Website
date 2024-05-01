package com.techelevator.model;

public class CollectionsDto {
    private int collectionId;
    private String username;
    private int tcgId;
    private String collectionName;
    private int quantity;

    public CollectionsDto() {
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getCollectionId() {
        return collectionId;
    }

    public void setCollectionId(int collectionId) {
        this.collectionId = collectionId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getTcgId() {
        return tcgId;
    }

    public void setTcgId(int tcgId) {
        this.tcgId = tcgId;
    }

    public String getCollectionName() {
        return collectionName;
    }

    public void setCollectionName(String collectionName) {
        this.collectionName = collectionName;
    }
}
