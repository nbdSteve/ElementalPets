package gg.steve.elemental.pets.core;

import gg.steve.elemental.pets.rarity.PetRarity;
import gg.steve.elemental.pets.utils.LogUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class PlayerPetManager implements Listener {
    private static Map<UUID, Map<PetType, Pet>> petPlayers;
    private static Map<UUID, Map<PetType, PetRarity>> petPlayerRarities;

    public static void initialise() {
        petPlayers = new HashMap<>();
        petPlayerRarities = new HashMap<>();
        if (Bukkit.getOnlinePlayers().isEmpty()) return;
        for (Player player : Bukkit.getOnlinePlayers()) {
            Map<Pet, PetRarity> pets = PetManager.loadPetsFromInventory(player.getInventory());
            if (pets.isEmpty()) return;
            LogUtil.info("Loading pets in " + player.getName() + "'s inventory since the server has been reloaded to the server.");
            for (Pet pet : pets.keySet()) {
                addPetToPlayer(player.getUniqueId(), pet, pets.get(pet));
            }
        }
    }

    public static boolean isPetActive(UUID playerId, PetType type) {
        if (!petPlayers.containsKey(playerId)) return false;
        return petPlayers.get(playerId).containsKey(type);
    }

    public static Pet getActivePet(UUID playerId, PetType type) {
        return petPlayers.get(playerId).get(type);
    }

    public static Map<PetType, Pet> getActivePets(UUID playerId) {
        return petPlayers.get(playerId);
    }

    public static PetRarity getPetRarity(UUID playerId, PetType type) {
        return petPlayerRarities.get(playerId).get(type);
    }

    public static void addPetToPlayer(UUID playerId, Pet pet, PetRarity rarity) {
        if (petPlayers == null) return;
        if (petPlayers.containsKey(playerId)) {
            petPlayers.get(playerId).put(pet.getType(), pet);
            petPlayerRarities.get(playerId).put(pet.getType(), rarity);
            return;
        }
        petPlayers.put(playerId, new HashMap<>());
        petPlayerRarities.put(playerId, new HashMap<>());
        petPlayers.get(playerId).put(pet.getType(), pet);
        petPlayerRarities.get(playerId).put(pet.getType(), rarity);
    }

    public static void removePetFromPlayer(UUID playerId, PetType type) {
        if (petPlayers == null || petPlayers.isEmpty()) return;
        if (!petPlayers.containsKey(playerId)) return;
        petPlayers.get(playerId).remove(type);
        petPlayerRarities.get(playerId).remove(type);
    }

    public static void removePlayer(UUID playerId) {
        if (petPlayers == null || petPlayers.isEmpty()) return;
        if (!petPlayers.containsKey(playerId)) return;
        petPlayers.remove(playerId);
        petPlayerRarities.remove(playerId);
    }

    @EventHandler
    public void playerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        Map<Pet, PetRarity> pets = PetManager.loadPetsFromInventory(player.getInventory());
        if (pets.isEmpty()) return;
        LogUtil.info("Loading pets in " + player.getName() + "'s inventory since they are connecting to the server.");
        for (Pet pet : pets.keySet()) {
            addPetToPlayer(player.getUniqueId(), pet, pets.get(pet));
        }
    }

    @EventHandler
    public void playerQuit(PlayerQuitEvent event) {
        removePlayer(event.getPlayer().getUniqueId());
    }
}
