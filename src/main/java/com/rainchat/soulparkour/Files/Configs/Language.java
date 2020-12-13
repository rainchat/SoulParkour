package com.rainchat.soulparkour.Files.Configs;

import com.rainchat.soulparkour.Files.FileManager;
import com.rainchat.soulparkour.utils.Color;
import org.bukkit.configuration.file.FileConfiguration;

public enum Language {

    NO_PERMISSION("Message.No-Permissions", "&cYou do not have permission to use this command"),
    RELOAD("Message.Reload", "&7All configs have been reloaded..");

    private final String path;
    private final String defaultMessage;

    Language(String path, String defaultMessage) {
        this.path = path;
        this.defaultMessage = defaultMessage;
    }


    public String getmessage(Boolean preffix) {
        FileConfiguration file = FileManager.Files.LANGUAGE.getFile();
        if (preffix){
            return Color.parseHexString(file.getString("Preffix", "&f[&bSoulParkour&f] ") + file.getString(path,defaultMessage));
        }
        return Color.parseHexString(file.getString(path,defaultMessage));
    }

    public String getmessage() {
        return Color.parseHexString(FileManager.Files.LANGUAGE.getFile().getString("Preffix") + FileManager.Files.LANGUAGE.getFile().getString(path, defaultMessage));

    }
}
