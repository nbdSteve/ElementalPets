package gg.steve.elemental.pets.utils;

import org.bukkit.plugin.Plugin;

public class LogUtil {
    private static Plugin instance;
    private static boolean debug = false;

    public static void setInstance(Plugin plugin) {
        instance = plugin;
    }

    public static void info(String message) {
        instance.getLogger().info(message);
    }

    public static void warning(String message) {
        instance.getLogger().warning(message);
    }

    public static void debug(String message) {
        if (debug) info(message);
    }
}
