package gg.steve.elemental.pets.rarity;

public class RarityAttributes {
    private String prefix;
    private int multiplier;

    public RarityAttributes(String prefix, int multiplier) {
        this.prefix = prefix;
        this.multiplier = multiplier;
    }

    public String getPrefix() {
        return prefix;
    }

    public int getMultiplier() {
        return multiplier;
    }
}
