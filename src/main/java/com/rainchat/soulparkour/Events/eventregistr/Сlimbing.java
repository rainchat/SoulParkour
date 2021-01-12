package com.rainchat.soulparkour.Events.eventregistr;

import com.rainchat.soulparkour.Api.customevents.PlayerUseClimbing;
import com.rainchat.soulparkour.Files.Configs.ConfigSettings;
import com.rainchat.soulparkour.Files.FileManager;
import com.rainchat.soulparkour.Files.database.PlayerDateManager;
import com.rainchat.soulparkour.SoulParkourMain;
import com.rainchat.soulparkour.utils.Check;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Сlimbing implements Listener {

    private List<UUID> player_Cooldown = new ArrayList<>();


    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) throws SQLException {
        Player player = event.getPlayer();

        if (player.getGameMode().equals(GameMode.CREATIVE) || player.getGameMode().equals(GameMode.SPECTATOR) || !PlayerDateManager.isToggle(player)) {
            return;
        }

        if (player_Cooldown.contains(player.getUniqueId())) {
            player_Cooldown.remove(player.getUniqueId());
            return;
        }
        Action action = event.getAction();

        Location loc;
        Vector tempVec;
        Vector vec;

        if (action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK && player.hasPermission("soulparkour.use.grab")) {
            loc = event.getPlayer().getLocation();
            player_Cooldown.add(player.getUniqueId());



            if (checkTargetBlock(player)) {
                if (!PlayerDateManager.addEnergy(player, -ConfigSettings.PARKOUR_CLIMBING_ENERGY.getDouble())) {
                    return;
                }
                tempVec = loc.subtract(player.getLocation()).toVector();
                callEvent(player);
                if (Check.getBlock(player, 1, 3).isAir()) {
                    vec = (new Vector(0, 0.65D, 0));
                    player.setVelocity(vec);
                    player.setFallDistance(0.0F);
                } else {
                    vec = (new Vector(0, 0.45D, 0));
                    player.setVelocity(vec);
                    player.setFallDistance(0.0F);
                }

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
