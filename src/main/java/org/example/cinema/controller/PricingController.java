package org.example.cinema.controller;

import org.example.cinema.domain.Pricing;
import org.example.cinema.service.PricingService;

import java.util.List;

public class PricingController {
    private PricingService pricingService;

    public PricingController(PricingService pricingService) {
        this.pricingService = pricingService;
    }

    public List<Pricing> handleGetPricings() {
        return pricingService.getPricings();
    }

    public Pricing handleGetPricing(int clientTypeID, int screeningTypeID) {
        return pricingService.getPricing(clientTypeID, screeningTypeID);
    }

    public Pricing handleGetPricing(int pricingID) {
        return pricingService.getPricing(pricingID);
    }
}
