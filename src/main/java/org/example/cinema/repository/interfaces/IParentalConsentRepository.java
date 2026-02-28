package org.example.cinema.repository.interfaces;

import org.example.cinema.domain.ParentalConsent;

import java.util.List;

public interface IParentalConsentRepository {
    List<ParentalConsent> getParentalConsents();
    ParentalConsent getParentalConsent(int parentalConsentID);
}
