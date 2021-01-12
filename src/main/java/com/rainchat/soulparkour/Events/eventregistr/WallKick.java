package com.rainchat.soulparkour.Events.eventregistr;

import com.rainchat.soulparkour.Api.customevents.PlayerUseWallKick;
import com.rainchat.soulparkour.Files.Configs.ConfigSettings;
import com.rainchat.soulparkour.Files.database.PlayerDateManager;
import com.rainchat.soulparkour.SoulParkourMain;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.sql.SQLException;

public class WallKick implements Listener {


    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) throws SQLException {
        Player player = event.getPlayer();
        if (player.getGameMode().equals(GameMode.CREATIVE) || player.getGameMode().equals(GameMode.SPECTATOR) || !PlayerDateManager.isToggle(player)) {
            return;
        }


        Action action = event.getAction();


        Location loc;
        Location locX2;
        World wX2;
        Block bX2;
        Vector tempVec;
        Block bZ1;
        Block bZ2;
        Vector vec;
        Location locZ1;
        World wZ1;
        Location locZ2;
        World wZ2;
        if (action == Action.LEFT_CLICK_BLOCK && !player.isOnGround() && player.hasPermission("soulparkour.use.bounces")) {
            if (!PlayerDateManager.addEnergy(player, -ConfigSettings.PARKOUR_WALL_JUMP_ENERGY.getDouble())) {
                return;
            }
            loc = event.getPlayer().getLocation();
            World wY = loc.getWorld();
            loc.setY(loc.getY() - 1.0D);
            Block bY = wY.getBlockAt(loc);
            if (true) {
                locX2 = event.getPlayer().getLocation();
                wX2 = locX2.getWorld();
                locX2.setX(locX2.getX() + 1.0D);
                bX2 = wX2.getBlockAt(locX2);
                if (!bX2.isEmpty()) {
                    callEvent(player);
                    velocityWall(player);
                }

                locZ1 = event.getPlayer().getLocation();
                wZ1 = locZ1.getWorld();
                locZ1.setX(locZ1.getX() - 1.0D);
                bZ1 = wZ1.getBlockAt(locZ1);
                if (!bZ1.isEmpty()) {
                    callEvent(player);
                    velocityWall(player);
                }

                locZ2 = event.getPlayer().getLocation();
                wZ2 = locZ2.getWorld();
                locZ2.setZ(locZ2.getZ() + 1.0D);
                bZ2 = wZ2.getBlockAt(locZ2);
                if (!bZ2.isEmpty()) {
                    callEvent(player);
                    velocityWall(player);
                }

                locZ2 = event.getPlayer().getLocation();
                wZ2 = locZ2.getWorld();
                locZ2.setZ(locZ2.getZ() - 1.0D);
                bZ2 = wZ2.getBlockAt(locZ2);
                if (!bZ2.isEmpty()) {
                    callEvent(player);
                    velocityWall(player);
                }
            }
        }
    }

    private void velocityWall(Player player) {
        Vector tempVec = player.getEyeLocation().getDirection();
        Vector vec = (new Vector(-tempVec.getX(), 0.70, -tempVec.getZ())).multiply(0.60D);
        player.setVelocity(vec);
        player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_FALLING, 10, 1));
    }

    public static void callEvent(Player player) {
        (new BukkitRunnable() {
            @Override
            public void run() {
                PlayerUseWallKick event = new PlayerUseWallKick(player);
                Bukkit.getPluginManager().callEvent(event);
                if (event.isCancelled()) {
                    return;
                }

            }
        }).runTaskLater(SoulParkourMain.getPluginInstance(), 1);
    }
}
