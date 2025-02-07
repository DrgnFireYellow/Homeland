package com.drgnfireyellow.homeland.commands;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.jetbrains.annotations.NotNull;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import xyz.xenondevs.invui.gui.Gui;
import xyz.xenondevs.invui.item.ItemProvider;
import xyz.xenondevs.invui.item.ItemWrapper;
import xyz.xenondevs.invui.item.builder.ItemBuilder;
import xyz.xenondevs.invui.item.impl.AbstractItem;
import xyz.xenondevs.invui.item.impl.CommandItem;
import xyz.xenondevs.invui.item.impl.controlitem.ControlItem;
import xyz.xenondevs.invui.window.Window;

final class VisitItem extends AbstractItem {
    @Override
    public ItemProvider getItemProvider() {
        ItemStack icon = new ItemStack(Material.ENDER_PEARL);
        ItemMeta iconMeta = icon.getItemMeta();
        iconMeta.displayName(Component.text("Visit Someone Else's House").decoration(TextDecoration.ITALIC, false));
        icon.setItemMeta(iconMeta);
        return new ItemWrapper(icon);
    }
    
    @Override
    public void handleClick(@NotNull ClickType clickType, @NotNull Player player, @NotNull InventoryClickEvent event) {
        Gui playersGui = Gui.normal().setStructure(". . . . . . . . .", ". . . . . . . . .", ". . . . . . . . .").build();
        Integer playerNum = 0;
        for (Player p : Bukkit.getOnlinePlayers()) {
            playerNum = playerNum + 1;
            if (playerNum < 28) {
                ItemStack playerIcon = new ItemStack(Material.PLAYER_HEAD);
                SkullMeta playerMeta = ((SkullMeta) playerIcon.getItemMeta());
                playerMeta.displayName(Component.text(p.getName()).decoration(TextDecoration.ITALIC, false));
                playerMeta.setOwningPlayer((OfflinePlayer) p);
                playerIcon.setItemMeta(playerMeta);
                playersGui.addItems(new CommandItem(new ItemWrapper(playerIcon), "/homeland:visit " + p.getName()));
            }
        }
        Window playersWindow = Window.single().setViewer(player).setGui(playersGui).setTitle("Visit Someone Else's House").build();
        playersWindow.open();
    }

}

public class housemenu {
    public static void command(CommandSender sender, String[] args) {
        if (sender instanceof Player && sender.hasPermission("homeland.housemenu")) {
            ItemStack houseItem = new ItemStack(Material.RED_BED);
            ItemMeta houseMeta = houseItem.getItemMeta();
            houseMeta.displayName(Component.text("Your House").decoration(TextDecoration.ITALIC, false));
            houseItem.setItemMeta(houseMeta);

            ItemStack toolboxItem = new ItemStack(Material.CHEST);
            ItemMeta toolboxMeta = toolboxItem.getItemMeta();
            toolboxMeta.displayName(Component.text("Open Toolbox").decoration(TextDecoration.ITALIC, false));
            toolboxItem.setItemMeta(toolboxMeta);
            Gui menu = Gui.normal().setStructure(
            ". . . . . . . . .",
            ". . . . . . . . .",
            ". . H . V . T . .",
            ". . . . . . . . .",
            ". . . . . . . . .")
            .addIngredient('H', new CommandItem(new ItemWrapper(houseItem), "/homeland:house"))
            .addIngredient('T', new CommandItem(new ItemWrapper(toolboxItem), "/homeland:house toolbox"))
            .addIngredient('V', new VisitItem())
            .build();
            Window displayWindow = Window.single().setViewer(((Player) sender)).setTitle("House Menu").setGui(menu).build();
            displayWindow.open();
        }
    }
}
