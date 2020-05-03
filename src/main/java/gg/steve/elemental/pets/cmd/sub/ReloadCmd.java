package gg.steve.elemental.pets.cmd.sub;

import gg.steve.elemental.pets.Pets;
import gg.steve.elemental.pets.config.Files;
import gg.steve.elemental.pets.message.CommandDebug;
import gg.steve.elemental.pets.message.MessageType;
import gg.steve.elemental.pets.permission.PermissionNode;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

public class ReloadCmd {

    public static void reload(CommandSender sender) {
        if (!PermissionNode.RELOAD.hasPermission(sender)) {
            CommandDebug.INSUFFICIENT_PERMISSION.message(sender, PermissionNode.RELOAD.get());
            return;
        }
        Files.reload();
        Bukkit.getPluginManager().disablePlugin(Pets.get());
        Bukkit.getPluginManager().enablePlugin(Pets.get());
        MessageType.RELOAD.message(sender);
    }
}
