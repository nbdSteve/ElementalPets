package gg.steve.elemental.pets.api;

import gg.steve.elemental.pets.Pets;
import org.bukkit.entity.Player;

public class PetApi {

    public static Pets getInstace() {
        return Pets.get();
    }

    public static boolean isPetActive(Player player) {
        return false;
    }

    public static int getPetSlot(Player player) {
        if (!isPetActive(player)) return -1;
        return 0;
    }
}