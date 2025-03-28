package com.drgnfireyellow.homeland.commands;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.GameRule;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class HouseSetting {
    public static void command(CommandSender sender, String[] args) {
        if (sender instanceof Player && sender.hasPermission("homeland.house.setting")) {
            World userWorld = Bukkit.getWorld("homeland_" + ((Player) sender).getUniqueId().toString());
            if (args.length > 1) {
                if (args[0].equals("time")) {
                    if (args[1].equals("sunrise")) {
                        userWorld.setTime(0);
                    }
                    else if (args[1].equals("day")) {
                        userWorld.setTime(1000);
                    }
                    else if (args[1].equals("noon")) {
                        userWorld.setTime(6000);
                    }
                    else if (args[1].equals("night")) {
                        userWorld.setTime(13000);
                    }
                    else if (args[1].equals("midnight")) {
                        userWorld.setTime(18000);
                    }
                    else {
                        userWorld.setTime(Integer.valueOf(args[1]));
                    }
                }
                else if (args[0].equals("mobspawning")) {
                    if (args[1].equals("true")) {
                        userWorld.setGameRule(GameRule.DO_MOB_SPAWNING, true);
                    }
                    else if (args[1].equals("false")) {
                        userWorld.setGameRule(GameRule.DO_MOB_SPAWNING, false);
                    }
                    else {
                        sender.sendMessage("Valid Values:");
                        sender.sendMessage("true");
                        sender.sendMessage("false");
                    }
                }
                else {
                    sender.sendMessage("Valid Settings:");
                    sender.sendMessage("time");
                    sender.sendMessage("mobspawning");
                }
            }
            else {
                sender.sendMessage("Please specify a setting and value.");
                sender.sendMessage("Valid Settings:");
                sender.sendMessage("time");
                sender.sendMessage("mobspawning");
            }
        }
    }
}
