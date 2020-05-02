package gg.steve.elemental.pets.config;

import org.bukkit.configuration.file.YamlConfiguration;

public enum Files {
    CONFIG("pets.yml");

    private final String file;

    Files(String file) {
        this.file = file;
    }

    public void load(FileManager fileManager) {
        fileManager.add(name(), this.file);
    }

    public YamlConfiguration get() {
        return FileManager.get(name());
    }

    public void save() {
        FileManager.save(name());
    }

    public static void reload() {
        FileManager.reload();
    }
}
