package main.java.com.drgnfireyellow.homeland;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import org.bukkit.WorldBorder;
import org.bukkit.Difficulty;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import main.java.com.drgnfireyellow.homeland.commands.house;
import main.java.com.drgnfireyellow.homeland.commands.visit;
import main.java.com.drgnfireyellow.homeland.commands.housesetting;

public class homeland extends JavaPlugin implements Listener {
    @Override
    public void onEnable() {
        this.getCommand("house").setExecutor(new house());
        this.getCommand("visit").setExecutor(new visit());
        this.getCommand("housesetting").setExecutor(new housesetting());
        Bukkit.getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        event.getPlayer().sendMessage("UUID: " + event.getPlayer().getUniqueId());
        if (Bukkit.getWorld(event.getPlayer().getUniqueId().toString()) == null) {
            WorldCreator creator = new WorldCreator(event.getPlayer().getUniqueId().toString());
            creator.environment(World.Environment.NORMAL);
            creator.type(WorldType.FLAT);
            creator.createWorld();
            World userWorld = Bukkit.getWorld(event.getPlayer().getUniqueId().toString());
            userWorld.setDifficulty(Difficulty.PEACEFUL);
            WorldBorder userWorldBorder = userWorld.getWorldBorder();
            userWorldBorder.setSize(100);
            userWorld.setGameRuleValue("doDaylightCycle", "false");
        }
    }
}
