package gg.steve.elemental.pets.core;

import gg.steve.elemental.pets.data.PetData;
import gg.steve.elemental.pets.rarity.PetRarity;
import gg.steve.elemental.pets.rarity.PetRarityManager;
import gg.steve.elemental.pets.utils.ItemBuilderUtil;
import gg.steve.elemental.pets.utils.YamlFileUtil;
import me.arcaniax.hdb.api.HeadDatabaseAPI;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.Map;
import java.util.UUID;

public class Pet {
    private UUID petId;
    private YamlConfiguration config;
    private ItemBuilderUtil itemBuilder;
    private PetType type;
    private PetData data;
    private Map<PetRarity, String> rarityPrefixes;

    public Pet(UUID petId, YamlFileUtil fileUtil) {
        this.petId = petId;
        this.config = fileUtil.get();
        this.type = PetType.valueOf(this.config.getString("data.type").toUpperCase());
        this.rarityPrefixes = PetRarityManager.loadPetRarityPrefixes(this);
        this.data = new PetData(this.config.getConfigurationSection("data"));
        // load some of the item builder so that is doesn't constantly get created in givePet
    }

    public void loadItem() {
        ConfigurationSection item = this.config.getConfigurationSection("item");
        if (item.getString("material").startsWith("hdb")) {
            String[] parts = item.getString("material").split("-");
            itemBuilder = new ItemBuilderUtil(new HeadDatabaseAPI().getItemHead(parts[1]));
        } else {
            itemBuilder = new ItemBuilderUtil(item.getString("material"), item.getString("data"));
        }
        itemBuilder.setLorePlaceholders("{rarity}");
        itemBuilder.addEnchantments(item.getStringList("enchantments"));
        itemBuilder.addItemFlags(item.getStringList("item-flags"));
    }

    public void givePet(Player player, PetRarity rarity) {
        if (itemBuilder == null) loadItem();
        ConfigurationSection item = this.config.getConfigurationSection("item");
        itemBuilder.addName(item.getString("name"), "{rarity}", this.rarityPrefixes.get(rarity));
        itemBuilder.addLore(item.getStringList("lore"), this.rarityPrefixes.get(rarity));
        itemBuilder.addNBT(this, rarity);
        player.getInventory().addItem(itemBuilder.getItem());
    }

    public ItemStack getItem(PetRarity rarity) {
        ConfigurationSection item = this.config.getConfigurationSection("item");
        itemBuilder.addName(item.getString("name"), "{rarity}", this.rarityPrefixes.get(rarity));
        itemBuilder.addLore(item.getStringList("lore"), this.rarityPrefixes.get(rarity));
        itemBuilder.addNBT(this, rarity);
        return itemBuilder.getItem();
    }

    public boolean isProcing(PetRarity rarity) {
        return this.data.isProcing(rarity);
    }

    public UUID getId() {
        return petId;
    }

    public YamlConfiguration getConfig() {
        return config;
    }

    public UUID getPetId() {
        return petId;
    }

    public ItemBuilderUtil getItemBuilder() {
        return itemBuilder;
    }

    public Map<PetRarity, String> getRarityPrefixes() {
        return rarityPrefixes;
    }

    public boolean isType(PetType type) {
        return this.type.equals(type);
    }

    public PetType getType() {
        return type;
    }

    public PetData getData() {
        return data;
    }
}
