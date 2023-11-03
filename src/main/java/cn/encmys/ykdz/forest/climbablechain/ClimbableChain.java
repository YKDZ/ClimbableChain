package cn.encmys.ykdz.forest.climbablechain;

import cn.encmys.ykdz.forest.climbablechain.manager.ClimbManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class ClimbableChain extends JavaPlugin {

    private static ClimbableChain instance;
    private static ClimbManager climbManager;

    @Override
    public void onEnable() {
        instance = this;
        climbManager = new ClimbManager();

        Bukkit.getPluginManager().registerEvents(climbManager, instance);

        getLogger().info("Successfully enabled ClimbableChain!");
    }

    @Override
    public void onDisable() {
        getLogger().info("Successfully disabled ClimbableChain!");
    }

    public static ClimbableChain getInstance() {
        return instance;
    }

    public static ClimbManager getClimbManager() {
        return climbManager;
    }
}
