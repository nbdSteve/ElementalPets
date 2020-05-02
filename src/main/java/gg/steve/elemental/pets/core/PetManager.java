package gg.steve.elemental.pets.core;

import gg.steve.elemental.pets.Pets;
import gg.steve.elemental.pets.config.Files;
import gg.steve.elemental.pets.utils.LogUtil;
import gg.steve.elemental.pets.utils.YamlFileUtil;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PetManager {
    private static Map<UUID, Pet> activePets;

    public static void loadPets() {
        activePets = new HashMap<>();
        for (String pet : Files.CONFIG.get().getStringList("loaded-pets")) {
            YamlFileUtil fileUtil = new YamlFileUtil("pets" + File.separator + pet + ".yml", Pets.get());
            UUID petId = UUID.fromString(fileUtil.get().getString("pet-id"));
            activePets.put(petId, new Pet(petId, fileUtil));
            LogUtil.info("Successfully loaded " + pet + " pet from configuration.");
        }
    }

    public static Pet getPet(UUID petId) {
        if (!activePets.containsKey(petId)) return null;
        return activePets.get(petId);
    }

    public static Map<UUID, Pet> getPets() {
        return activePets;
    }
}
