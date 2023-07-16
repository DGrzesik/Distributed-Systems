package com.restservice.models;

public class CurrencyDetails {
    private String name;
    private String description;
    private Double price;
    private Long marketCap;


    public CurrencyDetails(String name, String description, Double price, Long marketCap) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.marketCap = marketCap;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Long getMarketCap() {
        return marketCap;
    }

    public void setMarketCap(Long marketCap) {
        this.marketCap = marketCap;
    }
}
