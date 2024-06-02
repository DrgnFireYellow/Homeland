package main.java.com.drgnfireyellow.homeland;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import org.bukkit.WorldBorder;
import org.bukkit.Difficulty;
import org.bukkit.GameMode;
import org.bukkit.GameRule;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerArmorStandManipulateEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.util.Vector;
import main.java.com.drgnfireyellow.homeland.commands.house;
import main.java.com.drgnfireyellow.homeland.commands.visit;
import net.kyori.adventure.text.Component;
import main.java.com.drgnfireyellow.homeland.commands.housesetting;
import main.java.com.drgnfireyellow.homeland.commands.housetoolbox;

public class homeland extends JavaPlugin implements Listener {
    @Override
    public void onEnable() {
        this.getCommand("house").setExecutor(new house());
        this.getCommand("visit").setExecutor(new visit());
        this.getCommand("housesetting").setExecutor(new housesetting());
        this.getCommand("housesetting").setTabCompleter(new housesetting());
        this.getCommand("housetoolbox").setExecutor(new housetoolbox());
        Bukkit.getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        // event.getPlayer().sendMessage("UUID: " + event.getPlayer().getUniqueId());
        if (Bukkit.getWorld(event.getPlayer().getUniqueId().toString()) == null) {
            WorldCreator creator = new WorldCreator("homeland_" + event.getPlayer().getUniqueId().toString());
            creator.environment(World.Environment.NORMAL);
            creator.type(WorldType.FLAT);
            creator.createWorld();
            World userWorld = Bukkit.getWorld("homeland_" + event.getPlayer().getUniqueId().toString());
            userWorld.setDifficulty(Difficulty.PEACEFUL);
            WorldBorder userWorldBorder = userWorld.getWorldBorder();
            userWorldBorder.setSize(100);
            userWorld.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false);
        }
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        Block block = player.getLocation().getBlock().getRelative(BlockFace.DOWN);
        if (block.getType().equals(Material.SLIME_BLOCK) && player.getWorld().getName().startsWith("homeland_")) {
            player.setVelocity(new Vector(0f, 1.2f, 0f));
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Material heldItemMaterial = player.getInventory().getItemInMainHand().getType();
        if (player.getGameMode().equals(GameMode.CREATIVE)) {
            if (player.getWorld().getName().equals("homeland_" + player.getUniqueId().toString())) {
                if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
                    if (heldItemMaterial.equals(Material.NAME_TAG)) {
                        Location blockLocation = event.getClickedBlock().getRelative(event.getBlockFace()).getLocation();
                        ArmorStand hologramArmorStand = (ArmorStand) blockLocation.getWorld().spawnEntity(blockLocation, EntityType.ARMOR_STAND);
                        hologramArmorStand.setGravity(false);
                        hologramArmorStand.setSmall(true);
                        hologramArmorStand.setCanPickupItems(false);
                        hologramArmorStand.customName(player.getInventory().getItemInMainHand().getItemMeta().displayName());
                        hologramArmorStand.setCustomNameVisible(true);
                        hologramArmorStand.setVisible(false);
                    }
                    if (event.getClickedBlock().getType().equals(Material.LAVA_CAULDRON)) {
                        player.getInventory().clear(player.getInventory().getHeldItemSlot());
                    }
                }
            }
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        if (player.getWorld().getName().startsWith("homeland_")) {
            if (player.getInventory().getItemInMainHand().getItemMeta().displayName() != null && player.getInventory().getItemInMainHand().getItemMeta().displayName().equals(Component.text("Hologram Removal Tool"))) {
                event.setCancelled(true);
                for (Entity e : player.getNearbyEntities(1, 1, 1)) {
                    if (e.getType().equals(EntityType.ARMOR_STAND)) {
                        e.remove();
                    }
                }
            }
        }
    }

    @EventHandler
    public void onPlayerArmorStandManipulate(PlayerArmorStandManipulateEvent event) {
        if (!event.getRightClicked().isVisible()) {
            if (event.getPlayer().getWorld().getName().startsWith("homeland_")) {
                event.setCancelled(true);
            }
        }
    }
}
