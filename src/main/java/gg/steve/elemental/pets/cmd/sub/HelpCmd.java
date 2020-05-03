package gg.steve.elemental.pets.cmd.sub;

import gg.steve.elemental.pets.message.CommandDebug;
import gg.steve.elemental.pets.message.MessageType;
import gg.steve.elemental.pets.permission.PermissionNode;
import org.bukkit.command.CommandSender;

public class HelpCmd {

    public static void help(CommandSender sender) {
        if (!PermissionNode.HELP.hasPermission(sender)) {
            CommandDebug.INSUFFICIENT_PERMISSION.message(sender, PermissionNode.HELP.get());
            return;
        }
        MessageType.HELP.message(sender);
    }
}
