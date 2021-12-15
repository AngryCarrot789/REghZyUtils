package reghzy.utils.dependency;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;

public enum DependablePlugins {
    None(null),
    WorldEdit("WorldEdit"),
    WorldGuard("WorldGuard"),
    ChestShop("ChestShop"),
    CoreProtect("CoreProtect"),
    PermissionsEx("PermissionsEx"),
    MFuncLagFind("MFuncLagFind");

    public final PluginManager pluginManager = Bukkit.getPluginManager();
    private final String pluginName;

    DependablePlugins(String pluginName) {
        this.pluginName = pluginName;
    }

    public String getPluginName() {
        return pluginName;
    }

    public boolean isAvailable() {
        if (this.pluginName == null) {
            return true;
        }

        return pluginManager.getPlugin(this.pluginName) != null;
    }
}
