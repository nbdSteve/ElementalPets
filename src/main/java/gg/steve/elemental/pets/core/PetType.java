package gg.steve.elemental.pets.core;

import gg.steve.elemental.pets.config.Files;

public enum PetType {
    MONEY(Files.CONFIG.get().getDouble("money-booster")),
    FORTUNE(Files.CONFIG.get().getDouble("fortune-booster"));

    private final double booster;

    PetType(Double booster) {
        this.booster = booster;
    }

    public double getBoostAmount() {
        return booster;
    }
}
