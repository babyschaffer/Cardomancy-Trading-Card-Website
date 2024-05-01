package com.techelevator.model;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CardDto {
    @NotBlank
    private String id;
    private int tcgId;
    @NotBlank
    private String name;
    //image url is of full card
    @NotBlank
    private String imageUrl;
    private String smallImgUrl;
    //url takes you to full page card notation on scryfall

    private String scryfallUrl;
    private String reverseImgUrl;
    private String smallReverseImgUrl;
    private List<String> colors = new ArrayList<>();
    private List<String> colorIdentity = new ArrayList<>();

    private String setCode;

    private String setName;

    private String collectorNumber;

    private Map<String, String> legalities;
    private String layout;
    private double cmc;
    private int edhrecRank;

    private int qty;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getTcgId() {
        return tcgId;
    }

    public void setTcgId(int tcgId) {
        this.tcgId = tcgId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getSmallImgUrl() {
        return smallImgUrl;
    }

    public void setSmallImgUrl(String smallImgUrl) {
        this.smallImgUrl = smallImgUrl;
    }

    public String getScryfallUrl() {
        return scryfallUrl;
    }

    public void setScryfallUrl(String scryfallUrl) {
        this.scryfallUrl = scryfallUrl;
    }

    public String getReverseImgUrl() {
        return reverseImgUrl;
    }

    public void setReverseImgUrl(String reverseImgUrl) {
        this.reverseImgUrl = reverseImgUrl;
    }

    public String getSmallReverseImgUrl() {
        return smallReverseImgUrl;
    }

    public void setSmallReverseImgUrl(String smallReverseImgUrl) {
        this.smallReverseImgUrl = smallReverseImgUrl;
    }

    public List<String> getColors() {
        return colors;
    }

    public void setColors(List<String> colors) {
        this.colors = colors;
    }

    public List<String> getColorIdentity() {
        return colorIdentity;
    }

    public void setColorIdentity(List<String> colorIdentity) {
        this.colorIdentity = colorIdentity;
    }

    public String getSetCode() {
        return setCode;
    }

    public void setSetCode(String setCode) {
        this.setCode = setCode;
    }

    public String getSetName() {
        return setName;
    }

    public void setSetName(String setName) {
        this.setName = setName;
    }

    public String getCollectorNumber() {
        return collectorNumber;
    }

    public void setCollectorNumber(String collectorNumber) {
        this.collectorNumber = collectorNumber;
    }

    public Map<String, String> getLegalities() {
        return legalities;
    }

    public void setLegalities(Map<String, String> legalities) {
        this.legalities = legalities;
    }

    public String getLayout() {
        return layout;
    }

    public void setLayout(String layout) {
        this.layout = layout;
    }

    public double getCmc() {
        return cmc;
    }

    public void setCmc(double cmc) {
        this.cmc = cmc;
    }

    public int getEdhrecRank() {
        return edhrecRank;
    }

    public void setEdhrecRank(int edhrecRank) {
        this.edhrecRank = edhrecRank;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }
}
