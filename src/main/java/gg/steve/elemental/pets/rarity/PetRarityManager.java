package gg.steve.elemental.pets.rarity;

import gg.steve.elemental.pets.core.Pet;
import org.bukkit.configuration.ConfigurationSection;

import java.util.HashMap;
import java.util.Map;

public class PetRarityManager {

    public static Map<PetRarity, String> loadPetRarityPrefixes(Pet pet) {
        Map<PetRarity, String> rarities = new HashMap<>();
        ConfigurationSection prefix = pet.getConfig().getConfigurationSection("rarity");
        for (String rarity : prefix.getKeys(false)) {
            rarities.put(PetRarity.valueOf(rarity.toUpperCase()), prefix.getString(rarity));
        }
        return rarities;
    }
}
