package cc.dreamcode.otchlan.command;

import cc.dreamcode.otchlan.OtchlanPlugin;
import lombok.NonNull;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public interface CommandPlatform {
    void handle(@NonNull CommandSender sender, @NonNull String[] args);

    List<String> tab(@NonNull Player player, @NonNull String[] args);

    default <T> T createInstance(@NonNull Class<T> clazz) {
        return OtchlanPlugin.getOtchlanPlugin().createInstance(clazz);
    }
}
