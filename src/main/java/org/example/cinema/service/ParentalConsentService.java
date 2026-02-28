package org.example.cinema.service;

import org.example.cinema.domain.ParentalConsent;
import org.example.cinema.repository.db.ParentalConsentRepository;

import java.util.List;

public class ParentalConsentService {
    private ParentalConsentRepository parentalConsentRepository;

    public ParentalConsentService(ParentalConsentRepository parentalConsentRepository) {
        this.parentalConsentRepository = parentalConsentRepository;
    }

    public List<ParentalConsent> getParentalConsents() {
        return parentalConsentRepository.getParentalConsents();
    }

    public ParentalConsent getParentalConsent(int parentalConsentID) {
        return parentalConsentRepository.getParentalConsent(parentalConsentID);
    }
}
