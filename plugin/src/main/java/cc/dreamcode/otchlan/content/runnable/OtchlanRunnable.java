package cc.dreamcode.otchlan.content.runnable;

import cc.dreamcode.otchlan.OtchlanPlugin;
import cc.dreamcode.otchlan.config.PluginConfig;
import cc.dreamcode.otchlan.content.OtchlanService;
import cc.dreamcode.otchlan.content.OtchlanState;
import cc.dreamcode.otchlan.content.config.OtchlanConfig;
import cc.dreamcode.otchlan.content.menu.OtchlanMenuSetup;
import cc.dreamcode.otchlan.exception.PluginRuntimeException;
import cc.dreamcode.otchlan.features.notice.NoticeSender;
import cc.dreamcode.otchlan.utilities.CountUtil;
import cc.dreamcode.otchlan.utilities.TimeUtil;
import com.google.common.collect.ImmutableMap;
import eu.okaeri.injector.annotation.Inject;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OtchlanRunnable extends BukkitRunnable implements NoticeSender {

    private @Inject OtchlanPlugin otchlanPlugin;
    private @Inject PluginConfig pluginConfig;
    private @Inject OtchlanService otchlanService;

    /**
     * When an object implementing interface {@code Runnable} is used
     * to create a thread, starting the thread causes the object's
     * {@code run} method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method {@code run} is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
    @Override
    public void run() {
        if (!this.otchlanService.getOtchlanState().equals(OtchlanState.WAITING)) {
            this.cancel();
            return;
        }

        final OtchlanConfig otchlanConfig = this.pluginConfig.otchlanConfig;
        final long secondsLeft = CountUtil.getCountDownSeconds(
                this.otchlanService.getRunTime().get(),
                (int) otchlanConfig.startDuration.getSeconds()
        );

        Optional.ofNullable(otchlanConfig.announcements.get(secondsLeft)).ifPresent(notice ->
                this.otchlanPlugin.getServer().getOnlinePlayers().forEach(player ->
                        this.send(notice, player, new ImmutableMap.Builder<String, Object>()
                                .put("time", TimeUtil.convertSeconds(secondsLeft))
                                .build())));

        if (secondsLeft <= 0) {
            this.otchlanPlugin.getServer().getScheduler().runTask(this.otchlanPlugin, () -> {
                this.otchlanService.setBukkitMenuPaginated(
                        this.otchlanPlugin.createInstance(OtchlanMenuSetup.class).build());

                final List<ItemStack> items = new ArrayList<>();
                this.otchlanPlugin.getServer().getWorlds().forEach(world ->
                        world.getEntities()
                                .stream()
                                .filter(Item.class::isInstance)
                                .forEach(entity -> {
                                    final Item item = (Item) entity;
                                    items.add(item.getItemStack());
                                    item.remove();
                                }));

                this.otchlanService.getBukkitMenuPaginated().addStorageItems(items);
                this.otchlanService.setOtchlanState(OtchlanState.OPENED);
            });

            this.otchlanPlugin.getServer().getScheduler().runTaskLater(this.otchlanPlugin, () -> {
                if (this.otchlanService.getOtchlanState().equals(OtchlanState.CLOSED)) {
                    throw new PluginRuntimeException("Otchlan are closed when close task is running.");
                }

                this.otchlanService.setOtchlanState(OtchlanState.CLOSED);
                this.otchlanService.getRunTime().set(0L);
                this.otchlanService.getBukkitMenuPaginated().getViewers().forEach(HumanEntity::closeInventory);
                this.otchlanService.setBukkitMenuPaginated(null);

                this.otchlanPlugin.getServer().getOnlinePlayers().forEach(player ->
                        this.send(otchlanConfig.closeNotice, player));
            }, otchlanConfig.closeDuration.getSeconds() * 20);
        }
    }
}
