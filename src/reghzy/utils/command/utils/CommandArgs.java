package reghzy.utils.command.utils;

import com.avaje.ebeaninternal.server.lib.util.StringHelper;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.entity.Player;
import reghzy.utils.memory.Memory;

import java.util.HashMap;
import java.util.Iterator;

/**
 * Provides functions for easily manipulating arguments. This will never throw
 * exceptions, it will simply return null to indicate a failure
 */
public class CommandArgs implements Iterable<String> {
    private final String[] arguments;

    public CommandArgs(String[] arguments) {
        if (arguments == null) {
            this.arguments = new String[0];
        }
        else {
            this.arguments = arguments;
        }
    }

    public CommandArgs(String[] arguments, int startIndex) {
        this(arguments.length >= startIndex ? Memory.extractAfter(arguments, startIndex) : new String[0]);
    }

    /**
     * Returns the value at the given index
     * @param index The index to extract the value at
     */
    public String getString(int index) {
        if (indexWithinRange(index)) {
            return this.arguments[index];
        }

        return null;
    }

    /**
     * Returns the value at the given index
     * If it couldn't... it returns the default value
     * @param index The index to extract the value at
     */
    public String getString(int index, String defaultValue) {
        String value = getString(index);
        if (value == null) {
            return defaultValue;
        }

        return value;
    }

    /**
     * Gets the value at the given index, and replaces all underscore characters (in order for bukkit to count
     * the string as a whole, not multiple args) with whitespaces
     *
     * @param index The index to extract the value at
     */
    public String getStringUnderscored(int index) {
        if (indexWithinRange(index)) {
            return this.arguments[index].replace('_', ' ');
        }

        return null;
    }

    public String getLast() {
        if (this.arguments.length > 0) {
            return this.arguments[this.arguments.length - 1];
        }

        return null;
    }

    public String getLast(int backwards) {
        int index = this.arguments.length - 1 - backwards;
        if (!indexWithinRange(index)) {
            return null;
        }

        return this.arguments[index];
    }

    /**
     * Tries to parse the value at the given index as an integer value (no decimals)
     * @param index The index to extract the value at
     */
    public Integer getInteger(int index) {
        if (indexWithinRange(index)) {
            return StringHelper.parseInteger(this.arguments[index]);
        }
        return null;
    }

    /**
     * Tries to parse the value at the given index as an integer value (no decimals)
     * If it couldn't... it will return the default value
     * @param index The index to extract the value at
     */
    public int getInteger(int index, int defaultValue) {
        Integer value = getInteger(index);
        if (value == null) {
            return defaultValue;
        }

        return value;
    }

    /**
     * Tries to parse the value at the given index as a double value (including decimals)
     * @param index The index to extract the value at
     */
    public Double getDouble(int index) {
        if (indexWithinRange(index)) {
            return StringHelper.parseDouble(this.arguments[index]);
        }

        return null;
    }

    /**
     * Tries to parse the value at the given index as a double value (including decimals)
     * If it couldn't... it will return the default value
     *
     * @param index The index to extract the value at
     */
    public double getDouble(int index, double defaultValue) {
        Double value = getDouble(index);
        if (value == null) {
            return defaultValue;
        }

        return value;
    }

    /**
     * Tries to parse the value at the given index as a boolean (if its equal to "true", "t", "yes" or "y")
     * @param index The index to extract the value at
     */
    public Boolean getBoolean(int index) {
        if (indexWithinRange(index)) {
            String value = this.arguments[index].toLowerCase();
            return value.equals("true") || value.equals("t") || value.equals("yes") || value.equals("y");
        }
        return null;
    }

    /**
     * Tries to parse the value at the given index as a double value (including decimals)
     * If it couldn't... it will return the default value
     *
     * @param index The index to extract the value at
     */
    public boolean getBoolean(int index, boolean defaultValue) {
        Boolean value = getBoolean(index);
        if (value == null) {
            return defaultValue;
        }

        return value;
    }

