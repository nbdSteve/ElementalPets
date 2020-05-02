package gg.steve.elemental.pets;

import gg.steve.elemental.pets.config.FileManager;
import gg.steve.elemental.pets.config.SetupManager;
import gg.steve.elemental.pets.utils.LogUtil;
import org.bukkit.plugin.java.JavaPlugin;

public final class Pets extends JavaPlugin {
    private static Pets instance;

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;
        LogUtil.setInstance(instance);
        SetupManager.setupFiles(new FileManager(instance));
        SetupManager.registerCommands(instance);
        SetupManager.registerEvents(instance);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static Pets get() {
        return instance;
    }
}
