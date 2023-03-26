package cc.dreamcode.otchlan.command;

import cc.dreamcode.command.annotations.RequiredPlayer;
import cc.dreamcode.command.bukkit.BukkitCommand;
import cc.dreamcode.otchlan.OtchlanService;
import cc.dreamcode.otchlan.OtchlanState;
import cc.dreamcode.otchlan.config.MessageConfig;
import cc.dreamcode.otchlan.config.PluginConfig;
import cc.dreamcode.utilities.CountUtil;
import cc.dreamcode.utilities.TimeUtil;
import cc.dreamcode.utilities.builder.MapBuilder;
import eu.okaeri.injector.annotation.Inject;
import lombok.NonNull;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.time.Duration;
import java.util.List;

@RequiredPlayer
public class OtchlanCommand extends BukkitCommand {

    private @Inject PluginConfig pluginConfig;
    private @Inject MessageConfig messageConfig;
    private @Inject OtchlanService otchlanService;

    public OtchlanCommand() {
        super("otchlan");
    }

    @Override
    public void content(@NonNull CommandSender sender, @NonNull String[] args) {
        final Player player = (Player) sender;
        if (!this.otchlanService.getOtchlanState().equals(OtchlanState.OPENED)) {
            this.messageConfig.otchlanClosed.send(player);
            return;
        }

        final long time = this.otchlanService.getCacheOpenPlayerTime().getOrDefault(player.getUniqueId(), 0L);

        Duration duration = CountUtil.getCountDown(time, this.pluginConfig.otchlanOpenCoolDown);
        if (!duration.isNegative()) {
            this.messageConfig.cooldown.send(player, new MapBuilder<String, Object>()
                    .put("time", TimeUtil.convertDurationMills(duration))
                    .build());
            return;
        }

        this.otchlanService.getCacheOpenPlayerTime().put(player.getUniqueId(), System.currentTimeMillis());
        this.otchlanService.getBukkitMenuPaginated().openPage(player);
    }

    @Override
    public List<String> tab(@NonNull Player player, @NonNull String[] args) {
        return null;
    }
}
