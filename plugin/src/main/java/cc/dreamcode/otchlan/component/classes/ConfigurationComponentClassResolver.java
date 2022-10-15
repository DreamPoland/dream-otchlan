package cc.dreamcode.otchlan.component.classes;

import cc.dreamcode.otchlan.OtchlanPlugin;
import cc.dreamcode.otchlan.component.resolvers.ComponentClassResolver;
import cc.dreamcode.otchlan.config.ConfigSerdesPack;
import cc.dreamcode.otchlan.exception.PluginRuntimeException;
import cc.dreamcode.otchlan.config.annotations.Configuration;
import com.google.common.collect.ImmutableMap;
import eu.okaeri.configs.ConfigManager;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.serdes.commons.SerdesCommons;
import eu.okaeri.configs.yaml.bukkit.YamlBukkitConfigurer;
import eu.okaeri.configs.yaml.bukkit.serdes.SerdesBukkit;
import eu.okaeri.injector.Injector;
import eu.okaeri.injector.annotation.Inject;
import lombok.NonNull;

import java.io.File;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class ConfigurationComponentClassResolver extends ComponentClassResolver<Class<OkaeriConfig>> {

    private @Inject OtchlanPlugin otchlanPlugin;

    @Override
    public boolean isAssignableFrom(@NonNull Class<OkaeriConfig> okaeriConfigClass) {
        if (OkaeriConfig.class.isAssignableFrom(okaeriConfigClass)) {
            if (okaeriConfigClass.getAnnotation(Configuration.class) == null) {
                throw new PluginRuntimeException(okaeriConfigClass.getSimpleName() + " does not contain " + Configuration.class.getSimpleName() + " annotation.");
            }
            return true;
        }
        return false;
    }

    @Override
    public String getComponentName() {
        return "configuration";
    }

    @Override
    public Map<String, Object> getMetas(@NonNull Injector injector, @NonNull Class<OkaeriConfig> okaeriConfigClass) {
        final Configuration configuration = okaeriConfigClass.getAnnotation(Configuration.class);
        if (configuration == null) {
            throw new PluginRuntimeException("Config component must have an " + Configuration.class.getSimpleName() + " annotation.");
        }

        return new ImmutableMap.Builder<String, Object>()
                .put("path", configuration.child())
                .put("subconfigs", Arrays.stream(okaeriConfigClass.getDeclaredFields())
                        .map(Field::getName)
                        .filter(name -> name.contains("Config"))
                        .collect(Collectors.joining(", ")))
                .build();
    }

    @Override
    public Object resolve(@NonNull Injector injector, @NonNull Class<OkaeriConfig> okaeriConfigClass) {
        final Configuration configuration = okaeriConfigClass.getAnnotation(Configuration.class);
        if (configuration == null) {
            throw new PluginRuntimeException("Config component must have an " + Configuration.class.getSimpleName() + " annotation.");
        }

        return ConfigManager.create(okaeriConfigClass, (it) -> {
            it.withConfigurer(new YamlBukkitConfigurer(), new SerdesBukkit(), new SerdesCommons(), new ConfigSerdesPack());
            it.withBindFile(new File(this.otchlanPlugin.getDataFolder(), configuration.child()));
            it.saveDefaults();
            it.load(true);
        });
    }
}