    /**
     * Tries to parse the value at the given index as a boolean using the given
     * values as true cases, meaning the value must be equal to any of the given values.
     * if not, it returns false
     * @param index              The index to extract the value at
     * @param lowercaseTrueCases The collection of 'true cases'
     */
    public Boolean getBooleanCases(int index, String... lowercaseTrueCases) {
        if (indexWithinRange(index)) {
            String value = this.arguments[index].toLowerCase();
            for(String trueCase : lowercaseTrueCases) {
                if (value.equalsIgnoreCase(trueCase)) {
                    return true;
                }
            }
            return false;
        }

        return null;
    }

    /**
     * Tries to parse the value at the given index as a player's name,
     * then gets it using the Bukkit.getPlayer() function
     * @param index The index to extract the player name at
     */
    public Player getPlayer(int index) {
        if (indexWithinRange(index)) {
            String name = this.arguments[index];
            if (name == null || name.isEmpty()) {
                return null;
            }

            return Bukkit.getPlayer(name);
        }

        return null;
    }

    /**
     * Tries to parse the value at the given index as an offline player's name,
     * then gets it using the Bukkit.getOfflinePlayer() function
     *
     * @param index The index to extract the offline player name at
     */
    public OfflinePlayer getOfflinePlayer(int index) {
        if (indexWithinRange(index)) {
            String name = this.arguments[index];
            if (name == null || name.isEmpty()) {
                return null;
            }

            return Bukkit.getOfflinePlayer(name);
        }

        return null;
    }

    /**
     * Tries to parse the value at the given index as a world,
     * then gets it using the Bukkit.getWorld() function
     * @param index The index to extract the world name at
     */
    public World getWorld(int index) {
        if (indexWithinRange(index)) {
            return Bukkit.getWorld(this.arguments[index]);
        }

        return null;
    }

    /**
     * Parses the value at the given index and the 2 values after as X, Y and
     * Z values and puts into a location instance (using the given world reference)
     * @param startIndex The starting index to parse the X value and the next 3 values
     */
    public Location getLocation(World world, int startIndex) {
        if (!indexWithinRange(startIndex + 2)) {
            return null;
        }

        Double x = StringHelper.parseDouble(this.arguments[startIndex]);
        if (x == null) {
            return null;
        }

        Double y = StringHelper.parseDouble(this.arguments[startIndex + 1]);
        if (y == null) {
            return null;
        }

        Double z = StringHelper.parseDouble(this.arguments[startIndex + 2]);
        if (z == null) {
            return null;
        }

        return new Location(world, x, y, z);
    }

    /**
     * Parses the value at the given index as a world, and the 3
     * values after as X, Y and Z values and puts into a location instance
     * @param startIndex The starting index to parse the world and the next 3 values
     */
    public Location getLocation(int startIndex) {
        if (!indexWithinRange(startIndex + 3)) {
            return null;
        }

        World world = Bukkit.getWorld(this.arguments[startIndex]);
        if (world == null) {
            return null;
        }

        Double x = StringHelper.parseDouble(this.arguments[startIndex + 1]);
        if (x == null) {
            return null;
        }

        Double y = StringHelper.parseDouble(this.arguments[startIndex + 2]);
        if (y == null) {
            return null;
        }

        Double z = StringHelper.parseDouble(this.arguments[startIndex + 3]);
        if (z == null) {
            return null;
        }

        return new Location(world, x, y, z);

    }

