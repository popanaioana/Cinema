package org.example.cinema.domain;

public class Pricing {
    private int PriceID;
    private int ClientTypeID;
    private int ScreeningTypeID;
    private double Price;

    public int getPriceID() {
        return PriceID;
    }

    public Pricing(int PriceID, int ClientTypeID, int ScreeningTypeID, double Price) {
        this.PriceID = PriceID;
        this.ClientTypeID = ClientTypeID;
        this.ScreeningTypeID = ScreeningTypeID;
        this.Price = Price;
    }

    public double getPrice() {
        return Price;
    }
}
