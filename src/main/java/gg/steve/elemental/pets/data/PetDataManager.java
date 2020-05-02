package gg.steve.elemental.pets.data;

import gg.steve.elemental.pets.core.Pet;
import gg.steve.elemental.pets.data.types.FortunePetData;
import gg.steve.elemental.pets.data.types.MoneyPetData;
import org.bukkit.configuration.ConfigurationSection;

import java.util.HashMap;
import java.util.Map;

public class PetDataManager {

    public static Map<PetDataType, PetData> loadPetData(Pet pet) {
        Map<PetDataType, PetData> petData = new HashMap<>();
        ConfigurationSection data = pet.getConfig().getConfigurationSection("data");
        for (String entry : data.getKeys(false)) {
            switch (data.getString(entry + ".type")) {
                case "money":
                    petData.put(PetDataType.MONEY, new MoneyPetData());
                    break;
                case "fortune":
                    petData.put(PetDataType.FORTUNE, new FortunePetData());
                    break;
            }
        }
        return petData;
    }
}
