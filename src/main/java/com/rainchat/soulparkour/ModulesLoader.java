package com.rainchat.soulparkour;

import com.rainchat.soulparkour.Effects.EffectListeners;
import com.rainchat.soulparkour.Events.eventregistr.*;
import com.rainchat.soulparkour.Files.Configs.ConfigSettings;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;

import java.lang.reflect.Method;

public class ModulesLoader {

    private static ModulesLoader instance = new ModulesLoader();

    private SoulParkourMain plugin;

    public static ModulesLoader getInstance() {
        return instance;
    }

    public void initialization(SoulParkourMain plugin) {
        this.plugin = plugin;
    }

    public void onLoad() {

        plugin.getServer().getPluginManager().registerEvents(new JoinEvent(), plugin);
        plugin.getServer().getPluginManager().registerEvents(new EffectListeners(), plugin);
        plugin.getServer().getPluginManager().registerEvents(new Enegry(), plugin);

        //################################################################################

        if (ConfigSettings.MODULES_CLIMBING.getBoolen()) {
            plugin.getServer().getPluginManager().registerEvents(new Сlimbing(), plugin);
        }
        //################################################################################

        if (ConfigSettings.MODULES_CLINGING.getBoolen()){
            plugin.getServer().getPluginManager().registerEvents(new Clinging(), plugin);
        }


        //################################################################################

        if (ConfigSettings.MODULES_CRAWLING.getBoolen()) {
            plugin.getServer().getPluginManager().registerEvents(new Crawling(), plugin);
        }

        //################################################################################

        if (ConfigSettings.MODULES_JUMP_ON_THE_WALL.getBoolen()) {
            plugin.getServer().getPluginManager().registerEvents(new WallJump(), plugin);
        }

        //################################################################################

        if (ConfigSettings.MODULES_DOUBLE_JUMP.getBoolen()) {
            plugin.getServer().getPluginManager().registerEvents(new DoubleJump(), plugin);
        }

        //################################################################################

        if (ConfigSettings.MODULES_LEAP_OF_FAITH.getBoolen()) {
            plugin.getServer().getPluginManager().registerEvents(new LeapOfFaith(), plugin);
        }

        //################################################################################

        if (ConfigSettings.MODULES_WALL_JUMP.getBoolen()) {
            plugin.getServer().getPluginManager().registerEvents(new WallKick(), plugin);
        }

        //################################################################################

        if (ConfigSettings.MODULES_WALL_RUN.getBoolen()) {
            plugin.getServer().getPluginManager().registerEvents(new WallRun(), plugin);
        }


        //################################################################################
    }


    private void unregisterListener(Class<? extends Event> eventClass, Listener listener) {
        try {
            // unfortunately we can't cache this reflect call, as the method is static
            Method getHandlerListMethod = eventClass.getMethod("getHandlerList");
            HandlerList handlerList = (HandlerList) getHandlerListMethod.invoke(null);
            handlerList.unregister(listener);
        } catch (Throwable t) {
            // ignored
        }
    }


    public void onDisable(SoulParkourMain plugin) {


    }


}
