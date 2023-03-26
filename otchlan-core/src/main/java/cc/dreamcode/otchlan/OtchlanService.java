package cc.dreamcode.otchlan;

import cc.dreamcode.menu.bukkit.base.BukkitMenuPaginated;
import cc.dreamcode.platform.bukkit.exception.BukkitPluginException;
import cc.dreamcode.otchlan.event.EventHandler;
import cc.dreamcode.otchlan.event.OtchlanStartEvent;
import cc.dreamcode.otchlan.task.OtchlanRunnable;
import eu.okaeri.injector.annotation.Inject;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

@RequiredArgsConstructor
public class OtchlanService {

    private @Inject OtchlanPlugin otchlanPlugin;

    @Getter private final AtomicLong runTime = new AtomicLong();
    @Getter @Setter private BukkitMenuPaginated bukkitMenuPaginated;
    @Getter @Setter private OtchlanState otchlanState = OtchlanState.CLOSED;

    @Getter private final Map<UUID, Long> cacheOpenPlayerTime = new HashMap<>();

    public void start() {
        if (this.otchlanState.equals(OtchlanState.CLOSED)) {
            throw new BukkitPluginException("Otchlan is not closed, otchlan-task start is aborted.");
        }

        if (EventHandler.handle(new OtchlanStartEvent())) {
            return;
        }

        this.otchlanPlugin.createInstance(OtchlanRunnable.class)
                .runTaskTimerAsynchronously(this.otchlanPlugin, 20L, 20L);
    }

}
