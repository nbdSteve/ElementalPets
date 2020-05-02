package gg.steve.elemental.pets.listener;

import gg.steve.elemental.pets.core.Pet;
import gg.steve.elemental.pets.core.PetManager;
import gg.steve.elemental.pets.nbt.NBTItem;
import gg.steve.elemental.pets.utils.LogUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupItemEvent;

import java.util.UUID;

public class AddPetListener implements Listener {

    @EventHandler
    public void itemPickUp(PlayerPickupItemEvent event) {
        if (event.isCancelled()) return;
        if (event.getItem() == null || event.getItem().getItemStack() == null) return;
        Player player = event.getPlayer();
        NBTItem nbtItem = new NBTItem(event.getItem().getItemStack());
        if (nbtItem.getString("pets.id") == null) return;
        Pet pet = PetManager.getPet(UUID.fromString(nbtItem.getString("pets.id")));
        for (Pet active : PetManager.getActivePets(player)) {
            if (active.isType(pet.getData().keySet())) event.setCancelled(true);
        }
    }
}
