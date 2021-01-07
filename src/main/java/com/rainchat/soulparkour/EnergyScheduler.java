package com.rainchat.soulparkour;

import com.rainchat.soulparkour.Files.Configs.ConfigSettings;
import com.rainchat.soulparkour.Files.database.PlayerDateManager;
import net.md_5.bungee.api.ChatMessageType;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.sql.SQLException;

public class EnergyScheduler {

    static public EnergyScheduler instance = new EnergyScheduler();

    static public EnergyScheduler getInstance(){
        return instance;
    }

    private int tusk;

    public void open() {
        if (tusk != -1) {
            Bukkit.getScheduler().cancelTask(tusk);
        }

        tusk = Bukkit.getScheduler().scheduleAsyncRepeatingTask(SoulParkourMain.getPluginInstance(), () -> {
            for (Player player : Bukkit.getOnlinePlayers()) {
                try {
                    if (player.isOnGround()) {
                        PlayerDateManager.addEnergy(player, 1.2);
                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }, 0, 5);
    }

    public void close() {
        Bukkit.getScheduler().cancelTask(tusk);
    }
}
