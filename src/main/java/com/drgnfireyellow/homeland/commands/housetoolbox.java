package com.drgnfireyellow.homeland.commands;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.kyori.adventure.text.Component;

public class housetoolbox {
    public static void command(CommandSender sender, String[] args) {
        if (sender instanceof Player && sender.hasPermission("homeland.housetoolbox")) {
            Player player = (Player) sender;
            if ((((Player) sender).getGameMode().equals(GameMode.CREATIVE)) || (player.getWorld().getName().equals("homeland_" + player.getUniqueId()))) {

                Inventory toolboxInventory = Bukkit.createInventory(null, 27, Component.text("House Toolbox"));

                ItemStack slimeBlock = new ItemStack(Material.SLIME_BLOCK, 1);
                ItemMeta slimeBlockMeta = slimeBlock.getItemMeta();
                ArrayList<Component> slimeBlockLore = new ArrayList<Component>();
                slimeBlockLore.add(Component.text("Makes you bounce really high."));
                slimeBlockLore.add(Component.text("(Applies to all Slime Blocks)"));
                slimeBlockMeta.displayName(Component.text("Bounce Pad"));
                slimeBlockMeta.lore(slimeBlockLore);
                slimeBlock.setItemMeta(slimeBlockMeta);
                toolboxInventory.addItem(slimeBlock);

                ItemStack nameTag = new ItemStack(Material.NAME_TAG, 1);
                ItemMeta nameTagMeta = nameTag.getItemMeta();
                ArrayList<Component> nameTagLore = new ArrayList<Component>();
                nameTagLore.add(Component.text("Floating text. Rename in an anvil to use."));
                nameTagLore.add(Component.text("(Applies to all Name Tags)"));
                nameTagMeta.displayName(Component.text("Hologram"));
                nameTagMeta.lore(nameTagLore);
                nameTag.setItemMeta(nameTagMeta);
                toolboxInventory.addItem(nameTag);

                ItemStack hologramRemovalTool = new ItemStack(Material.BARRIER, 1);
                ItemMeta hologramRemovalToolMeta = hologramRemovalTool.getItemMeta();
                ArrayList<Component> hologramRemovalToolLore = new ArrayList<Component>();
                hologramRemovalToolLore.add(Component.text("When placed, removes all nearby holograms."));
                hologramRemovalToolMeta.lore(hologramRemovalToolLore);
                hologramRemovalToolMeta.displayName(Component.text("Hologram Removal Tool"));
                hologramRemovalTool.setItemMeta(hologramRemovalToolMeta);
                toolboxInventory.addItem(hologramRemovalTool);

                player.openInventory(toolboxInventory);
            }
        }
    }
}