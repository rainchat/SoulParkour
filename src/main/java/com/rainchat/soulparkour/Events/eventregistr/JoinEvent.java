package com.rainchat.soulparkour.Events.eventregistr;

import com.destroystokyo.paper.event.player.PlayerJumpEvent;
import com.rainchat.soulparkour.Files.database.PlayerDateManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.sql.SQLException;

public class JoinEvent implements Listener {

    @EventHandler
    public void onJoin(PlayerJumpEvent e) throws SQLException {
        PlayerDateManager.addPlayer(e.getPlayer());
    }

}
