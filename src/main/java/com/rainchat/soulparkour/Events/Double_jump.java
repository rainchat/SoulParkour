package com.rainchat.soulparkour.Events;

import com.rainchat.soulparkour.Files.Configs.ConfigSettings;
import com.rainchat.soulparkour.Files.database.PlayerDateManager;
import com.rainchat.soulparkour.SoulParkourMain;
import org.bukkit.Effect;
import org.bukkit.GameMode;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.sql.SQLException;

public class Double_jump implements Listener {



    @EventHandler
    public void onFlight(PlayerToggleFlightEvent event) throws SQLException {
        Player player = event.getPlayer();
        if (player.getGameMode().equals(GameMode.CREATIVE) || player.getGameMode().equals(GameMode.SPECTATOR) || !PlayerDateManager.isToggle(player)) {
            return;
        }


        event.setCancelled(true);
        player.setAllowFlight(false);
        player.setFlying(false);//Disable to prevent wobbling

        if (player.getFoodLevel() <= 6 || !player.hasPermission("soulparkour.use.doublejump")) {
            return;
        }


        Vector direction = player.getEyeLocation().getDirection().multiply(0.6);
        direction.setY(ConfigSettings.PARKOUR_DOUBLE_JUMP_HIGH.getDouble());
        player.setFoodLevel(player.getFoodLevel()-1);
        player.getLocation().getWorld().playEffect(player.getLocation(), Effect.valueOf(ConfigSettings.SOUND_EFFECT.getString()), 0, 15);
        player.setVelocity(direction);

        if (!PlayerDateManager.addEnergy(player,-ConfigSettings.PARKOUR_DOUBLE_JUMP_ENERGY.getDouble())){
            return;
        }
        new BukkitRunnable(){

            public void run(){
                player.spawnParticle(Particle.valueOf(ConfigSettings.PARTICLE_EFFECT.getString()), player.getLocation(), 12, 0.05, 0.05, 0.05, 0.05);
                if (player.isOnGround()){
                    this.cancel();
                }

            }
        }.runTaskTimer(SoulParkourMain.getPluginInstance(), 0, 1);


    }

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if (player.getGameMode().equals(GameMode.CREATIVE) || player.getGameMode().equals(GameMode.SPECTATOR) ) {
            return;
        }


        if (event.getTo().getBlockX() == event.getFrom().getBlockX() &&
                event.getTo().getBlockY() == event.getFrom().getBlockY() &&
                event.getTo().getBlockZ() == event.getFrom().getBlockZ()) {
            return; // user didn't actually move a full block
        }
        if (player.getVelocity().getY() + 0.0784000015258789 <= 0 && !player.isOnGround() || !player.hasPermission("soulparkour.use.doublejump")){
            player.setAllowFlight(false);
            return;
        }



        player.setGravity(true);
        player.setAllowFlight(true);
        //Not ready
        /*
        if (playerSettings.getPlayerHoldingToWall(player) && checkTargetBlock(event.getPlayer()) && !event.getPlayer().isOnGround() && event.getPlayer().isSneaking()) {
            playerSettings.getPlayerHoldingToWall(player);
            player.setGravity(false);
            player.setAllowFlight(true);
            player.setFlying(true);
            player.setVelocity((new Vector()).zero());
        }
        */

    }

}
