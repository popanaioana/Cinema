package org.example.cinema.domain;

public class ParentalConsent {
    private int parentalConsentID;
    private int age;

    public ParentalConsent(int parentalConsentID, int age) {
        this.parentalConsentID = parentalConsentID;
        this.age = age;
    }

    public int getParentalConsentID() {
        return parentalConsentID;
    }

    public int getAge() {
        return age;
    }
}