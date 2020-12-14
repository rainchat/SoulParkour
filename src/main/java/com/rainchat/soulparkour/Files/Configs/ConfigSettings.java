package com.rainchat.soulparkour.Files.Configs;

import com.rainchat.soulparkour.Files.FileManager;

public enum ConfigSettings {
    DOUBLE_JUMP_HIGH("Settings.double_jump.jump_high", "0.15"),
    ENERGY_TO_CLIMB("Settings.energy_to_climb", "12"),
    MAX_BOUNCES_OFF_THE_WALL("Settings.max_bounces_off_the_wall", "4"),
    CAN_HOLDING_TO_WALL("Settings.can_holding_to_wall", "true"),
    DOUBLE_JUMP_PARTICLE("Settings.double_jump.paricle", "SMOKE_NORMAL"),
    DOUBLE_JUMP_SOUND("Settings.double_jump.sound", "BLAZE_SHOOT");


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
