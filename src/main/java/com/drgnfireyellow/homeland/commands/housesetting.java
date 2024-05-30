package main.java.com.drgnfireyellow.homeland.commands;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class housesetting implements CommandExecutor,TabCompleter {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            World userWorld = Bukkit.getWorld("homeland_" + ((Player) sender).getUniqueId().toString());
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
    @Override
    public ArrayList<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        ArrayList<String> output = new ArrayList<String>();
        if (args.length == 1) {
            output.add("time");
        }
        if (args.length == 2) {
            if (args[0].equals("time")) {
                output.add("sunrise");
                output.add("day");
                output.add("noon");
                output.add("night");
                output.add("midnight");
            }
        }
        return output;
    }
}
