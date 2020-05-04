package gg.steve.elemental.pets.data;

import gg.steve.elemental.pets.rarity.PetRarity;
import org.bukkit.configuration.ConfigurationSection;

import java.util.HashMap;
import java.util.Map;

public class PetData {
    private Map<PetRarity, Double> procChances;

    public PetData(ConfigurationSection data) {
        procChances = new HashMap<>();
        for (String rarity : data.getKeys(false)) {
            if (rarity.equalsIgnoreCase("type")) continue;
            procChances.put(PetRarity.valueOf(rarity.toUpperCase()), data.getDouble(rarity));
        }
    }

    public boolean isProcing(PetRarity rarity) {
        return Math.random() * 1 < procChances.get(rarity);
    }
}
