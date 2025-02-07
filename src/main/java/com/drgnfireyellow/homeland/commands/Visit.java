package com.drgnfireyellow.homeland.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.World;
import org.bukkit.Location;
import org.bukkit.GameMode;
import com.drgnfireyellow.homeland.Homeland;
import org.bukkit.plugin.java.JavaPlugin;

public class Visit implements CommandExecutor {
    FileConfiguration config = JavaPlugin.getPlugin(Homeland.class).getConfig();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player && sender.hasPermission("homeland.visit")) {
            if (Bukkit.getOfflinePlayerIfCached(args[0]) != null) {
                sender.sendMessage("Teleporting to "  + args[0] + "'s house...");
                World destinationWorld = Bukkit.getWorld("homeland_" + Bukkit.getOfflinePlayerIfCached(args[0]).getUniqueId().toString());
                Location destination = new Location(destinationWorld, 0, -60, 0);
                ((Player) sender).teleport(destination);
                ((Player) sender).setGameMode(GameMode.ADVENTURE);
                if (config.getBoolean("clearInventoryOnVisit")) {
                    ((Player) sender).getInventory().clear();
                }
            }
            else {
                sender.sendMessage("Unable to find that player's house!");
            }
        }
        return false;
    }
}
