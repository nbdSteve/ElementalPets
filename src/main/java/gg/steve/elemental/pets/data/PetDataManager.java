package gg.steve.elemental.pets.data;

import gg.steve.elemental.pets.core.Pet;
import gg.steve.elemental.pets.data.types.FortunePetData;
import gg.steve.elemental.pets.data.types.MoneyPetData;
import org.bukkit.configuration.ConfigurationSection;

public class PetDataManager {

    public static PetData loadPetData(Pet pet) {
        ConfigurationSection data = pet.getConfig().getConfigurationSection("data");
        switch (data.getString("type")) {
            case "money":
                return new MoneyPetData();
            case "fortune":
                return new FortunePetData();
        }
        return null;
    }
}
