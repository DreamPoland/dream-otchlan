package cc.dreamcode.otchlan.config;

import cc.dreamcode.notice.NoticeType;
import cc.dreamcode.notice.bukkit.BukkitNotice;
import cc.dreamcode.platform.bukkit.component.configuration.Configuration;
import com.google.common.collect.ImmutableMap;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Header;
import eu.okaeri.configs.annotation.Headers;
import eu.okaeri.configs.annotation.NameModifier;
import eu.okaeri.configs.annotation.NameStrategy;
import eu.okaeri.configs.annotation.Names;

import java.time.Duration;
import java.util.Map;

@Configuration(
        child = "message.yml"
)
@Headers({
        @Header("## Dream-Otchlan (Message-Config) ##"),
        @Header("Dostepne type: (CHAT, ACTION_BAR, SUBTITLE, TITLE, TITLE_SUBTITLE)")
})
@Names(strategy = NameStrategy.HYPHEN_CASE, modifier = NameModifier.TO_LOWER_CASE)
public class MessageConfig extends OkaeriConfig {

    public BukkitNotice noPermission = new BukkitNotice(NoticeType.CHAT, "&4Nie posiadasz uprawnien.");
    public BukkitNotice cooldown = new BukkitNotice(NoticeType.CHAT, "&4Aby to zrobic&c, poczekaj {time}.");
    public BukkitNotice notPlayer = new BukkitNotice(NoticeType.CHAT, "&4Musisz byc graczem, aby to zrobic.");

    public BukkitNotice firstPageReach = new BukkitNotice(NoticeType.CHAT, "&cOsiagnieto pierwsza strone, dalej juz nic nie ma!");
    public BukkitNotice lastPageReach = new BukkitNotice(NoticeType.CHAT, "&cOsiagnieto ostatnia strone, dalej juz nic nie ma!");
    public BukkitNotice otchlanClosed = new BukkitNotice(NoticeType.CHAT, "&cOtchlan jest zamknieta, nie mozesz jej otworzyc!");

    public Map<Duration, BukkitNotice> announcements = new ImmutableMap.Builder<Duration, BukkitNotice>()
            .put(Duration.ofSeconds(30), new BukkitNotice(NoticeType.CHAT, "&5&lOTCHLAN: &7Zostanie otwarta za &6{time}&7."))
            .put(Duration.ofSeconds(15), new BukkitNotice(NoticeType.CHAT, "&5&lOTCHLAN: &7Zostanie otwarta za &6{time}&7."))
            .put(Duration.ofSeconds(10), new BukkitNotice(NoticeType.CHAT, "&5&lOTCHLAN: &7Zostanie otwarta za &6{time}&7."))
            .put(Duration.ofSeconds(5), new BukkitNotice(NoticeType.CHAT, "&5&lOTCHLAN: &7Zostanie otwarta za &6{time}&7."))
            .put(Duration.ofSeconds(4), new BukkitNotice(NoticeType.CHAT, "&5&lOTCHLAN: &7Zostanie otwarta za &6{time}&7."))
            .put(Duration.ofSeconds(3), new BukkitNotice(NoticeType.CHAT, "&5&lOTCHLAN: &7Zostanie otwarta za &6{time}&7."))
            .put(Duration.ofSeconds(2), new BukkitNotice(NoticeType.CHAT, "&5&lOTCHLAN: &7Zostanie otwarta za &6{time}&7."))
            .put(Duration.ofSeconds(1), new BukkitNotice(NoticeType.CHAT, "&5&lOTCHLAN: &7Zostanie otwarta za &6{time}&7."))
            .put(Duration.ofSeconds(0), new BukkitNotice(NoticeType.CHAT, "&5&lOTCHLAN: &7Zostala &aotwarta&7. (&f/otchlan&7)"))
            .build();

    public BukkitNotice closeNotice = new BukkitNotice(NoticeType.CHAT, "&5&lOTCHLAN: &7Zostala &czamknieta&7.");

}
