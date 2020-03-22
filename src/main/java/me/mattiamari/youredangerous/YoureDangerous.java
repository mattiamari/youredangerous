package me.mattiamari.youredangerous;

import org.bukkit.plugin.java.JavaPlugin;

public class YoureDangerous extends JavaPlugin {

    public void onEnable() {
        getServer().getPluginManager().registerEvents(new DangerListener(this), this);
    }
}
