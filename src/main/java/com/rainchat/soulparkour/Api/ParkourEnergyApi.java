package com.rainchat.soulparkour.Api;

import com.rainchat.soulparkour.Files.Configs.ConfigSettings;
import com.rainchat.soulparkour.Files.database.PlayerDateManager;
import org.bukkit.entity.Player;

import java.sql.SQLException;

public class ParkourEnergyApi {

    static public String getEnergy(Player player) throws SQLException {
        return PlayerDateManager.getEnergy(player).toString();
    }

    static public String getMaxEnergy(){
        return ConfigSettings.MAX_ENERGY.getString();
    }

}
