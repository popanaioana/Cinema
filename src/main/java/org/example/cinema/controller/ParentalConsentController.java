package org.example.cinema.controller;

import org.example.cinema.domain.ParentalConsent;
import org.example.cinema.service.ParentalConsentService;

import java.util.List;

public class ParentalConsentController {
    private final ParentalConsentService parentalConsentService;

    public ParentalConsentController(ParentalConsentService parentalConsentService) {
        this.parentalConsentService = parentalConsentService;
    }

    public List<ParentalConsent> handleGetParentalConsents() {
        return parentalConsentService.getParentalConsents();
    }

    public ParentalConsent handleGetParentalConsent(int parentalConsentID) {
        return parentalConsentService.getParentalConsent(parentalConsentID);
    }
}