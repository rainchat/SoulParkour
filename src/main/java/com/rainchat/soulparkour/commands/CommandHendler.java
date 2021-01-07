package com.rainchat.soulparkour.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

public class CommandHendler implements TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List e = new ArrayList();
        if (args.length == 1) {
            e.add("reload");
            e.add("toggle");
            e.add("check");
            e.add("shulker");
            return e;
        }
        return null;
    }
}
