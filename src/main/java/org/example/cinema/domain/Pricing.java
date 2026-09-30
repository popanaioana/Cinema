package org.example.cinema.domain;

public class Pricing {
    private int priceID;
    private int clientTypeID;
    private int screeningTypeID;
    private double price;

    public Pricing(int priceID, int clientTypeID, int screeningTypeID, double price) {
        this.priceID = priceID;
        this.clientTypeID = clientTypeID;
        this.screeningTypeID = screeningTypeID;
        this.price = price;
    }

    public int getPriceID() {
        return priceID;
    }

    public int getClientTypeID() {
        return clientTypeID;
    }

    public int getScreeningTypeID() {
        return screeningTypeID;
    }

    public double getPrice() {
        return price;
    }
}