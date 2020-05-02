package gg.steve.elemental.pets.core;

import gg.steve.elemental.pets.Pets;
import gg.steve.elemental.pets.config.Files;
import gg.steve.elemental.pets.data.PetData;
import gg.steve.elemental.pets.data.PetDataType;
import gg.steve.elemental.pets.nbt.NBTItem;
import gg.steve.elemental.pets.utils.LogUtil;
import gg.steve.elemental.pets.utils.YamlFileUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.util.*;

public class PetManager {
    private static Map<UUID, Pet> activePets;
    private static Map<String, Pet> petsByName;

    public static void loadPets() {
        activePets = new HashMap<>();
        petsByName = new HashMap<>();
        for (String pet : Files.CONFIG.get().getStringList("loaded-pets")) {
            YamlFileUtil fileUtil = new YamlFileUtil("pets" + File.separator + pet + ".yml", Pets.get());
            UUID petId = UUID.fromString(fileUtil.get().getString("pet-id"));
            activePets.put(petId, new Pet(petId, fileUtil));
            petsByName.put(pet, activePets.get(petId));
            LogUtil.info("Successfully loaded " + pet + " pet from configuration.");
        }
    }

    public static Pet getPet(UUID petId) {
        if (!activePets.containsKey(petId)) return null;
        return activePets.get(petId);
    }

    public static Pet getPetByName(String name) {
        if (!petsByName.containsKey(name)) return null;
        return petsByName.get(name);
    }

    public static Map<UUID, Pet> getPets() {
        return activePets;
    }

    public static boolean isPetActive(Player player, PetDataType type) {
        for (ItemStack item : player.getInventory()) {
            if (item == null || item.getType().equals(Material.AIR)) continue;
            NBTItem nbtItem = new NBTItem(item);
            if (nbtItem.getString("pets.id") == null) continue;
            Pet pet = getPet(UUID.fromString(nbtItem.getString("pets.id")));
            if (pet.getData().containsKey(type)) return true;
        }
        return false;
    }

    public static List<Pet> getActivePets(Player player) {
        List<Pet> pets = new ArrayList<>();
        for (ItemStack item : player.getInventory()) {
            if (item == null || item.getType().equals(Material.AIR)) continue;
            NBTItem nbtItem = new NBTItem(item);
            if (nbtItem.getString("pets.id") == null) continue;
            pets.add(getPet(UUID.fromString(nbtItem.getString("pets.id"))));
        }
        return pets;
    }

    public static Pet getActivePet(Player player, PetDataType type) {
        for (ItemStack item : player.getInventory()) {
            if (item == null || item.getType().equals(Material.AIR)) continue;
            NBTItem nbtItem = new NBTItem(item);
            if (nbtItem.getString("pets.id") == null) continue;
            Pet pet = getPet(UUID.fromString(nbtItem.getString("pets.id")));
            if (pet.getData().containsKey(type)) return pet;
        }
        return null;
    }
}
