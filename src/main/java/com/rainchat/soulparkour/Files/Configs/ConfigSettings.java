package com.rainchat.soulparkour.Files.Configs;

import com.rainchat.soulparkour.Files.FileManager;

public enum ConfigSettings {

    MAX_ENERGY("Settings.max_energy", "20.0"),
    PARTICLE_EFFECT("Settings.effect.paricle", "SMOKE_NORMAL"),
    SOUND_EFFECT("Settings.effect.sound", "BLAZE_SHOOT"),
    LANGUAGE("Settings.language","En_en"),
    EFFECT_STILE("Settings.effect_stile","none"),

    //ModulesSettings
    MODULES_CLIMBING("Modules.Сlimbing","true"),
    MODULES_CRAWLING("Modules.Crawling","true"),
    MODULES_JUMP_ON_THE_WALL("Modules.Jump_on_the_wall","true"),
    MODULES_LEAP_OF_FAITH("Modules.Leap_Of_Faith","true"),
    MODULES_WALL_JUMP("Modules.Wall_jump","true"),
    MODULES_WALL_RUN("Modules.Wall_run","true"),
    MODULES_DOUBLE_JUMP("Modules.Double_jump","false"),

    //ParkourSettings path "ParkourSettings"
    PARKOUR_DOUBLE_JUMP_HIGH("ParkourSettings.double_jump.jump_high", "0.15"),
    PARKOUR_DOUBLE_JUMP_ENERGY("ParkourSettings.double_jump.energy_need", "1"),
    PARKOUR_CLIMBING_ENERGY("ParkourSettings.Climbing.energy_need", "1"),
    PARKOUR_CRAWLING_ENERGY("ParkourSettings.Crawling.energy_need", "1"),
    PARKOUR_CRAWLING_BOOST("ParkourSettings.Crawling.boost", "true"),
    PARKOUR_JUMP_ON_THE_WALL_ENERGY("ParkourSettings.Jump_on_the_wall.energy_need", "1"),
    PARKOUR_LEAP_OF_FAITH_ENERGY("ParkourSettings.Leap_Of_Faith.energy_need", "1"),
    PARKOUR_WALL_JUMP_ENERGY("ParkourSettings.Wall_jump.energy_need", "1");


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
