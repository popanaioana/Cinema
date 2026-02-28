package org.example.cinema.repository.interfaces;

import org.example.cinema.domain.Pricing;

import java.util.List;

public interface IPricingRepository {
    List<Pricing> getPricings();
    Pricing getPricing(int clientTypeID, int screeningTypeID);
    Pricing getPricing(int pricingID);
}
