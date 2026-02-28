package org.example.cinema.domain;

public class ParentalConsent {
    private int ParentalConsentID;
    private int Age;

    public ParentalConsent(int parentalConsentID, int age) {
        this.ParentalConsentID = parentalConsentID;
        this.Age = age;
    }

    public int getAge() {
        return Age;
    }

    public int getParentalConsentID() {
        return ParentalConsentID;
    }
}
