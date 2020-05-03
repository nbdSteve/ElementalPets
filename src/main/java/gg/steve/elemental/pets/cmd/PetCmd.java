package gg.steve.elemental.pets.cmd;

import gg.steve.elemental.pets.cmd.sub.GiveCmd;
import gg.steve.elemental.pets.cmd.sub.HelpCmd;
import gg.steve.elemental.pets.cmd.sub.ReloadCmd;
import gg.steve.elemental.pets.message.CommandDebug;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class PetCmd implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("pet")) {
            if (args.length == 0) {
                HelpCmd.help(sender);
                return true;
            }
            switch (args[0]) {
                case "help":
                case "h":
                    HelpCmd.help(sender);
                    break;
                case "reload":
                case "r":
                    ReloadCmd.reload(sender);
                    break;
                case "give":
                case "g":
                    GiveCmd.give(sender, args);
                    break;
                default:
                    CommandDebug.INVALID_COMMAND.message(sender);
                    break;
            }
        }
        return true;
    }
}
