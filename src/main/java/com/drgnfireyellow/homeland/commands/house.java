package com.drgnfireyellow.homeland.commands;

import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class house implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player && sender.hasPermission("homeland.house") && args.length == 0) {
            sender.sendMessage("Teleporting to your house...");
            World destinationWorld = Bukkit.getWorld("homeland_" + ((Player) sender).getUniqueId());
            Location destination = new Location(destinationWorld, 0, -60, 0);
            ((Player) sender).teleport(destination);
            ((Player) sender).setGameMode(GameMode.CREATIVE);
        }

        if (args.length > 0 && args[0].equals("setting")) {
            housesetting.command(sender, Arrays.copyOfRange(args, 1, args.length));
        }

        if (args.length > 0 && args[0].equals("toolbox")) {
            housetoolbox.command(sender, Arrays.copyOfRange(args, 1, args.length));
        }

        if (args.length > 0 && args[0].equals("menu")) {
            housemenu.command(sender, Arrays.copyOfRange(args, 1, args.length));
        }

        return false;
    }
}
