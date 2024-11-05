package com.drgnfireyellow.homeland;

import com.drgnfireyellow.kite.Display;
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
import org.bukkit.block.BlockState;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.*;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerArmorStandManipulateEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.util.Vector;
import com.drgnfireyellow.homeland.commands.house;
import com.drgnfireyellow.homeland.commands.housemenu;
import com.drgnfireyellow.homeland.commands.visit;
import net.kyori.adventure.text.Component;
import com.drgnfireyellow.homeland.commands.housesetting;
import com.drgnfireyellow.homeland.commands.housetoolbox;
import org.joml.Vector3f;

import java.util.Arrays;

public class homeland extends JavaPlugin implements Listener {
    @Override
    public void onEnable() {
        this.getCommand("house").setExecutor(new house());
        this.getCommand("visit").setExecutor(new visit());
        this.getCommand("housesetting").setExecutor(new housesetting());
        this.getCommand("housesetting").setTabCompleter(new housesetting());
        this.getCommand("housetoolbox").setExecutor(new housetoolbox());
        this.getCommand("housemenu").setExecutor(new housemenu());
        Bukkit.getPluginManager().registerEvents(this, this);
        saveDefaultConfig();
        Bukkit.getLogger().info("Thank you for using Homeland " + this.getPluginMeta().getVersion() + "!");
    }

