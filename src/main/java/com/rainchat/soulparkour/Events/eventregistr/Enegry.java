package com.rainchat.soulparkour.Events.eventregistr;

import com.rainchat.soulparkour.Api.customevents.PlayerEnegryChange;
import com.rainchat.soulparkour.SoulParkourMain;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;


public class Enegry implements Listener {

    public static void callEvent(Player player) {
        (new BukkitRunnable() {
            @Override
            public void run() {
                PlayerEnegryChange event = new PlayerEnegryChange(player);
                Bukkit.getPluginManager().callEvent(event);
                if (event.isCancelled()) {
                    return;
                }

            }
        }).runTaskLater(SoulParkourMain.getPluginInstance(), 1);
    }

}
