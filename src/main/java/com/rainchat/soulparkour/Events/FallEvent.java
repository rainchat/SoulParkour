package com.rainchat.soulparkour.Events;

import com.rainchat.soulparkour.Files.FileManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.util.Vector;

import java.util.List;

public class FallEvent implements Listener {


    @EventHandler
    public void onFall(EntityDamageEvent e) {

        if (e.getEntity() instanceof Player) {
            Player player = (Player) e.getEntity();
            EntityDamageEvent.DamageCause dc = e.getCause();
            Location loc = player.getLocation();
            loc.setY(loc.getY() - 1.0D);
            List whitelist = FileManager.Files.BLOCKS.getFile().getList("Blocks.LeapOfFaith");
            if (player.hasPermission("soulparkour.use.leapoffaith") && player.isSneaking() && dc.equals(EntityDamageEvent.DamageCause.FALL)
                    && whitelist.contains(loc.getBlock().getType().toString())) {
                player.teleport(loc);
                e.setCancelled(true);
            } else if (dc.equals(EntityDamageEvent.DamageCause.FALL) && player.getFallDistance() >= 3 && player.getFallDistance() <= 10) {
                if (player.isSneaking()) {
                    e.setDamage(e.getDamage() - 3.0F);
                    player.setVelocity(player.getLocation().getDirection().setY(0).normalize().multiply(1.4D));
                    player.setSneaking(false);
                    player.setSprinting(true);
                } else {
                    e.setDamage(e.getDamage() - 1.0F);
                    player.setVelocity(player.getLocation().getDirection().setY(0).normalize().multiply(0.8D));
                    player.setSneaking(false);
                    player.setSprinting(true);
                }


            }
        }
    }
}