    public final FileConfiguration config = this.getConfig();

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        // event.getPlayer().sendMessage("UUID: " + event.getPlayer().getUniqueId());
        if (Bukkit.getWorld(event.getPlayer().getUniqueId().toString()) == null) {
            WorldCreator creator = new WorldCreator("homeland_" + event.getPlayer().getUniqueId().toString());
            creator.environment(World.Environment.NORMAL);
            creator.type(WorldType.FLAT);
            creator.createWorld();
        }
        World userWorld = Bukkit.getWorld("homeland_" + event.getPlayer().getUniqueId().toString());
        userWorld.setDifficulty(Difficulty.PEACEFUL);
        WorldBorder userWorldBorder = userWorld.getWorldBorder();
        userWorldBorder.setSize(config.getDouble("houseSize"));
        userWorld.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false);
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        Block block = player.getLocation().getBlock().getRelative(BlockFace.DOWN);
        if (block.getType().equals(Material.SLIME_BLOCK) && (player.getWorld().getName().startsWith("homeland_") || config.get("customFunctionalityInAllWorlds").equals(true))) {
            player.setVelocity(new Vector(0f, 1.2f, 0f));
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Material heldItemMaterial = player.getInventory().getItemInMainHand().getType();
        if (player.getGameMode().equals(GameMode.CREATIVE)) {
            if (player.getWorld().getName().equals("homeland_" + player.getUniqueId().toString()) || config.get("customFunctionalityInAllWorlds").equals(true)) {
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
        Material[] concretes = {Material.WHITE_CONCRETE, Material.LIGHT_GRAY_CONCRETE, Material.GRAY_CONCRETE, Material.BLACK_CONCRETE, Material.BROWN_CONCRETE, Material.RED_CONCRETE, Material.ORANGE_CONCRETE, Material.YELLOW_CONCRETE, Material.LIME_CONCRETE, Material.GREEN_CONCRETE, Material.CYAN_CONCRETE, Material.LIGHT_BLUE_CONCRETE, Material.BLUE_CONCRETE, Material.PURPLE_CONCRETE, Material.MAGENTA_CONCRETE, Material.PINK_CONCRETE};
        ItemStack heldItem = player.getInventory().getItemInMainHand();
        if (player.getWorld().getName().startsWith("homeland_") || config.get("customFunctionalityInAllWorlds").equals(true)) {
            if (player.getInventory().getItemInMainHand().getItemMeta().displayName() != null && heldItem.getItemMeta().displayName().equals(Component.text("Hologram Removal Tool"))) {
                event.setCancelled(true);
                for (Entity e : player.getNearbyEntities(1, 1, 1)) {
                    if (e.getType().equals(EntityType.ARMOR_STAND)) {
                        e.remove();
                    }
                }
            }
            if (Arrays.asList(concretes).contains(player.getInventory().getItemInMainHand().getType()) && heldItem.getItemMeta().displayName() != null && (heldItem.getItemMeta().displayName().equals(Component.text("Bottom Slab")) || heldItem.getItemMeta().displayName().equals(Component.text("Top Slab")) || heldItem.getItemMeta().displayName().equals(Component.text("Stairs")) || heldItem.getItemMeta().displayName().equals(Component.text("Upside-Down Stairs")))) {
                Location placementLocation = event.getBlock().getLocation();
                if (config.get("concreteStairsAndSlabs.enableSlabs").equals(true)) {
                    if (heldItem.getItemMeta().displayName().equals(Component.text("Bottom Slab"))) {
                        Display.createBlockDisplay(heldItem.getType(), placementLocation, new Vector3f(0, 0, 0), new Vector3f(1F, 0.5F, 1F), new Vector3f(0, 0, 0));
                        placementLocation.getBlock().setType(Material.BARRIER);
                    }
                    if (heldItem.getItemMeta().displayName().equals(Component.text("Top Slab"))) {
                        Display.createBlockDisplay(heldItem.getType(), placementLocation, new Vector3f(0F, 0.5F, 0F), new Vector3f(1F, 0.5F, 1F), new Vector3f(0, 0, 0));
                        placementLocation.getBlock().setType(Material.BARRIER);
                    }
                }
                if (config.get("concreteStairsAndSlabs.enableStairs").equals(true)) {
                    double direction = Math.round((float) ((player.getYaw() + 180) / 90));
                    Vector3f topScale;
                    Vector3f topTransform;
                    if (direction == 4D || direction == 0D) {
                        topScale = new Vector3f(1F, 0.5F, 0.5F);
                        topTransform = new Vector3f(0F, 0.5F, 0F);
                    }
                    else if (direction == 3D) {
                        topScale = new Vector3f(0.5F, 0.5F, 1F);
                        topTransform = new Vector3f(0F, 0.5F, 0F);
                    }
                    else if (direction == 2D) {
                        topScale = new Vector3f(1F, 0.5F, 0.5F);
                        topTransform = new Vector3f(0F, 0.5F, 0.5F);
                    }
                    else {
                        topScale = new Vector3f(0.5F, 0.5F, 1F);
                        topTransform = new Vector3f(0.5F, 0.5F, 0F);
                    }
                    if (heldItem.getItemMeta().displayName().equals(Component.text("Stairs"))) {
                        Display.createBlockDisplay(heldItem.getType(), placementLocation, new Vector3f(0, 0, 0), new Vector3f(1F, 0.5F, 1F), new Vector3f(0, 0, 0));
                        Display.createBlockDisplay(heldItem.getType(), placementLocation, topTransform, topScale, new Vector3f(0, 0, 0));
                        placementLocation.getBlock().setType(Material.BARRIER);
                    }
                    if (heldItem.getItemMeta().displayName().equals(Component.text("Upside-Down Stairs"))) {
                        topTransform.y = 0F;
                        Display.createBlockDisplay(heldItem.getType(), placementLocation, new Vector3f(0F, 0.5F, 0F), new Vector3f(1F, 0.5F, 1F), new Vector3f(0, 0, 0));
                        Display.createBlockDisplay(heldItem.getType(), placementLocation, topTransform, topScale, new Vector3f(0, 0, 0));
                        placementLocation.getBlock().setType(Material.BARRIER);
                    }
                }
            }
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        BlockState blockState = event.getBlock().getState();
        if (player.getWorld().getName().startsWith("homeland_") || config.get("customFunctionalityInAllWorlds").equals(true))  {
            if (blockState.getType().equals(Material.BARRIER)) {
                for (Entity e : player.getWorld().getEntities()) {
                    if (e.getType().equals(EntityType.BLOCK_DISPLAY) && e.getLocation().equals(event.getBlock().getLocation())) {
                        e.remove();
                    }
                }
            }
        }
    }

    @EventHandler
    public void onPlayerArmorStandManipulate(PlayerArmorStandManipulateEvent event) {
        if (!event.getRightClicked().isVisible()) {
            if (event.getPlayer().getWorld().getName().startsWith("homeland_") || config.get("customFunctionalityInAllWorlds").equals(true)) {
                event.setCancelled(true);
            }
        }
    }
}
