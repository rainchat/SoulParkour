package com.rainchat.soulparkour.commands;

import com.rainchat.soulparkour.Files.Configs.Language;
import com.rainchat.soulparkour.Files.FileManager;
import com.rainchat.soulparkour.Files.database.PlayerDateManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.sql.SQLException;

public class SoulParkourCMD implements CommandExecutor {
    private FileManager fileManager = FileManager.getInstance();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            if (args[0].equalsIgnoreCase("reload")) {
                if (args.length > 1) {
                    System.out.println(Language.RELOAD.getmessage(true));
                    return true;
                }
                reload();
            }

            return true;
        }
        if (args.length == 0){
            return true;
        }
        Player p = (Player) sender;
        if (args[0].equalsIgnoreCase("reload")) {

            if (args.length > 1) {
                return false;
            }
            if (p.hasPermission("soulparkour.admin.reload")){
                reload();
                FileManager.Files.CONFIG.relaodFile();
                p.sendMessage(Language.RELOAD.getmessage(true));
            }
            else {
                p.sendMessage(Language.NO_PERMISSION.getmessage(true));
            }
        }

        if (args[0].equalsIgnoreCase("check")) {


            if (args.length > 1) {
                return false;
            }
            try {
                Bukkit.broadcastMessage("" + PlayerDateManager.getEnergy(p));
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

        if (args[0].equalsIgnoreCase("toggle")) {

            if (args.length > 1) {
                return false;
            }
            try {
                if (PlayerDateManager.toggleMode(p)){
                    p.sendMessage(Language.TOGGLE_ON.getmessage(true));
                } else {
                    p.sendMessage(Language.TOGGLE_OFF.getmessage(true));
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

        return true;
    }

    public void reload() {
        fileManager.reloadAllFiles();
    }
}
