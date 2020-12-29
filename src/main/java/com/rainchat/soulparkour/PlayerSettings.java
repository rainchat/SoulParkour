package com.rainchat.soulparkour;

import com.rainchat.soulparkour.Files.Configs.ConfigSettings;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class PlayerSettings {
    private static PlayerSettings instance = new PlayerSettings();

    private HashMap<UUID, Integer> playerEnergy = new HashMap<>();
    private HashMap<UUID, Integer> playerJumps = new HashMap<>();
    // to do
    //private HashMap<UUID, Boolean> playerHoldingToWall = new HashMap<>();

    public static PlayerSettings getInstance() {
        return instance;
    }


    public void resetDate(Player player) {
        if (player.isOnGround()) {
            playerEnergy.put(player.getUniqueId(), ConfigSettings.ENERGY_TO_CLIMB.getInt()*2);
            playerJumps.put(player.getUniqueId(), ConfigSettings.MAX_BOUNCES_OFF_THE_WALL.getInt());
            //playerHoldingToWall.put(player.getUniqueId(), ConfigSettings.CAN_HOLDING_TO_WALL.getBoolen());
        }
    }

    public void removeDate(Player player) {
            playerEnergy.remove(player.getUniqueId());
            playerJumps.remove(player.getUniqueId());
            //playerHoldingToWall.put(player.getUniqueId(), ConfigSettings.CAN_HOLDING_TO_WALL.getBoolen());
    }


    public void setPlayerEnergy(Player player) {
        playerEnergy.put(player.getUniqueId(), playerEnergy.get(player.getUniqueId()) - 1);
    }

    public void setPlayerJumps(Player player) {
        playerJumps.put(player.getUniqueId(), playerJumps.get(player.getUniqueId()) - 1);
    }



    public int getPlayerStamina(Player player) {
        return playerEnergy.get(player.getUniqueId());
    }

    public int getPlayerJumps(Player player) {
        return playerJumps.get(player.getUniqueId());
    }

    /*
    public void setPlayerHoldingToWall(Player player) {
        playerHoldingToWall.put(player.getUniqueId(), true);
    }

    public boolean getPlayerHoldingToWall(Player player) {
        return playerHoldingToWall.get(player.getUniqueId());
    }
    */


}
