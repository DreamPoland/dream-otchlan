package cc.dreamcode.otchlan.menu;

import cc.dreamcode.menu.bukkit.BukkitMenuBuilder;
import cc.dreamcode.menu.bukkit.BukkitMenuProvider;
import cc.dreamcode.menu.bukkit.base.BukkitMenu;
import cc.dreamcode.menu.bukkit.base.BukkitMenuPaginated;
import cc.dreamcode.menu.bukkit.setup.BukkitMenuPaginatedSetup;
import cc.dreamcode.otchlan.config.MessageConfig;
import cc.dreamcode.otchlan.config.PluginConfig;
import eu.okaeri.injector.annotation.Inject;

public class OtchlanMenuSetup implements BukkitMenuPaginatedSetup {

    private @Inject PluginConfig pluginConfig;
    private @Inject MessageConfig messageConfig;
    private @Inject BukkitMenuProvider bukkitMenuProvider;

    @Override
    public BukkitMenuPaginated build() {
        final BukkitMenuBuilder bukkitMenuBuilder = this.pluginConfig.otchlanMenu;
        final BukkitMenu bukkitMenu = bukkitMenuBuilder.buildEmpty();

        bukkitMenuBuilder.getItems().forEach((integer, itemStack) ->
                bukkitMenu.setItem(integer, itemStack, e -> e.setCancelled(true)));

        final BukkitMenuPaginated bukkitMenuPaginated = this.bukkitMenuProvider.createPaginated(bukkitMenu);

        bukkitMenuPaginated.setPreviousPageSlot(this.pluginConfig.previousPageSlot, sender ->
                this.messageConfig.firstPageReach.send(sender));

        bukkitMenuPaginated.setNextPageSlot(this.pluginConfig.nextPageSlot, sender ->
                this.messageConfig.lastPageReach.send(sender));

        return bukkitMenuPaginated;
    }
}
