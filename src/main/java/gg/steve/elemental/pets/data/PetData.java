package gg.steve.elemental.pets.data;

import org.bukkit.event.block.BlockBreakEvent;

public interface PetData {
    void onSell();
    void onMine(BlockBreakEvent event);
}
