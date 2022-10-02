package cc.dreamcode.otchlan.event;

import lombok.NonNull;
import org.bukkit.Bukkit;

public class EventHandler {

    /**
     * @return is event cancelled
     */
    public static boolean handle(@NonNull OtchlanEvent otchlanEvent) {
        Bukkit.getPluginManager().callEvent(otchlanEvent);

        return otchlanEvent.isCancelled();
    }
}
