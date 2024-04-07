package main.java.com.drgnfireyellow.homeland.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class housesetting implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            World userWorld = Bukkit.getWorld(((Player) sender).getUniqueId().toString());
            System.out.println(args[0]);
            System.out.println(args[1]);
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
            else {
                sender.sendMessage("Valid Settings:");
                sender.sendMessage("time");
            }
        }
        return false;
    }
}
