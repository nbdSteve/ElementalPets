package gg.steve.elemental.pets.listener;

import gg.steve.elemental.pets.nbt.NBTItem;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class BlockPlaceListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void playerPlace(PlayerInteractEvent event) {
        if (!event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) return;
        if (event.getItem() == null || event.getItem().getType().equals(Material.AIR)) return;
        NBTItem nbtItem = new NBTItem(event.getItem());
        if (nbtItem.getString("pets.id").equalsIgnoreCase("")) return;
        event.setCancelled(true);
    }
}
