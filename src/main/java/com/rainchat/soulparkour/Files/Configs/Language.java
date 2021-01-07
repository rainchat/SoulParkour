package com.rainchat.soulparkour.Files.Configs;

import com.rainchat.soulparkour.Files.FileManager;
import com.rainchat.soulparkour.utils.Color;
import org.bukkit.configuration.file.FileConfiguration;


public enum Language {

    NO_PERMISSION("Message.No-Permissions", "&cYou do not have permission to use this command"),
    TOGGLE_ON("Message.Toggle-On", "&7You toggle parkour mode - &aon"),
    TOGGLE_OFF("Message.Toggle-Off", "&7You toggle parkour mode - &coff"),
    RELOAD("Message.Reload", "&7All configs have been reloaded..");

    private final String path;
    private final String defaultMessage;

    Language(String path, String defaultMessage) {
        this.path = path;
        this.defaultMessage = defaultMessage;
    }



    public static void addMissingMessages() {
        FileConfiguration messages = FileManager.getInstance().getLanguage().getFile();
        boolean saveFile = false;
        for (Language message : values()) {
            if (!messages.contains(message.path)) {
                saveFile = true;
                if (message.defaultMessage != null) {
                    messages.set(message.path, message.defaultMessage);
                }
            }
            if (saveFile) {
                FileManager.getInstance().getLanguage().saveFile();
            }
        }
    }


    public String getmessage(Boolean preffix) {
        FileConfiguration file = FileManager.getInstance().getLanguage().getFile();
        if (preffix){
            return Color.parseHexString(file.getString("Preffix", "&f[&bSoulParkour&f] ") + file.getString(path,defaultMessage));
        }
        return Color.parseHexString(file.getString(path,defaultMessage));
    }
}
