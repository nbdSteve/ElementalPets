package gg.steve.elemental.pets.api;

import gg.steve.elemental.pets.Pets;
import gg.steve.elemental.pets.core.Pet;
import gg.steve.elemental.pets.core.PetType;
import gg.steve.elemental.pets.core.PlayerPetManager;
import org.bukkit.entity.Player;

import java.util.Map;

public class PetApi {

    public static Pets getInstance() {
        return Pets.get();
    }

    public static boolean isPetActive(Player player, PetType type) {
        return PlayerPetManager.isPetActive(player.getUniqueId(), type);
    }

    public static Pet getActivePet(Player player, PetType type) {
        return PlayerPetManager.getActivePet(player.getUniqueId(), type);
    }

    public static Map<PetType, Pet> getActivePets(Player player) {
        return PlayerPetManager.getActivePets(player.getUniqueId());
    }
}