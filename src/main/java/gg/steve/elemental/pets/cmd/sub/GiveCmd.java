package gg.steve.elemental.pets.cmd.sub;

import gg.steve.elemental.pets.core.Pet;
import gg.steve.elemental.pets.core.PetManager;
import gg.steve.elemental.pets.core.PlayerPetManager;
import gg.steve.elemental.pets.message.CommandDebug;
import gg.steve.elemental.pets.message.MessageType;
import gg.steve.elemental.pets.permission.PermissionNode;
import gg.steve.elemental.pets.rarity.PetRarity;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GiveCmd {

    public static void give(CommandSender sender, String[] args) {
        // /pet give nbdsteve money legendary
        if (args.length < 4) {
            CommandDebug.INVALID_NUMBER_OF_ARGUMENTS.message(sender);
            return;
        }
        if (!PermissionNode.GIVE.hasPermission(sender)) {
            CommandDebug.INSUFFICIENT_PERMISSION.message(sender, PermissionNode.GIVE.get());
            return;
        }
        Player target = Bukkit.getPlayer(args[1]);
        if (target == null) {
            CommandDebug.TARGET_NOT_ONLINE.message(sender);
            return;
        }
        Pet pet;
        if ((pet = PetManager.getPetByName(args[2].toLowerCase())) == null) {
            CommandDebug.INVALID_PET.message(sender);
            return;
        }
        PetRarity rarity;
        try {
            rarity = PetRarity.valueOf(args[3].toUpperCase());
        } catch (Exception e) {
            CommandDebug.INVALID_RARITY.message(sender);
            return;
        }
        Player player = null;
        if (sender instanceof Player) {
            if (!target.getUniqueId().equals(((Player) sender).getUniqueId())) {
                player = (Player) sender;
            }
        }
        if (PlayerPetManager.isPetActive(target.getUniqueId(), pet.getType()) || target.getInventory().firstEmpty() == -1) {
            target.getWorld().dropItem(target.getLocation(), pet.getItem(rarity));
            MessageType.GIVE_RECEIVER_ERROR.message(target, pet.getRarityPrefixes().get(rarity), args[2]);
        } else {
            pet.givePet(target, rarity);
            PlayerPetManager.addPetToPlayer(target.getUniqueId(), pet, rarity);
            MessageType.GIVE_RECEIVER.message(target, pet.getRarityPrefixes().get(rarity), args[2]);
        }
        if (!(sender instanceof Player) || player != null) {
            MessageType.GIVE_GIVER.message(sender, target.getName(), pet.getRarityPrefixes().get(rarity), args[2]);
        }
    }
}
