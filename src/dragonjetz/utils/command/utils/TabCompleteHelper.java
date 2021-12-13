package dragonjetz.utils.command.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class TabCompleteHelper {
    public static List<String> tryExtractTyped(Collection<String> values, String[] args) {
        if (args == null || args.length == 0) {
            return new ArrayList<String>(values);
        }

        return extractTyped(values, args[0]);
    }

    public static List<String> tryExtractTyped(List<String> values, String[] args) {
        if (args == null || args.length == 0) {
            return values;
        }

        return extractTyped(values, args[0]);
    }

    public static ArrayList<String> extractTyped(Collection<String> values, String typed) {
        ArrayList<String> typedValues = new ArrayList<String>(values.size());
        for (String value : values) {
            if (value.startsWith(typed)) {
                typedValues.add(value);
            }
        }
        return typedValues;
    }
}
