package com.rainchat.soulparkour.Events;

import com.destroystokyo.paper.event.player.PlayerJumpEvent;
import com.rainchat.soulparkour.Files.Configs.ConfigSettings;
import com.rainchat.soulparkour.Files.FileManager;
import com.rainchat.soulparkour.Files.database.PlayerDateManager;
import com.rainchat.soulparkour.SoulParkourMain;
import com.rainchat.soulparkour.utils.Check;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Сlimbing implements Listener {

    private List<UUID> player_Cooldown = new ArrayList<>();


    @EventHandler
    public void onJump(PlayerJumpEvent event) throws SQLException {
        Player player = event.getPlayer();

        if (!player.isSneaking()){
            return;
        }
        if (!PlayerDateManager.addEnergy(player,-1.5)){
            return;
        }
            new BukkitRunnable() {
                @Override
                public void run() {
                    if (player.isOnGround()) {
                        this.cancel();
                    }
                    if (Check.checkTargetBlock(player) && player.isSneaking()){
                        newRun(player);

                        this.cancel();
                    }
                }
            }.runTaskTimerAsynchronously(SoulParkourMain.getPluginInstance(), 0, 1);




    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) throws SQLException {
        Player player = event.getPlayer();

        if (player.getGameMode().equals(GameMode.CREATIVE) || player.getGameMode().equals(GameMode.SPECTATOR) || !PlayerDateManager.isToggle(player)) {
            return;
        }

        if (player_Cooldown.contains(player.getUniqueId())){
            player_Cooldown.remove(player.getUniqueId());
            return;
        }
        Action action = event.getAction();

        Location loc;
        Vector tempVec;
        Vector vec;

        if (action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK && player.hasPermission("soulparkour.use.grab")) {
            loc = event.getPlayer().getLocation();
            player_Cooldown.add(player.getUniqueId());
            if (!PlayerDateManager.addEnergy(player,-ConfigSettings.PARKOUR_CLIMBING_ENERGY.getDouble())){
                return;
            }

            if (checkTargetBlock(player)) {
                tempVec = loc.subtract(player.getLocation()).toVector();
                if (Check.getBlock(player,1,3).isAir()){
                    vec = (new Vector(0, 0.65D, 0));
                    player.setVelocity(vec);
                    player.setFallDistance(0.0F);
                } else {
                    vec = (new Vector(0, 0.45D, 0));
                    player.setVelocity(vec);
                    player.setFallDistance(0.0F);
                }

            }
        }
    }

    private boolean checkTargetBlock(Player player) {
        Block block = player.getTargetBlock(null, 1);
        List whitelist = FileManager.Files.BLOCKS.getFile().getList("Blocks.GrabBlock");
        if (block.isEmpty() || !whitelist.contains(block.getType().toString())) {
            return false;
        }
        return (block.getY() - player.getLocation().getY() > 0) && player.getLocation().distance(block.getLocation()) >= 0.25;
    }


    public void newRun(Player p){
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

}
