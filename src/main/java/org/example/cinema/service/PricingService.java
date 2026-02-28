package org.example.cinema.service;

import org.example.cinema.domain.Pricing;
import org.example.cinema.repository.db.PricingDBRepository;

import java.util.List;

public class PricingService {
    private PricingDBRepository pricingDBRepository;

    public PricingService(PricingDBRepository pricingRepository) {
        this.pricingDBRepository = pricingRepository;
    }

    public List<Pricing> getPricings() {
        return pricingDBRepository.getPricings();
    }

    public Pricing getPricing(int clientTypeID, int screeningTypeID) {
        return pricingDBRepository.getPricing(clientTypeID, screeningTypeID);
    }

    public Pricing getPricing(int pricingID) {
        return pricingDBRepository.getPricing(pricingID);
    }
}
