package org.example.cinema.service;

import org.example.cinema.domain.Pricing;
import org.example.cinema.repository.interfaces.IPricingRepository;

import java.util.List;

public class PricingService {
    private final IPricingRepository pricingRepository;

    public PricingService(IPricingRepository pricingRepository) {
        this.pricingRepository = pricingRepository;
    }

    public List<Pricing> getPricings() {
        return pricingRepository.getPricings();
    }

    public Pricing getPricing(int clientTypeID, int screeningTypeID) {
        return pricingRepository.getPricing(clientTypeID, screeningTypeID);
    }

    public Pricing getPricing(int pricingID) {
        return pricingRepository.getPricing(pricingID);
    }
}