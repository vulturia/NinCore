package cc.acquized.itembuilder;

import me.ninjoh.nincore.api.common.org.jetbrains.annotations.NotNull;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.material.MaterialData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ItemBuilder API for creating ItemStacks easy with just 1 line of code
 * @author Acquized
 * @see ItemStack
 */
public class ItemBuilder {

    private ItemStack item;
    private ItemMeta meta;
    private Material material = Material.STONE;
    private int amount = 1;
    private MaterialData data;
    private short damage = 0;
    private Map<Enchantment, Integer> enchantments = new HashMap<>();
    private String displayname;
    private List<String> lore = new ArrayList<>();
    private List<ItemFlag> flags = new ArrayList<>();

    /**
     * Initials the ItemBuilder with the Material
     * @see Material
     */
    public ItemBuilder(Material material) {
        this.material = material;
    }

    /**
     * Initials the ItemBuilder with an already existing ItemStack
     * @see ItemStack
     */
    public ItemBuilder(@NotNull ItemStack item) {
        this.item = item;
        this.meta = item.getItemMeta();
        this.material = item.getType();
        this.amount = item.getAmount();
        this.data = item.getData();
        this.damage = item.getDurability();
        this.enchantments = item.getEnchantments();
        this.displayname = item.getItemMeta().getDisplayName();
        this.lore = item.getItemMeta().getLore();
        for(ItemFlag f : item.getItemMeta().getItemFlags()) {
            flags.add(f);
        }
    }

    /**
     * Sets the amount of the builded ItemStack
     * @param amount (Integer)
     * @return this, for method chaining.
     */
    @NotNull
    public ItemBuilder amount(int amount) {
        this.amount = amount;
        return this;
    }

    /**
     * Sets the MaterialData of the builded ItemStack
     * @param data (MaterialData)
     * @return this, for method chaining.
     */
    @NotNull
    public ItemBuilder data(MaterialData data) {
        this.data = data;
        return this;
    }

    /**
     * Sets the damage (durability) of the builded ItemStack
     * @param damage (Short)
     * @deprecated Use ItemBuilder#durability
     * @return this, for method chaining.
     */
    @NotNull
    @Deprecated
    public ItemBuilder damage(short damage) {
        this.damage = damage;
        return this;
    }

    /**
     * Sets the durability (damage) of the builded ItemStack
     * @param damage (Short)
     * @return this, for method chaining.
     */
    @NotNull
    public ItemBuilder durability(short damage) {
        this.damage = damage;
        return this;
    }

    /**
     * Sets the Material of the builded ItemStack
     * @param material (Material)
     * @return this, for method chaining.
     */
    @NotNull
    public ItemBuilder material(Material material) {
        this.material = material;
        return this;
    }

    /**
     * Sets the ItemMeta of the builded ItemStack
     * @param meta (ItemMeta)
     * @return this, for method chaining.
     */
    @NotNull
    public ItemBuilder meta(ItemMeta meta) {
        this.meta = meta;
        return this;
    }

    /**
     * Adds the Enchantment of the builded ItemStack
     * @param enchant (Enchantment)
     * @return this, for method chaining.
     */
    @NotNull
    public ItemBuilder enchant(Enchantment enchant, int level) {
        enchantments.put(enchant, level);
        return this;
    }

    /**
     * Sets the Enchantments of the builded ItemStack
     * @param enchantments (Map<Enchantment, Integer> )
     * @return this, for method chaining.
     */
    @NotNull
    public ItemBuilder enchant(Map<Enchantment, Integer> enchantments) {
        this.enchantments = enchantments;
        return this;
    }

    /**
     * Sets the Displayname of the builded ItemStack
     * @param displayname (Displayname)
     * @return this, for method chaining.
     */
    @NotNull
    public ItemBuilder displayname(String displayname) {
        this.displayname = displayname;
        return this;
    }

    /**
     * Adds the line to the Lore of the builded ItemStack
     * @param line (String)
     * @return this, for method chaining.
     */
    @NotNull
    public ItemBuilder lore(String line) {
        lore.add(line);
        return this;
    }

    /**
     * Sets the lore of the builded ItemStack
     * @param lore {@link List<String>}
     * @return this, for method chaining.
     */
    @NotNull
    public ItemBuilder lore(List<String> lore) {
        this.lore = lore;
        return this;
    }

    /**
     * Adds the line as the counts position as lore
     * @param line (String)
     * @param count (Integer)
     * @return this, for method chaining.
     */
    @NotNull
    public ItemBuilder lore(String line, int count) {
        lore.set(count, line);
        return this;
    }

    /**
     * Adds the ItemFlag to the builded ItemStack
     * @param flag (ItemFlag)
     * @return this, for method chaining.
     */
    @NotNull
    public ItemBuilder flag(ItemFlag flag) {
        flags.add(flag);
        return this;
    }

    /**
     * Sets the ItemFlags of the builded ItemStack
     * @param flags (List<ItemFlag>)
     * @return this, for method chaining.
     */
    @NotNull
    public ItemBuilder flag(List<ItemFlag> flags) {
        this.flags = flags;
        return this;
    }

    /**
     * Makes the Item (un-)breakable
     * @param unbreakable (Boolean)
     * @return this, for method chaining.
     */
    @NotNull
    public ItemBuilder unbreakable(boolean unbreakable) {
        meta.spigot().setUnbreakable(unbreakable);
        return this;
    }

    /**
     * Sets the Owner of the Skull
     * This only affects if the Item is a SKULL_ITEM or a SKULL
     * @param user (String)
     * @return this, for method chaining.
     */
    @NotNull
    public ItemBuilder owner(String user) {
        if((material == Material.SKULL_ITEM) || (material == Material.SKULL)) {
            SkullMeta smeta = (SkullMeta) meta;
            smeta.setOwner(user);
            meta = smeta;
        }
        return this;
    }

    /**
     * Builds the ItemStack and returns it
     * @return (ItemStack)
     */
    public ItemStack build() {
        item = new ItemStack(material, amount, damage);
        meta = item.getItemMeta();
        if(data != null) {
            item.setData(data);
        }
        if(enchantments.size() > 0) {
            item.addUnsafeEnchantments(enchantments);
        }
        if(displayname != null) {
            meta.setDisplayName(displayname);
        }
        if(lore.size() > 0) {
            meta.setLore(lore);
        }
        if(flags.size() > 0) {
            for(ItemFlag f : flags) {
                meta.addItemFlags(f);
            }
        }
        item.setItemMeta(meta);
        return item;
    }

}