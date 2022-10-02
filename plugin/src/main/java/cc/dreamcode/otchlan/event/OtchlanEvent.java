package cc.dreamcode.otchlan.event;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

@Getter
@RequiredArgsConstructor
public class OtchlanEvent extends Event implements Cancellable {

    private final HandlerList handlers = new HandlerList();

    @Setter private boolean cancelled;
}
