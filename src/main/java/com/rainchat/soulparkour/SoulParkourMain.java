package com.rainchat.soulparkour;

import com.rainchat.soulparkour.Events.FallEvent;
import com.rainchat.soulparkour.Events.Parkour;
import com.rainchat.soulparkour.Files.FileManager;
import com.rainchat.soulparkour.commands.SoulParkourCMD;
import org.bukkit.command.CommandExecutor;
import org.bukkit.plugin.java.JavaPlugin;

public final class SoulParkourMain extends JavaPlugin {
    private static SoulParkourMain pluginInstance;
    private FileManager fileManager = FileManager.getInstance();

    @Override
    public void onEnable() {
        setPluginInstance(this);
        fileManager.logInfo(true)
                .setup(pluginInstance);

        getCommand("soulparkour").setExecutor(new SoulParkourCMD());
        getServer().getPluginManager().registerEvents(new Parkour(), this);
        getServer().getPluginManager().registerEvents(new FallEvent(), this);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static SoulParkourMain getPluginInstance() {
        return pluginInstance;
    }

    private static void setPluginInstance(SoulParkourMain pluginInstance) {
        SoulParkourMain.pluginInstance = pluginInstance;
    }
}
