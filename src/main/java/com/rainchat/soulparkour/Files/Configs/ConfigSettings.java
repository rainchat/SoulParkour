package com.rainchat.soulparkour.Files.Configs;

import com.rainchat.soulparkour.Files.FileManager;

public enum ConfigSettings {
    DOUBLE_JUMP("double_jump", "0.15"),
    ENERGY("energy", "12"),
    MAX_BOUNCES_OFF_THE_WALL("max_bounces_off_the_wall", "4"),
    CAN_HOLDING_TO_WALL("can_holding_to_wall", "true");


    String path;
    String settings;

    ConfigSettings(String path, String settings) {
        this.path = path;
        this.settings = settings;
    }


    public String getString() {
        return FileManager.Files.CONFIG.getFile().getString(path, settings);
    }

    public int getInt() {
        return FileManager.Files.CONFIG.getFile().getInt(path, Integer.parseInt(settings));
    }

    public double getDouble() {
        return FileManager.Files.CONFIG.getFile().getDouble(path, Double.parseDouble(settings));
    }

    public boolean getBoolen() {
        return FileManager.Files.CONFIG.getFile().getBoolean(path, Boolean.parseBoolean(settings));
    }

}
