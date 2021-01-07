package com.rainchat.soulparkour.utils;

import com.rainchat.soulparkour.SoulParkourMain;

import java.util.logging.Level;

public class ServerLog {


    public static void log(Level level, String message) {
        SoulParkourMain.getPluginInstance().getServer().getLogger().log(level,
                "[" + SoulParkourMain.getPluginInstance().getDescription().getName() + "] " + message);
    }

}
