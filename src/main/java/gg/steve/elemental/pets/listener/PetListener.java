package gg.steve.elemental.pets.listener;

import gg.steve.elemental.pets.core.Pet;
import gg.steve.elemental.pets.core.PetManager;
import gg.steve.elemental.pets.core.PlayerPetManager;
import gg.steve.elemental.pets.nbt.NBTItem;
import gg.steve.elemental.pets.utils.LogUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;

import java.util.UUID;

public class PetListener implements Listener {

    @EventHandler
    public void itemPickUp(PlayerPickupItemEvent event) {
        if (event.isCancelled()) return;
        Player player = event.getPlayer();
        NBTItem nbtItem = new NBTItem(event.getItem().getItemStack());
        if (nbtItem.getString("pets.id") == null) return;
        Pet pet = PetManager.getPet(UUID.fromString(nbtItem.getString("pets.id")));
        if (PlayerPetManager.isPetActive(player.getUniqueId(), pet.getType())) {
            event.setCancelled(true);
            return;
        }
        PlayerPetManager.addPetToPlayer(player.getUniqueId(), pet);
        LogUtil.info(player.getName() + " picked up pet item, " + pet.getType() + " pet has been added to player.");
    }

    @EventHandler
    public void itemDrop(PlayerDropItemEvent event) {
        if (event.isCancelled()) return;
        Player player = event.getPlayer();
        NBTItem nbtItem = new NBTItem(event.getItemDrop().getItemStack());
        if (nbtItem.getString("pets.id") == null) return;
        Pet pet = PetManager.getPet(UUID.fromString(nbtItem.getString("pets.id")));
        PlayerPetManager.removePetFromPlayer(player.getUniqueId(), pet.getType());
        LogUtil.info(player.getName() + " dropped pet item, " + pet.getType() + " pet has been removed from player.");
    }

    @EventHandler
    public void playerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        PlayerPetManager.removePlayer(player.getUniqueId());
        LogUtil.info(player.getName() + " died, all pets have been removed.");
    }

    @EventHandler
    public void inventoryAdd(InventoryClickEvent event) {
        if (event.isCancelled()) return;
        if (!(event.getWhoClicked() instanceof Player)) return;
        if (event.getCursor() == null || event.getCursor().getType().equals(Material.AIR)) return;
        Player player = (Player) event.getWhoClicked();
        if (event.getClickedInventory() == null || !event.getClickedInventory().equals(player.getInventory()))
            return;
        NBTItem nbtItem = new NBTItem(event.getCursor());
        if (nbtItem.getString("pets.id") == null) return;
        Pet pet = PetManager.getPet(UUID.fromString(nbtItem.getString("pets.id")));
        if (PlayerPetManager.isPetActive(player.getUniqueId(), pet.getType())) {
            event.setCancelled(true);
            return;
        }
        PlayerPetManager.addPetToPlayer(player.getUniqueId(), pet);
        LogUtil.info(player.getName() + " added pet item to their inventory, " + pet.getType() + " pet has been added to player.");
    }

    @EventHandler
    public void inventoryShift(InventoryClickEvent event) {
        if (event.isCancelled()) return;
        if (!(event.getWhoClicked() instanceof Player)) return;
        if (event.getCurrentItem() == null || event.getCurrentItem().getType().equals(Material.AIR)) return;
        Player player = (Player) event.getWhoClicked();
        if (event.getClickedInventory() == null || event.getClickedInventory().equals(player.getInventory()))
            return;
        NBTItem nbtItem = new NBTItem(event.getCurrentItem());
        if (nbtItem.getString("pets.id") == null) return;
        Pet pet = PetManager.getPet(UUID.fromString(nbtItem.getString("pets.id")));
        if (PlayerPetManager.isPetActive(player.getUniqueId(), pet.getType())) {
            event.setCancelled(true);
            return;
        }
        PlayerPetManager.addPetToPlayer(player.getUniqueId(), pet);
        LogUtil.info(player.getName() + " added pet item to their inventory, " + pet.getType() + " pet has been added to player.");
    }

    @EventHandler
    public void inventoryRemove(InventoryClickEvent event) {
        if (event.isCancelled()) return;
        if (!(event.getWhoClicked() instanceof Player)) return;
        if (event.getCurrentItem() == null || event.getCurrentItem().getType().equals(Material.AIR)) return;
        Player player = (Player) event.getWhoClicked();
        if (!event.getClickedInventory().equals(player.getInventory())) return;
        NBTItem nbtItem = new NBTItem(event.getCurrentItem());
        if (nbtItem.getString("pets.id") == null) return;
        Pet pet = PetManager.getPet(UUID.fromString(nbtItem.getString("pets.id")));
        PlayerPetManager.removePetFromPlayer(player.getUniqueId(), pet.getType());
        LogUtil.info(player.getName() + " removed pet item from their inventory, " + pet.getType() + " pet has been removed from player.");
    }
}
