package me.ninjoh.nincore.modules;


import me.ninjoh.nincore.api.Core;
import me.ninjoh.nincore.api.CoreModule;
import me.ninjoh.nincore.listeners.ArmorListener;
import org.bukkit.Bukkit;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ArmorEquipEventModule extends CoreModule
{
    private static List<String> blockedMaterials = new ArrayList<>();

    static
    {
        blockedMaterials.add(Material.FURNACE.toString());
        blockedMaterials.add(Material.CHEST.toString());
        blockedMaterials.add(Material.BEACON.toString());
        blockedMaterials.add(Material.DISPENSER.toString());
        blockedMaterials.add(Material.DROPPER.toString());
        blockedMaterials.add(Material.HOPPER.toString());
        blockedMaterials.add(Material.WORKBENCH.toString());
        blockedMaterials.add(Material.ENCHANTMENT_TABLE.toString());
        blockedMaterials.add(Material.ENDER_CHEST.toString());
        blockedMaterials.add(Material.ANVIL.toString());
        blockedMaterials.add(Material.BED_BLOCK.toString());
        blockedMaterials.add(Material.FENCE_GATE.toString());
        blockedMaterials.add(Material.SPRUCE_FENCE_GATE.toString());
        blockedMaterials.add(Material.BIRCH_FENCE_GATE.toString());
        blockedMaterials.add(Material.ACACIA_FENCE_GATE.toString());
        blockedMaterials.add(Material.JUNGLE_FENCE_GATE.toString());
        blockedMaterials.add(Material.DARK_OAK_FENCE_GATE.toString());
        blockedMaterials.add(Material.IRON_DOOR_BLOCK.toString());
        blockedMaterials.add(Material.WOODEN_DOOR.toString());
        blockedMaterials.add(Material.SPRUCE_DOOR.toString());
        blockedMaterials.add(Material.BIRCH_DOOR.toString());
        blockedMaterials.add(Material.JUNGLE_DOOR.toString());
        blockedMaterials.add(Material.ACACIA_DOOR.toString());
        blockedMaterials.add(Material.DARK_OAK_DOOR.toString());
        blockedMaterials.add(Material.WOOD_BUTTON.toString());
        blockedMaterials.add(Material.STONE_BUTTON.toString());
        blockedMaterials.add(Material.TRAP_DOOR.toString());
        blockedMaterials.add(Material.IRON_TRAPDOOR.toString());
        blockedMaterials.add(Material.DIODE_BLOCK_OFF.toString());
        blockedMaterials.add(Material.DIODE_BLOCK_ON.toString());
        blockedMaterials.add(Material.REDSTONE_COMPARATOR_OFF.toString());
        blockedMaterials.add(Material.REDSTONE_COMPARATOR_ON.toString());
        blockedMaterials.add(Material.FENCE.toString());
        blockedMaterials.add(Material.SPRUCE_FENCE.toString());
        blockedMaterials.add(Material.BIRCH_FENCE.toString());
        blockedMaterials.add(Material.JUNGLE_FENCE.toString());
        blockedMaterials.add(Material.DARK_OAK_FENCE.toString());
        blockedMaterials.add(Material.ACACIA_FENCE.toString());
        blockedMaterials.add(Material.NETHER_FENCE.toString());
        blockedMaterials.add(Material.BREWING_STAND.toString());
        blockedMaterials.add(Material.CAULDRON.toString());
        blockedMaterials.add(Material.SIGN_POST.toString());
        blockedMaterials.add(Material.WALL_SIGN.toString());
        blockedMaterials.add(Material.SIGN.toString());
    }

    public ArmorEquipEventModule(Core core)
    {
        super(core);
    }


    @Override
    public void onEnable()
    {
        this.getLogger().finest("The list of blocked materials is as follows;");
        this.getLogger().finest(Arrays.toString(blockedMaterials.toArray()));

        this.getLogger().info("Registering event listener for ArmorEquipEvent..");
        Bukkit.getPluginManager().registerEvents(new ArmorListener(blockedMaterials), this.getCore());
    }
}
