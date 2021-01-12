package com.rainchat.soulparkour.Events.eventregistr;

import com.destroystokyo.paper.event.player.PlayerJumpEvent;
import com.rainchat.soulparkour.Api.customevents.PlayerUseClinging;
import com.rainchat.soulparkour.Files.database.PlayerDateManager;
import com.rainchat.soulparkour.SoulParkourMain;
import com.rainchat.soulparkour.utils.Check;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.SQLException;

public class Clinging implements Listener {

    @EventHandler
    public void onJump(PlayerJumpEvent event) throws SQLException {
        Player player = event.getPlayer();

        if (!player.isSneaking()) {
            return;
        }
        if (!PlayerDateManager.addEnergy(player, -1.5)) {
            return;
        }

        new BukkitRunnable() {
            @Override
            public void run() {
                if (player.isOnGround()) {
                    this.cancel();
                }
                if (Check.checkTargetBlock(player) && player.isSneaking()) {
                    callEvent(player);
                    newRun(player);
                    this.cancel();
                }
            }
        }.runTaskTimerAsynchronously(SoulParkourMain.getPluginInstance(), 0, 1);

    }


    public void newRun(Player p) {
        Location bar = p.getLocation().clone().add(0.0D, -1D, 0.0D);

        Bukkit.getScheduler().scheduleSyncDelayedTask(SoulParkourMain.getPluginInstance(), new Runnable() {
            public void run() {
                p.sendBlockChange(bar, Bukkit.createBlockData(Material.BARRIER));
                Bukkit.getScheduler().scheduleSyncDelayedTask(SoulParkourMain.getPluginInstance(), new Runnable() {
                    public void run() {
                        p.sendBlockChange(bar, bar.getBlock().getBlockData());
                        bar.getBlock().getState().update();
                    }
                }, 30L);
            }
        }, 0L);
    }

    public static void callEvent(Player player) {
        (new BukkitRunnable() {
            @Override
            public void run() {
                PlayerUseClinging event = new PlayerUseClinging(player);
                Bukkit.getPluginManager().callEvent(event);
                if (event.isCancelled()) {
                    return;
                }

            }
        }).runTaskLater(SoulParkourMain.getPluginInstance(), 1);
    }
}