    public ItemDataPair getItemData(int startIndex) {
        if (indexWithinRange(startIndex)) {
            String value = this.arguments[startIndex];
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

        return null;
    }

    public SplitString getSplitString(int startIndex, char splitter) {
        if (indexWithinRange(startIndex)) {
            return SplitString.split(this.arguments[startIndex], splitter);
        }

        return null;
    }

    /**
     * Loops through all the arguments and tries to find a value that beings with '-', and where the rest of the characters are equal to the given flag
     * @param flag                The flag to search for
     * @param startIndexInclusive The index to start searching through all the arguments (inclusive, so 0 starts at the very beginning)
     */
    public boolean hasFlag(String flag, int startIndexInclusive) {
        if (!indexWithinRange(startIndexInclusive)) {
            return false;
        }

        String[] args = this.arguments;
        for (int i = startIndexInclusive, len = args.length; i < len; i++) {
            String element = args[i];
            if (element.charAt(0) == '-' && element.substring(1).equalsIgnoreCase(flag)) {
                return true;
            }
        }
        return false;
    }

    public String getFlagValue(String flag, int startIndex) {
        if (!indexWithinRange(startIndex)) {
            return null;
        }

        String[] args = this.arguments;
        for (int i = startIndex, len = args.length, lenIndex = len - 1; i < len; i++) {
            String element = args[i];
            if (element.charAt(0) == '-' && element.substring(1).equalsIgnoreCase(flag)) {
                if (i == lenIndex) {
                    return null;
                }

                return args[i + 1];
            }
        }
        return null;
    }

    public String getFlagValue(char flag, int startIndex) {
        if (!indexWithinRange(startIndex)) {
            return null;
        }

        String[] args = this.arguments;
        for (int i = startIndex, len = args.length, lenIndex = len - 1; i < len; i++) {
            String element = args[i];
            if (element.charAt(0) == '-' && element.charAt(1) == flag) {
                if (i == lenIndex) {
                    return null;
                }

                return args[i + 1];
            }
        }
        return null;
    }

    /**
     * Iterates through all arguments (starting at the given start index) and returns the first occurrence of the given flag, or -1 if one isn't found
     */
    public int getFlagIndex(char flag, int startIndex) {
        if (!indexWithinRange(startIndex)) {
            return -1;
        }

        String[] args = this.arguments;
        for (int i = startIndex, len = args.length; i < len; i++) {
            String element = args[i];
            if (element.charAt(0) == '-' && element.charAt(1) == flag) {
                return i;
            }
        }

        return -1;
    }

    /**
     * Iterates through all arguments (starting at the given start index) and returns the first occurrence of the given flag, or -1 if one isn't found
     */
    public int getFlagIndex(String flag, int startIndex) {
        if (!indexWithinRange(startIndex)) {
            return -1;
        }

        String[] args = this.arguments;
        for (int i = startIndex, len = args.length; i < len; i++) {
            String element = args[i];
            if (element.charAt(0) == '-' && element.substring(1).equalsIgnoreCase(flag)) {
                return i;
            }
        }

        return -1;
    }

    /**
     * Loops through all the arguments and tries to find a value that beings with '-', and ends with the given character flag
     * @param flag                The flag to search for
     * @param startIndexInclusive The index to start searching through all the arguments (inclusive, so 0 starts at the very beginning)
     */
    public boolean hasFlag(char flag, int startIndexInclusive) {
        if (!indexWithinRange(startIndexInclusive)) {
            return false;
        }

        String[] args = this.arguments;
        for (int i = startIndexInclusive, len = args.length; i < len; i++) {
            String element = args[i];
            if (element.charAt(0) == '-' && element.charAt(1) == flag) {
                return true;
            }
        }
        return false;
    }

    public boolean hasAnyFlag(int startIndexInclusive, String... flags) {
        if (!indexWithinRange(startIndexInclusive)) {
            return false;
        }

        String[] args = this.arguments;
        for (int i = startIndexInclusive, len = flags.length; i < len; i++) {
            String element = args[i];
            if (element.charAt(0) == '-') {
                String flagValue = element.substring(1);
                for (String flag : flags) {
                    if (flagValue.equals(flag)) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    public boolean hasAnyFlag(int startIndexInclusive, char... flags) {
        if (!indexWithinRange(startIndexInclusive)) {
            return false;
        }

        String[] args = this.arguments;
        for (int i = startIndexInclusive, len = flags.length; i < len; i++) {
            String element = args[i];
            if (element.charAt(0) == '-') {
                char flagChar = element.charAt(1);
                for(char flag : flags) {
                    if (flagChar == flag) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    /**
     * This is an easier way to find a key:value based argument. You could use the getKeyValueMap() function...
     * but that might be quite slow, especially if you only need to find 1 thing
     * <p>
     *     Recommend you do use that function (or getMappedArgs()) for many values... because this
     *     method iterates through every element, and creates new class instances every time (aka this method is quite inefficient)
     * </p>
     * @param key
     * @param splitter
     * @return
     */
    public String getKeyValue(String key, char splitter) {
        for(String element : this.arguments) {
            int split = element.indexOf(splitter);
            if (split == -1) {
                continue;
            }

            if (element.substring(0, split).equalsIgnoreCase(key)) {
                return element.substring(split + 1);
            }
        }

        return null;
    }

    /**
     * Creates a hashmap which contains all of the elements which are parsable as a "key:value" (the splitter being the ':' char)
     * <p>
     *     This is useful, so that instead of having arguments defined in specific order,
     *     you can simply refer 'world' as 'w', so 'w:worldName'
     * </p>
     * @param splitter The character that is used to split the key from the value. normally, this is the colon (':') character
     */
    public HashMap<String, String> getKeyValueMap(int startIndex, char splitter) {
        HashMap<String, String> map = new HashMap<String, String>();
        for (int i = startIndex, len = this.arguments.length; i < len; i++) {
            String string = this.arguments[i];
            int splitIndex = string.indexOf(splitter);
            if (splitIndex > -1 && splitIndex < string.length()) {
                map.put(string.substring(0, splitIndex), string.substring(splitIndex + 1));
            }
        }
        return map;
    }

    public CommandMappedArgs getMappedArgs(int startIndex) {
        return new CommandMappedArgs(startIndex, this);
    }

    public CommandMappedArgs getMappedArgs() {
        return new CommandMappedArgs(0, this);
    }

    /**
     * Combines all of the values in the internal array into a
     * single string, joining it with the whitespace character (' ')
     */
    public String concat() {
        return StringHelper.joinArray(this.arguments, 0, ' ');
    }

    /**
     * Combines all of the values in the internal array into a single string
     *
     * @param joinChar The character to add in between each element
     */
    public String concat(char joinChar) {
        return StringHelper.joinArray(this.arguments, 0, joinChar);
    }

    /**
     * Combines all of the values in the internal array into a single string
     *
     * @param joinText The text to add in between each element
     */
    public String concat(String joinText) {
        return StringHelper.joinArray(this.arguments, 0, joinText);
    }

    /**
     * Combines all of the values in the internal array into a single string starting at the given start index
     * @param index         The start index. 0 will concat every element, 1 concatenates all but the first
     * @param joinCharacter The character to add in between each element
     */
    public String concat(int index, char joinCharacter) {
        return StringHelper.joinArray(this.arguments, index, joinCharacter);
    }

    /**
     * Combines the values in the internal array into a single string starting at the given start index
     *
     * @param index    The start index. 0 will concat every element, 1 concatenates all but the first
     * @param joinText The text to add in between each element
     */
    public String concat(int index, String joinText) {
        return StringHelper.joinArray(this.arguments, index, joinText);
    }

    public CommandArgs removeStart() {
        if (this.arguments.length > 1) {
            return new CommandArgs(Memory.extractAfter(this.arguments, 1));
        }

        return new CommandArgs(new String[0]);
    }

    /**
     * Returns the internal array (same reference)
     */
    public String[] getArguments() {
        return this.arguments;
    }

    public int getArgsLength() {
        return this.arguments.length;
    }

    /**
     * Returns true if this contains any arguments (aka the internal array size is bigger than 0)
     */
    public boolean hasAnyArgs() {
        return this.arguments.length > 0;
    }

    /**
     * Checks if the given index can index within the internal array without throwing an IndexOutOfBoundsException
     * @param index The index that you need to index to within the internal array
     * @return False if the index is bigger than or equal to the length of the internal array. Otherwise, true
     */
    private boolean indexWithinRange(int index) {
        return index < this.arguments.length;
    }

    @Override
    public Iterator<String> iterator() {
        return new CommandArgsIterator();
    }

    private class CommandArgsIterator implements Iterator<String> {
        private int index = 0;

        @Override
        public boolean hasNext() {
            return index < arguments.length;
        }

        @Override
        public String next() {
            return arguments[index++];
        }

        @Override
        public void remove() {

        }
    }
}
