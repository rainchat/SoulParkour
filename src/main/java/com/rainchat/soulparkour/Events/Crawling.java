package com.rainchat.soulparkour.Events;

import com.rainchat.soulparkour.Files.Configs.ConfigSettings;
import com.rainchat.soulparkour.Files.database.PlayerDateManager;
import com.rainchat.soulparkour.SoulParkourMain;
import com.rainchat.soulparkour.utils.Check;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Crawling implements Listener {

    static List<Player> cancelDamage = new ArrayList();

    @EventHandler
    public void onEntityDamage(EntityDamageEvent e) {
        if (e.getEntity() instanceof Player) {
            Player p = (Player)e.getEntity();
            if (cancelDamage.contains(p) && e.getCause().equals(EntityDamageEvent.DamageCause.SUFFOCATION)) {
                e.setCancelled(true);
                return;
            }
        }

    }


    @EventHandler
    public void onshift(PlayerToggleSneakEvent e) throws SQLException {
        Player player = e.getPlayer();

        if (player.getGameMode().equals(GameMode.CREATIVE) || player.getGameMode().equals(GameMode.SPECTATOR) || !PlayerDateManager.isToggle(player)) {
            return;
        }

        if (!player.isSneaking() && player.getLocation().getPitch() >= 45) {

            if (!PlayerDateManager.addEnergy(player,-ConfigSettings.PARKOUR_CRAWLING_ENERGY.getDouble())){
                return;
            }

            Bukkit.getScheduler().scheduleSyncDelayedTask(SoulParkourMain.getPluginInstance(), new Runnable() {
                public void run() {
                    if (player.isSneaking()) {
                        if (Check.isAWall(player)){

                            putInTunnel(e.getPlayer());
                        } else if (Check.isATunnel(player)) {
                            putInTunnel(e.getPlayer());
                        }
                    }
                }

            }, 5L);
        }
    }

    public void putInTunnel(Player player) {

        if (player.getGameMode().equals(GameMode.CREATIVE) || player.getGameMode().equals(GameMode.SPECTATOR) ) {
            return;
        }

        Location loc = player.getLocation();
        final Location bar,bar2;
        Vector vector = new Vector();
        cancelDamage.add(player);
        bar = player.getLocation().clone().add(0.0D, 1.0D, 0.0D);
        bar2 = player.getLocation().clone().add(0.0D, -1.0D, 0.0D);
        player.sendBlockChange(bar, Bukkit.createBlockData(Material.BARRIER));
        if (bar2.getBlock().getType().isAir()){
            player.sendBlockChange(bar2, Bukkit.createBlockData(Material.BARRIER));
        }
        Bukkit.getScheduler().scheduleSyncDelayedTask(SoulParkourMain.getPluginInstance(), new Runnable() {
            public void run() {
                teleport(player);
                if (ConfigSettings.PARKOUR_CRAWLING_BOOST.getBoolen()){
                    boost(player);
                }

                player.sendBlockChange(bar, bar.getBlock().getBlockData());
                bar.getBlock().getState().update();
                Bukkit.getScheduler().scheduleSyncDelayedTask(SoulParkourMain.getPluginInstance(), new Runnable() {
                    public void run() {
                        if (cancelDamage.contains(player)) {
                            cancelDamage.remove(player);
                        }

                    }
                }, 10L);
                player.sendBlockChange(bar2, bar2.getBlock().getBlockData());
                bar2.getBlock().getState().update();
                Bukkit.getScheduler().scheduleSyncDelayedTask(SoulParkourMain.getPluginInstance(), new Runnable() {
                    public void run() {

                    }
                }, 10L);
            }
        }, 5L);
    }

    public void teleport(Player player) {
        Location loc = player.getLocation();
        if (player.getFacing().equals(BlockFace.NORTH)) {
            loc.add(0, 0, -0.11);
        } else if (player.getFacing().equals(BlockFace.SOUTH)) {
            loc.add(0, 0, 0.11);
        } else if (player.getFacing().equals(BlockFace.EAST)) {
            loc.add(0.11, 0, 0);
        } else if (player.getFacing().equals(BlockFace.WEST)) {
            loc.add(-0.11, 0, 0);
        }
        player.teleport(loc);
    }




    public void boost(Player p) {
        Location loc = p.getLocation();
        new BukkitRunnable() {
            int i = 10;

            public void run() {
                if (i == 0) {
                    this.cancel();
                }
                p.setVelocity(p.getFacing().getDirection().multiply(0.3));
                i--;
            }
        }.runTaskTimerAsynchronously(SoulParkourMain.getPluginInstance(), 0, 1);
    }

}
