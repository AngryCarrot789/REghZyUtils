package reghzy.utils.command.utils;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.entity.Player;
import reghzy.mfunclagfind.utils.text.StringHelper;
import reghzy.mfunclagfind.utils.types.ItemDataPair;

import java.util.HashMap;

/**
 * A similar class to the CommandArgs class, but all of arguments are interpreted as a Key:Value pair
 * <p>
 *     If they dont contain the split character, it isn't used. this is useful for having a large
 *     amount of parameters in no specific order, but you key them with a known name
 * </p>
 */
public class CommandMappedArgs {
    private final HashMap<String, String> map;

    public CommandMappedArgs(int startIndex, CommandArgs args) {
        if (args == null) {
            this.map = new HashMap<String, String>();
        }
        else {
            this.map = args.getKeyValueMap(startIndex, ':');
        }
    }

    public CommandMappedArgs(HashMap<String, String> map) {
        if (map == null) {
            this.map = new HashMap<String, String>();
        }
        else {
            this.map = map;
        }
    }

    public boolean hasKey(String key) {
        return this.map.containsKey(key);
    }

    public String getString(String key) {
        return map.get(key);
    }

    public String getString(String key, String defaultValue) {
        String value = getString(key);
        if (value == null) {
            return defaultValue;
        }

        return value;
    }

    public Integer getInteger(String key) {
        String value = getString(key);
        if (value == null) {
            return null;
        }

        return StringHelper.parseInteger(value);
    }

    public int getInteger(String key, int defaultValue) {
        Integer value = getInteger(key);
        if (value == null) {
            return defaultValue;
        }

        return value;
    }

    public Double getDouble(String key) {
        String value = getString(key);
        if (value == null) {
            return null;
        }
        return StringHelper.parseDouble(value);
    }

    public double getInteger(String key, double defaultValue) {
        Double value = getDouble(key);
        if (value == null) {
            return defaultValue;
        }

        return value;
    }

    public Boolean getBoolean(String key) {
        String value = getString(key);
        if (value == null) {
            return null;
        }
        value = value.toLowerCase();
        return value.equals("true") || value.equals("t") || value.equals("yes") || value.equals("y");
    }

    public boolean getBoolean(String key, boolean defaultValue) {
        Boolean value = getBoolean(key);
        if (value == null) {
            return defaultValue;
        }

        return value;
    }

    public Boolean getBoolean(String key, String... trueCasesToLower) {
        String value = getString(key);
        if (value == null) {
            return null;
        }

        value = value.toLowerCase();
        for (String trueCase : trueCasesToLower) {
            if (value.equalsIgnoreCase(trueCase)) {
                return true;
            }
        }
        return false;
    }

    public World getWorld(String key) {
        String worldName = getString(key);
        if (worldName == null) {
            return null;
        }
        return Bukkit.getWorld(worldName);
    }

    public Player getPlayer(String key) {
        String playerName = getString(key);
        if (playerName == null) {
            return null;
        }

        return Bukkit.getPlayer(playerName);
    }

    public OfflinePlayer getOfflinePlayer(String key) {
        String playerName = getString(key);
        if (playerName == null) {
            return null;
        }

        return Bukkit.getOfflinePlayer(playerName);
    }

    public ItemDataPair getItemData(String key) {
        String value = getString(key);
        if (value == null) {
            return null;
        }

        int split = value.indexOf(':');
        if (split == -1) {
            Integer id = StringHelper.parseInteger(value);
            if (id == null) {
                return null;
            }

            return new ItemDataPair(id, -1);
        }
        else {
            Integer id = StringHelper.parseInteger(value.substring(0, split));
            if (id == null) {
                return null;
            }

            Integer data = StringHelper.parseInteger(value.substring(split + 1));
            if (data == null) {
                return null;
            }

            return new ItemDataPair(id, data);
        }
    }
}
