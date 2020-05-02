package gg.steve.elemental.pets.api;

import gg.steve.elemental.pets.Pets;
import gg.steve.elemental.pets.core.Pet;
import gg.steve.elemental.pets.core.PetManager;
import gg.steve.elemental.pets.data.PetDataType;
import org.bukkit.entity.Player;

import java.util.List;

public class PetApi {

    public static Pets getInstace() {
        return Pets.get();
    }

    public static boolean isPetActive(Player player, PetDataType type) {
        return PetManager.isPetActive(player, type);
    }

    public static Pet getActivePet(Player player, PetDataType type) {
        return PetManager.getActivePet(player, type);
    }

    public static List<Pet> getActivePets(Player player) {
        return PetManager.getActivePets(player);
    }
}