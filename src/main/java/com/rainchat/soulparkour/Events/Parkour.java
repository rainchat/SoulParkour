package com.rainchat.soulparkour.Events;

import com.rainchat.soulparkour.Files.Configs.ConfigSettings;
import com.rainchat.soulparkour.Files.FileManager;
import com.rainchat.soulparkour.PlayerSettings;
import com.rainchat.soulparkour.SoulParkourMain;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.*;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class Parkour implements Listener {
    private PlayerSettings playerSettings = PlayerSettings.getInstance();

    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        playerSettings.resetDate(e.getPlayer());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e){
        playerSettings.removeDate(e.getPlayer());
    }


    @EventHandler
    public void onChangeMode(PlayerGameModeChangeEvent event) {
        Player player = event.getPlayer();
        if (event.getNewGameMode().equals(GameMode.CREATIVE) || event.getNewGameMode().equals(GameMode.SPECTATOR)) {
            //player.setAllowFlight(true);
        }
    }

    @EventHandler
    public void onFlight(PlayerToggleFlightEvent event) {
        Player player = event.getPlayer();
        if (player.getGameMode().equals(GameMode.CREATIVE) || player.getGameMode().equals(GameMode.SPECTATOR)) {
            event.setCancelled(true);
            return;
        }


        event.setCancelled(true);
        player.setAllowFlight(false);
        player.setFlying(false);//Disable to prevent wobbling

        if (player.getFoodLevel() <= 6 || !player.hasPermission("soulparkour.use.doublejump")) {
            return;
        }


        Vector direction = player.getEyeLocation().getDirection().multiply(0.6);
        direction.setY(ConfigSettings.DOUBLE_JUMP_HIGH.getDouble());
        player.setFoodLevel(player.getFoodLevel()-1);
        player.getLocation().getWorld().playEffect(player.getLocation(), Effect.valueOf(ConfigSettings.DOUBLE_JUMP_SOUND.getString()), 0, 15);
        player.setVelocity(direction);

        new BukkitRunnable(){

            public void run(){
                player.spawnParticle(Particle.valueOf(ConfigSettings.DOUBLE_JUMP_PARTICLE.getString()), player.getLocation(), 12, 0.05, 0.05, 0.05, 0.05);
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


        playerSettings.resetDate(player);
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

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {

        Player player = event.getPlayer();
        if (player.getGameMode().equals(GameMode.CREATIVE) || player.getGameMode().equals(GameMode.SPECTATOR)) {
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
        if (action == Action.LEFT_CLICK_BLOCK && !player.isOnGround() && playerSettings.getPlayerJumps(player) > 0 && player.hasPermission("soulparkour.use.bounces")) {
            playerSettings.setPlayerJumps(player);
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
                    tempVec = locX2.subtract(player.getLocation()).toVector();
                    tempVec = (new Vector(tempVec.getX() - 7.0D, 10.0D, tempVec.getZ())).normalize().multiply(0.7D);
                    player.setVelocity(tempVec);
                }

                locZ1 = event.getPlayer().getLocation();
                wZ1 = locZ1.getWorld();
                locZ1.setX(locZ1.getX() - 1.0D);
                bZ1 = wZ1.getBlockAt(locZ1);
                if (!bZ1.isEmpty()) {
                    tempVec = locZ1.subtract(player.getLocation()).toVector();
                    vec = (new Vector(tempVec.getX() + 7.0D, 10.0D, tempVec.getZ())).normalize().multiply(0.7D);
                    player.setVelocity(vec);
                }

                locZ2 = event.getPlayer().getLocation();
                wZ2 = locZ2.getWorld();
                locZ2.setZ(locZ2.getZ() + 1.0D);
                bZ2 = wZ2.getBlockAt(locZ2);
                if (!bZ2.isEmpty()) {
                    tempVec = locZ2.subtract(player.getLocation()).toVector();
                    vec = (new Vector(tempVec.getX(), 10.0D, tempVec.getZ() - 7.0D)).normalize().multiply(0.7D);
                    player.setVelocity(vec);
                }

                locZ2 = event.getPlayer().getLocation();
                wZ2 = locZ2.getWorld();
                locZ2.setZ(locZ2.getZ() - 1.0D);
                bZ2 = wZ2.getBlockAt(locZ2);
                if (!bZ2.isEmpty()) {
                    tempVec = locZ2.subtract(player.getLocation()).toVector();
                    vec = (new Vector(tempVec.getX(), 10.0D, tempVec.getZ() + 7.0D)).normalize().multiply(0.7D);
                    player.setVelocity(vec);
                }
            }
        } else if (action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK && playerSettings.getPlayerStamina(player) > 0 && player.hasPermission("soulparkour.use.grab")) {
            loc = event.getPlayer().getLocation();

            if (checkTargetBlock(player)) {
                playerSettings.setPlayerEnergy(player);
                tempVec = loc.subtract(player.getLocation()).toVector();
                vec = (new Vector(tempVec.getX(), 1.0D, tempVec.getZ())).normalize().multiply(0.65D);
                player.setVelocity(vec);
                player.setFallDistance(0.0F);
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


}
