package com.rainchat.soulparkour.commands;

import com.rainchat.soulparkour.Files.Configs.Language;
import com.rainchat.soulparkour.Files.FileManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

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
        Player p = (Player) sender;
        if (args[0].equalsIgnoreCase("reload")) {
            if (args.length > 1) {
                return false;
            }
            reload();
            p.sendMessage(Language.RELOAD.getmessage(true));
        }

        return true;
    }

    public void reload() {
        fileManager.reloadAllFiles();
    }
}
