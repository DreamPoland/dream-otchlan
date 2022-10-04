package cc.dreamcode.otchlan;

import cc.dreamcode.menu.bukkit.BukkitMenuProvider;
import cc.dreamcode.otchlan.boot.PluginBootLoader;
import cc.dreamcode.otchlan.component.ComponentHandler;
import cc.dreamcode.otchlan.config.MessageConfig;
import cc.dreamcode.otchlan.config.PluginConfig;
import cc.dreamcode.otchlan.content.OtchlanService;
import cc.dreamcode.otchlan.content.commands.OtchlanCommand;
import cc.dreamcode.otchlan.content.runnable.OtchlanStartRunnable;
import lombok.Getter;
import lombok.NonNull;
import org.bukkit.entity.HumanEntity;
import org.bukkit.plugin.java.annotation.plugin.ApiVersion;
import org.bukkit.plugin.java.annotation.plugin.Description;
import org.bukkit.plugin.java.annotation.plugin.Plugin;
import org.bukkit.plugin.java.annotation.plugin.Website;
import org.bukkit.plugin.java.annotation.plugin.author.Author;

@Plugin(name = "Dream-Otchlan", version = "1.0.3-SNAPSHOT")
@Author("Ravis96")
@Description("Otchlan plugin by DreamCode.")
@Website("DreamCode - https://discord.gg/dreamcode")
@ApiVersion(ApiVersion.Target.v1_13)
public final class OtchlanPlugin extends PluginBootLoader {

    @Getter private static OtchlanPlugin otchlanPlugin;
    @Getter private static OtchlanLogger otchlanLogger;

    @Override
    public void load() {
        // Static content for api.
        otchlanPlugin = this;
        otchlanLogger = new OtchlanLogger(otchlanPlugin.getLogger());
    }

    @Override
    public void start(@NonNull ComponentHandler componentHandler) {
        // Injectable object registering. (library etc.)
        this.registerInjectable(BukkitMenuProvider.create(this));

        // Component system inspired by okaeri-platform
        // These simple structure can register all content of this plugin. (A-Z)
        componentHandler.registerComponent(PluginConfig.class);
        componentHandler.registerComponent(MessageConfig.class);
        componentHandler.registerComponent(OtchlanService.class);
        componentHandler.registerComponent(OtchlanStartRunnable.class);
        componentHandler.registerComponent(OtchlanCommand.class);
    }

    @Override
    public void stop() {
        // features need to be call by stop server
        this.getInject(OtchlanService.class).ifPresent(otchlanService -> {
            if (otchlanService.getBukkitMenuPaginated() == null) {
                return;
            }

            otchlanService.getBukkitMenuPaginated().getViewers().forEach(HumanEntity::closeInventory);
        });
    }
}
