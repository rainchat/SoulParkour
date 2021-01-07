package com.rainchat.soulparkour.Effects;

import com.destroystokyo.paper.event.player.PlayerJumpEvent;
import com.rainchat.soulparkour.Files.Configs.ConfigSettings;
import org.bukkit.Bukkit;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleSprintEvent;

public class EffectListeners implements Listener {

    @EventHandler
    public void onJump(PlayerJumpEvent e){
        if (ConfigSettings.EFFECT_STILE.getString().equals("none")){
            return;
        }
        Player player = e.getPlayer();
        player.spawnParticle(Particle.SOUL_FIRE_FLAME,player.getLocation().add(0,0.5,0), 18, 0.06,0.06,0.06,0.06);
    }

    @EventHandler
    public void onSprint(PlayerToggleSprintEvent e){
        if (ConfigSettings.EFFECT_STILE.getString().equals("none")){
            return;
        }
        Player player = e.getPlayer();
        if(player.isSprinting()){
            return;
        }
        player.spawnParticle(Particle.END_ROD,player.getLocation().add(0,0.5,0), 18, 0.08,0.08,0.08,0.08);
    }

}
