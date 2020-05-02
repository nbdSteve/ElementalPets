package gg.steve.elemental.pets.cmd;

import gg.steve.elemental.pets.core.Pet;
import gg.steve.elemental.pets.core.PetManager;
import gg.steve.elemental.pets.rarity.PetRarity;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PetCmd implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("pet")) {
            Player player = (Player) sender;
            Pet pet = PetManager.getPetByName(args[0]);
            pet.givePet(player, PetRarity.LEGENDARY);
        }
        return false;
    }
}
