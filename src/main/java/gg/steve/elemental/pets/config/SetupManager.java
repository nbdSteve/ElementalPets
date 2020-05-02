package gg.steve.elemental.pets.config;

import gg.steve.elemental.pets.Pets;
import gg.steve.elemental.pets.cmd.PetCmd;
import gg.steve.elemental.pets.core.PlayerPetManager;
import gg.steve.elemental.pets.listener.PetListener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

/**
 * Class that handles setting up the plugin on start
 */
public class SetupManager {

    private SetupManager() throws IllegalAccessException {
        throw new IllegalAccessException("Manager class cannot be instantiated.");
    }

    /**
     * Loads the files into the file manager
     *
     * @param fileManager FileManager, the plugins file manager
     */
    public static void setupFiles(FileManager fileManager) {
        // general files
        for (Files file : Files.values()) {
            file.load(fileManager);
        }
    }

    public static void registerCommands(Pets instance) {
        instance.getCommand("pet").setExecutor(new PetCmd());
//        PaperCommandManager manager = new PaperCommandManager(instance);
//        manager.registerCommand(new CaneCmd());
    }

    /**
     * Register all of the events for the plugin
     *
     * @param instance Plugin, the main plugin instance
     */
    public static void registerEvents(Plugin instance) {
        PluginManager pm = instance.getServer().getPluginManager();
        pm.registerEvents(new PetListener(), instance);
        pm.registerEvents(new PlayerPetManager(), instance);
    }
}
