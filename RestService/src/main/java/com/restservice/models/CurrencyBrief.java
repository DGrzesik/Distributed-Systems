package com.restservice.models;

public class CurrencyBrief {

    private String name;
    private Double price;
    private Double priceChangePercentage24h;
    private Integer priceRank;
    private Integer priceChangePercentage24hRank;

    public CurrencyBrief(String name, Double price, Double priceChangePercentage24h) {
        this.name = name;
        this.price = price;
        this.priceChangePercentage24h = priceChangePercentage24h;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Double getPriceChangePercentage24h() {
        return priceChangePercentage24h;
    }

    public void setPriceChangePercentage24h(Double rank) {
        this.priceChangePercentage24h = rank;
    }

    public Integer getPriceRank() {
        return priceRank;
    }

    public void setPriceRank(Integer rankPrice) {
        this.priceRank = rankPrice;
    }

    public Integer getPriceChangePercentage24hRank() {
        return priceChangePercentage24hRank;
    }

    public void setPriceChangePercentage24hRank(Integer priceChangePercentage24hRank) {
        this.priceChangePercentage24hRank = priceChangePercentage24hRank;
    }
}
