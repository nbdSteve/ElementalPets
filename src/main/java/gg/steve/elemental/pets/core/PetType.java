package gg.steve.elemental.pets.core;

import gg.steve.elemental.pets.config.Files;

public enum PetType {
    MONEY(Files.CONFIG.get().getDouble("money-boost")),
    FORTUNE(Files.CONFIG.get().getDouble("fortune-boost"));

    private final double booster;

    PetType(Double booster) {
        this.booster = booster;
    }

    public double getBoostAmount() {
        return this.booster;
    }
}
