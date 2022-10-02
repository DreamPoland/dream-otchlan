package cc.dreamcode.otchlan.content.menu;

import cc.dreamcode.menu.bukkit.BukkitMenuProvider;
import cc.dreamcode.menu.bukkit.base.BukkitMenu;
import cc.dreamcode.menu.bukkit.base.BukkitMenuPaginated;
import cc.dreamcode.menu.bukkit.setup.BukkitMenuPaginatedSetup;
import cc.dreamcode.menu.serdes.bukkit.BukkitMenuBuilder;
import cc.dreamcode.otchlan.config.MessageConfig;
import cc.dreamcode.otchlan.config.PluginConfig;
import cc.dreamcode.otchlan.content.config.OtchlanMenuConfig;
import cc.dreamcode.otchlan.features.notice.NoticeSender;
import eu.okaeri.injector.annotation.Inject;

public class OtchlanMenuSetup implements BukkitMenuPaginatedSetup, NoticeSender {

    private @Inject PluginConfig pluginConfig;
    private @Inject MessageConfig messageConfig;
    private @Inject BukkitMenuProvider bukkitMenuProvider;

    @Override
    public BukkitMenuPaginated build() {
        final OtchlanMenuConfig otchlanMenuConfig = this.pluginConfig.otchlanMenuConfig;
        final BukkitMenuBuilder bukkitMenuBuilder = otchlanMenuConfig.otchlanMenu;
        final BukkitMenu bukkitMenu = bukkitMenuBuilder.build();

        bukkitMenuBuilder.getItems().forEach((integer, itemStack) ->
                bukkitMenu.setItem(integer, itemStack, e -> e.setCancelled(true)));

        final BukkitMenuPaginated bukkitMenuPaginated = this.bukkitMenuProvider.createPaginated(bukkitMenu);

        bukkitMenuPaginated.setPreviousPageSlot(otchlanMenuConfig.previousPageSlot, sender ->
                this.send(this.messageConfig.firstPageReach, sender));

        bukkitMenuPaginated.setNextPageSlot(otchlanMenuConfig.nextPageSlot, sender ->
                this.send(this.messageConfig.lastPageReach, sender));

        return bukkitMenuPaginated;
    }
}
