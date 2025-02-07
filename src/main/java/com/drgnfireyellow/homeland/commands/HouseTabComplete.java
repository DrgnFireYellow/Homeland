package com.drgnfireyellow.homeland.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

public class HouseTabComplete implements TabCompleter {

    @Override
    public @Nullable ArrayList<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        ArrayList<String> output = new ArrayList<>();

        if (args.length == 1) {
            output.add("menu");
            output.add("toolbox");
            output.add("setting");
        }

        if (args.length > 1) {
            if (args[0].equals("setting")) {
                if (args.length == 2) {
                    output.add("time");
                    output.add("mobspawning");
                }
                if (args.length == 3) {
                    if (args[1].equals("time")) {
                        output.add("sunrise");
                        output.add("day");
                        output.add("noon");
                        output.add("night");
                        output.add("midnight");
                    }
                    else if (args[1].equals("mobspawning")) {
                        output.add("true");
                        output.add("false");
                    }
                }
            }
        }

        return output;
    }
}
