package reghzy.utils.dependency;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface CommandDependency {
    /**
     * Plugin dependencies (aka, specific plugins are required for this command to work)
     */
    DependablePlugins[] plugins() default {DependablePlugins.None};

    /**
     * Mod dependencies (aka, specific mods are required for this command to work)
     */
    DependableMod[] mods() default {DependableMod.None};
}
