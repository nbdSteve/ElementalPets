package gg.steve.elemental.pets.listener;

import gg.steve.elemental.pets.core.Pet;
import gg.steve.elemental.pets.core.PetManager;
import gg.steve.elemental.pets.core.PlayerPetManager;
import gg.steve.elemental.pets.nbt.NBTItem;
import gg.steve.elemental.pets.utils.LogUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;

import java.util.List;
import java.util.UUID;

/**
 * Listener class that blocks players from having more than 1 pet in the inventory.
 */
public class PetListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void itemPickUp(PlayerPickupItemEvent event) {
        Player player = event.getPlayer();
        NBTItem nbtItem = new NBTItem(event.getItem().getItemStack());
        if (nbtItem.getString("pets.id").equalsIgnoreCase("")) return;
        Pet pet = PetManager.getPet(UUID.fromString(nbtItem.getString("pets.id")));
        if (PlayerPetManager.isPetActive(player.getUniqueId(), pet.getType())) {
            event.setCancelled(true);
            return;
        }
        PlayerPetManager.addPetToPlayer(player.getUniqueId(), pet);
        LogUtil.debug(player.getName() + " picked up pet item, " + pet.getType() + " pet has been added to player. " + event.getEventName());
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void itemDrop(PlayerDropItemEvent event) {
        Player player = event.getPlayer();
        NBTItem nbtItem = new NBTItem(event.getItemDrop().getItemStack());
        if (nbtItem.getString("pets.id").equalsIgnoreCase("")) return;
        Pet pet = PetManager.getPet(UUID.fromString(nbtItem.getString("pets.id")));
        if (!PlayerPetManager.isPetActive(player.getUniqueId(), pet.getType())) return;
        PlayerPetManager.removePetFromPlayer(player.getUniqueId(), pet.getType());
        // when the player removes a pet from their inventory using drop we want to reset the players active
        // pets to ensure that they cant pick up another pet of the same type
        List<Pet> pets = PetManager.loadPetsFromInventory(player.getInventory());
        if (!pets.isEmpty()) {
            for (Pet invPet : pets) {
                PlayerPetManager.addPetToPlayer(player.getUniqueId(), invPet);
            }
        }
        LogUtil.debug(player.getName() + " dropped pet item, " + pet.getType() + " pet has been removed from player. " + event.getEventName());
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void playerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        PlayerPetManager.removePlayer(player.getUniqueId());
        LogUtil.debug(player.getName() + " died, all pets have been removed. " + event.getEventName());
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void inventoryCheck(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player)) return;
        boolean shift = false, numberkey = false, normal = false;
        if (event.getAction().equals(InventoryAction.NOTHING)) return;
        switch (event.getClick()) {
            case SHIFT_LEFT:
            case SHIFT_RIGHT:
                shift = true;
                break;
            case NUMBER_KEY:
                numberkey = true;
                break;
            case LEFT:
            case RIGHT:
            case MIDDLE:
                normal = true;
                break;
        }
        Pet current = null;
        Pet cursor = null;
        if (event.getCurrentItem() != null && !event.getCurrentItem().getType().equals(Material.AIR)) {
            NBTItem nbtItem = new NBTItem(event.getCurrentItem());
            if (nbtItem.getString("pets.id").equalsIgnoreCase("")) return;
            current = PetManager.getPet(UUID.fromString(nbtItem.getString("pets.id")));
        }
        if (event.getCursor() != null && !event.getCursor().getType().equals(Material.AIR)) {
            NBTItem nbtItem = new NBTItem(event.getCursor());
            if (nbtItem.getString("pets.id").equalsIgnoreCase("")) return;
            cursor = PetManager.getPet(UUID.fromString(nbtItem.getString("pets.id")));
        }
        Player player = (Player) event.getWhoClicked();
        boolean petActive = false;
        boolean playerInv = event.getClickedInventory() != null && event.getClickedInventory().equals(player.getInventory());
        if (current != null && PlayerPetManager.isPetActive(player.getUniqueId(), current.getType())) {
            petActive = true;
        }
        if (cursor != null && PlayerPetManager.isPetActive(player.getUniqueId(), cursor.getType()) && playerInv) {
            event.setCancelled(true);
            LogUtil.debug(player.getName() + " tried to add another " + cursor.getType() + " pet to the inventory, but was blocked ((line ~94))");
            return;
        }
        if (petActive && cursor != null && cursor.isType(current.getType()) && !playerInv) {
            event.setCancelled(true);
            LogUtil.debug(player.getName() + " tried to add another " + current.getType() + " pet to the inventory, but was blocked ((line ~99))");
            return;
        }
        if (normal && playerInv) {
            if (petActive) {
                PlayerPetManager.removePetFromPlayer(player.getUniqueId(), current.getType());
                LogUtil.debug(player.getName() + " removed " + current.getType() + " pet from their inventory using a normal click ((line ~105))");
            }
            if (cursor != null) {
                PlayerPetManager.addPetToPlayer(player.getUniqueId(), cursor);
                LogUtil.debug(player.getName() + " added " + cursor.getType() + " pet to their inventory using a normal click ((line ~109))");
            }
            return;
        }
        if (numberkey && !playerInv) {
            if (petActive) {
                event.setCancelled(true);
                LogUtil.debug(player.getName() + " tried to add another " + current.getType() + " pet to the inventory, but was blocked ((line ~116))");
                return;
            } else if (current != null) {
                PlayerPetManager.addPetToPlayer(player.getUniqueId(), current);
                LogUtil.debug(player.getName() + " added a " + current.getType() + " pet to their inventory ((line ~120))");
            } else if (player.getInventory().getItem(event.getHotbarButton()) != null
                    && !player.getInventory().getItem(event.getHotbarButton()).getType().equals(Material.AIR)) {
                NBTItem nbtItem = new NBTItem(player.getInventory().getItem(event.getHotbarButton()));
                if (nbtItem.getString("pets.id").equalsIgnoreCase("")) return;
                Pet hotbar = PetManager.getPet(UUID.fromString(nbtItem.getString("pets.id")));
                PlayerPetManager.removePetFromPlayer(player.getUniqueId(), hotbar.getType());
                LogUtil.debug(player.getName() + " removed " + hotbar.getType() + " pet from their inventory using hotbar swap, pet removed from player ((line ~127))");
            }
            return;
        }
        if (shift) {
            if (!playerInv) {
                if (petActive) {
                    LogUtil.debug(player.getName() + " tried to add another " + current.getType() + " pet to their inventory using shift click, but was blocked ((line ~133))");
                    event.setCancelled(true);
                    return;
                } else if (current != null) {
                    PlayerPetManager.addPetToPlayer(player.getUniqueId(), current);
                    LogUtil.debug(player.getName() + " added a " + current.getType() + " pet to their inventory using shift click, pet added to player ((line ~138))");
                }
            } else if (playerInv && event.getRawSlot() > 44) {
                PlayerPetManager.removePetFromPlayer(player.getUniqueId(), current.getType());
                LogUtil.debug(player.getName() + " removed " + current.getType() + " pet from their inventory using shift click, pet removed from player ((line ~142))");
            }
        }
    }
}
