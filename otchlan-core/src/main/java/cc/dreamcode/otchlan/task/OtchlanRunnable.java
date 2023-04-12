package cc.dreamcode.otchlan.task;

import cc.dreamcode.platform.bukkit.exception.BukkitPluginException;
import cc.dreamcode.otchlan.OtchlanPlugin;
import cc.dreamcode.otchlan.OtchlanService;
import cc.dreamcode.otchlan.OtchlanState;
import cc.dreamcode.otchlan.config.MessageConfig;
import cc.dreamcode.otchlan.config.PluginConfig;
import cc.dreamcode.otchlan.menu.OtchlanMenuSetup;
import cc.dreamcode.utilities.CountUtil;
import cc.dreamcode.utilities.TimeUtil;
import cc.dreamcode.utilities.builder.MapBuilder;
import eu.okaeri.injector.annotation.Inject;
import eu.okaeri.tasker.core.Tasker;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Item;
import org.bukkit.scheduler.BukkitRunnable;

import java.time.Duration;
import java.util.Optional;

public class OtchlanRunnable extends BukkitRunnable {

    private @Inject OtchlanPlugin otchlanPlugin;
    private @Inject PluginConfig pluginConfig;
    private @Inject MessageConfig messageConfig;
    private @Inject OtchlanService otchlanService;
    private @Inject Tasker tasker;

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

        final Duration duration = CountUtil.getCountDown(
                this.otchlanService.getRunTime().get(),
                this.pluginConfig.startDuration
        );

        Optional.ofNullable(this.messageConfig.announcements.get(duration.getSeconds())).ifPresent(notice ->
                this.otchlanPlugin.getServer().getOnlinePlayers().forEach(player ->
                        notice.send(player, new MapBuilder<String, Object>()
                                .put("time", TimeUtil.convertDurationSeconds(duration))
                                .build())));

        if (duration.isNegative()) {

            this.otchlanService.setBukkitMenuPaginated(
                    this.otchlanPlugin.createInstance(OtchlanMenuSetup.class).build());

            this.otchlanPlugin.getServer().getWorlds().forEach(world ->
                    world.getEntities()
                            .stream()
                            .filter(Item.class::isInstance)
                            .forEach(entity -> {
                                final Item item = (Item) entity;
                                this.otchlanService.getBukkitMenuPaginated().addStorageItem(item.getItemStack());
                                item.remove();
                            }));

            this.otchlanService.setOtchlanState(OtchlanState.OPENED);

            this.tasker.newDelayer(this.pluginConfig.closeDuration)
                    .delayed(() -> {
                        if (this.otchlanService.getOtchlanState().equals(OtchlanState.CLOSED)) {
                            throw new BukkitPluginException("Otchlan are closed when close task is running.");
                        }

                        this.otchlanService.setOtchlanState(OtchlanState.CLOSED);
                        this.otchlanService.getRunTime().set(0L);
                        this.otchlanService.getBukkitMenuPaginated().getViewers().forEach(HumanEntity::closeInventory);
                        this.otchlanService.setBukkitMenuPaginated(null);

                        this.otchlanPlugin.getServer().getOnlinePlayers().forEach(player ->
                                this.messageConfig.closeNotice.send(player));
                    })
                    .executeSync();
        }
    }
}
