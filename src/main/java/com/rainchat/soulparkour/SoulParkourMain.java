package com.rainchat.soulparkour;

import com.rainchat.soulparkour.Events.*;
import com.rainchat.soulparkour.Files.Configs.Language;
import com.rainchat.soulparkour.Files.FileManager;
import com.rainchat.soulparkour.Files.database.SqlLite;
import com.rainchat.soulparkour.commands.CommandHendler;
import com.rainchat.soulparkour.commands.SoulParkourCMD;
import org.bukkit.plugin.java.JavaPlugin;

public final class SoulParkourMain extends JavaPlugin {
    private static SoulParkourMain pluginInstance;
    private FileManager fileManager = FileManager.getInstance();
    private ModulesLoader modules = ModulesLoader.getInstance();
    private EnergyScheduler energyScheduler = EnergyScheduler.getInstance();
    private SqlLite sqlLite = SqlLite.getInstance();

    @Override
    public void onEnable() {

        setPluginInstance(this);
        modules.initialization(this);
        sqlLite.onLoad();

        String languages = "/Languages";
        fileManager.logInfo(true)
                .registerCustomFilesFolder("/Languages")
                .registerDefaultGenerateFiles("En_en.yml","/Languages", languages)
                .registerDefaultGenerateFiles("Ru_ru.yml","/Languages", languages)
                .setup(pluginInstance);

        Language.addMissingMessages();

        getCommand("soulparkour").setExecutor(new SoulParkourCMD());
        getCommand("soulparkour").setTabCompleter(new CommandHendler());

        energyScheduler.open();
        modules.onLoad();


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
