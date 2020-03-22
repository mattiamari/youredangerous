package me.mattiamari.youredangerous;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class DangerListener implements Listener {
    private YoureDangerous plugin;

    public DangerListener(YoureDangerous plugin) {
        this.plugin = plugin;
    }

    /**
     * Log creeper explosions and the player it was targeting
     * @param event
     */
    @EventHandler
    public void onCreeperExplode(EntityExplodeEvent event) {
        Entity entity = event.getEntity();
        
        if (entity.getType() != EntityType.CREEPER) {
            return;
        }

        Location loc = event.getLocation();
        Creeper creeper = Creeper.class.cast(entity);

        if (creeper.getTarget() == null) {
            return;
        }

        plugin.getLogger().info(String.format("%s made a Creeper explode at (%d %d %d)",
            creeper.getTarget().getName(),
            loc.getBlockX(), loc.getBlockY(), loc.getBlockZ()));
    }

    /**
     * Log when "flint and steel" is used to trigger creeper explosion
     * @param event
     */
    @EventHandler
    public void onCreeperIgnite(PlayerInteractEntityEvent event) {
        ItemStack itemInHand = event.getPlayer().getInventory().getItemInMainHand();

        if (itemInHand == null
            || itemInHand.getType() != Material.FLINT_AND_STEEL
        ) {
            return;
        }

        if (event.getRightClicked().getType() != EntityType.CREEPER) {
            return;
        }

        Location loc = event.getPlayer().getLocation();

        plugin.getLogger().info(String.format(
            "%s used flint and steel on Creeper at (%d %d %d)",
            event.getPlayer().getName(),
            loc.getBlockX(), loc.getBlockY(), loc.getBlockZ()
        ));
    }

    /**
     * Log when "flint and steel" is used to trigger TNT
     * @param event
     */
    @EventHandler
    public void onTntIgnite(PlayerInteractEvent event) {
        ItemStack itemInHand = event.getPlayer().getInventory().getItemInMainHand();
        Block block = event.getClickedBlock();

        if (itemInHand == null || itemInHand.getType() != Material.FLINT_AND_STEEL) {
            return;
        }

        if (block == null) {
            return;
        }

        if (block.getType() != Material.TNT && block.getType() != Material.TNT_MINECART) {
            return;
        }

        Location loc = event.getPlayer().getLocation();

        plugin.getLogger().info(String.format(
            "%s ignited TNT at (%d %d %d)",
            event.getPlayer().getName(),
            loc.getBlockX(), loc.getBlockY(), loc.getBlockZ()
        ));
    }

    /**
     * Log when dangerous blocks (e.g. lava and TNT) are placed
     * @param event
     */
    @EventHandler
    public void onDangerousBlockPlace(BlockPlaceEvent event) {
        Block block = event.getBlock();

        if (block.getType() == Material.LAVA
            || block.getType() == Material.TNT
            || block.getType() == Material.TNT_MINECART
        ) {
            plugin.getLogger().info(String.format("%s placed %s at (%d %d %d).",
                event.getPlayer().getName(),
                block.getType().name(),
                block.getX(), block.getY(), block.getZ()));
        }
    }

    /**
     * Log when a lava bucket gets emptied
     * @param event
     */
    @EventHandler
    public void onLavaBucketEmpty(PlayerBucketEmptyEvent event) {
        Block block = event.getBlock();

        if (event.getBucket() == Material.LAVA_BUCKET) {
            plugin.getLogger().info(String.format("%s placed %s at (%d %d %d).",
                event.getPlayer().getName(),
                event.getBucket().name(),
                block.getX(), block.getY(), block.getZ()));
        }
    }
    
    /**
     * Log when "flint and steel" is used to start a fire
     * @param event
     */
    @EventHandler
    public void onBlockIgnite(BlockIgniteEvent event) {
        Block block = event.getBlock();

        if (event.getPlayer() != null) {
            plugin.getLogger().info(String.format("%s started a fire at (%d %d %d).",
                event.getPlayer().getName(),
                block.getX(), block.getY(), block.getZ()));
        }
    }
}
