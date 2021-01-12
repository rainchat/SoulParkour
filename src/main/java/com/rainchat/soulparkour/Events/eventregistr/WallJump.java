package com.rainchat.soulparkour.Events.eventregistr;

import com.destroystokyo.paper.event.player.PlayerJumpEvent;
import com.rainchat.soulparkour.Api.customevents.PlayerUseWallJump;
import com.rainchat.soulparkour.Files.Configs.ConfigSettings;
import com.rainchat.soulparkour.Files.database.PlayerDateManager;
import com.rainchat.soulparkour.SoulParkourMain;
import com.rainchat.soulparkour.utils.Check;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.SQLException;

public class WallJump implements Listener {


    @EventHandler
    public void onJump(PlayerJumpEvent e) throws SQLException {
        Player player = e.getPlayer();
        if (player.getGameMode().equals(GameMode.CREATIVE) || player.getGameMode().equals(GameMode.SPECTATOR) || !PlayerDateManager.isToggle(player)) {
            return;
        }

        if (!player.isSprinting()) {
            return;
        }


        new BukkitRunnable() {
            @Override
            public void run() {
                if (player.isOnGround()) {
                    this.cancel();
                }
                if (Check.checkTargetBlock(e.getPlayer())) {
                    try {
                        if (!PlayerDateManager.addEnergy(player, -ConfigSettings.PARKOUR_JUMP_ON_THE_WALL_ENERGY.getDouble())) {
                            return;
                        }
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
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
                }, 10L);
            }
        }, 0L);
    }


    public static void callEvent(Player player) {
        (new BukkitRunnable() {
            @Override
            public void run() {
                PlayerUseWallJump event = new PlayerUseWallJump(player);
                Bukkit.getPluginManager().callEvent(event);
                if (event.isCancelled()) {
                    return;
                }

            }
        }).runTaskLater(SoulParkourMain.getPluginInstance(), 1);
    }
}
