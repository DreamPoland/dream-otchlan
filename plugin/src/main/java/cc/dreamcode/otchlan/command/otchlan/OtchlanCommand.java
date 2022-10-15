package cc.dreamcode.otchlan.command.otchlan;

import cc.dreamcode.otchlan.OtchlanService;
import cc.dreamcode.otchlan.OtchlanState;
import cc.dreamcode.otchlan.builder.MapBuilder;
import cc.dreamcode.otchlan.command.CommandHandler;
import cc.dreamcode.otchlan.command.annotations.RequiredPlayer;
import cc.dreamcode.otchlan.config.MessageConfig;
import cc.dreamcode.otchlan.config.PluginConfig;
import cc.dreamcode.otchlan.config.otchlan.OtchlanConfig;
import cc.dreamcode.otchlan.utilities.CountUtil;
import cc.dreamcode.otchlan.utilities.TimeUtil;
import eu.okaeri.injector.annotation.Inject;
import lombok.NonNull;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

@RequiredPlayer
public class OtchlanCommand extends CommandHandler {

    private @Inject PluginConfig pluginConfig;
    private @Inject MessageConfig messageConfig;
    private @Inject OtchlanService otchlanService;

    public OtchlanCommand() {
        super("otchlan", null);
    }

    @Override
    public void handle(@NonNull CommandSender sender, @NonNull String[] args) {
        final Player player = (Player) sender;
        if (!this.otchlanService.getOtchlanState().equals(OtchlanState.OPENED)) {
            this.send(this.messageConfig.otchlanClosed, player);
            return;
        }

        final OtchlanConfig otchlanConfig = this.pluginConfig.otchlanConfig;
        final long time = this.otchlanService.getCacheOpenPlayerTime().getOrDefault(player.getUniqueId(), 0L);

        if (!CountUtil.isOut(time, otchlanConfig.otchlanOpenCoolDown)) {
            this.send(this.messageConfig.cooldown, player, new MapBuilder<String, Object>()
                    .put("time", TimeUtil.convertMills(CountUtil.getCountDownMills(
                            time, otchlanConfig.otchlanOpenCoolDown
                    )))
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
