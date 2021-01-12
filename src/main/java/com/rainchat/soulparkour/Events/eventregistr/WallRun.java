package com.rainchat.soulparkour.Events.eventregistr;

import com.rainchat.soulparkour.Api.customevents.PlayerUseClimbing;
import com.rainchat.soulparkour.SoulParkourMain;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;

public class WallRun implements Listener {


    public static void callEvent(Player player) {
        (new BukkitRunnable() {
            @Override
            public void run() {
                PlayerUseClimbing event = new PlayerUseClimbing(player);
                Bukkit.getPluginManager().callEvent(event);
                if (event.isCancelled()) {
                    return;
                }

            }
        }).runTaskLater(SoulParkourMain.getPluginInstance(), 1);
    }

}
