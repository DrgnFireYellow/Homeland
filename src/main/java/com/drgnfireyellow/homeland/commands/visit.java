package main.java.com.drgnfireyellow.homeland.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.World;
import org.bukkit.Location;
import org.bukkit.GameMode;


public class visit implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            sender.sendMessage("Teleporting to "  + args[0] + "'s house...");
            World destinationWorld = Bukkit.getWorld(Bukkit.getOfflinePlayerIfCached(args[0]).toString());
            Location destination = new Location(destinationWorld, 0, -60, 0);
            ((Player) sender).teleport(destination);
            ((Player) sender).setGameMode(GameMode.ADVENTURE);
        }
        return false;
    }
}
