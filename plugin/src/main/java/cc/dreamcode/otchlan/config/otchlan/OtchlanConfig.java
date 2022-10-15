package cc.dreamcode.otchlan.config.otchlan;

import cc.dreamcode.otchlan.notice.Notice;
import com.google.common.collect.ImmutableMap;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import eu.okaeri.configs.annotation.NameModifier;
import eu.okaeri.configs.annotation.NameStrategy;
import eu.okaeri.configs.annotation.Names;

import java.time.Duration;
import java.util.Map;

@Names(strategy = NameStrategy.HYPHEN_CASE, modifier = NameModifier.TO_LOWER_CASE)
public class OtchlanConfig extends OkaeriConfig {

    @Comment("Co jaki czas ma byc uruchamiana otchlan?")
    public Duration startDuration = Duration.ofMinutes(1);

    @Comment("Po jakim czasie ma byc zamknieta otchlan?")
    public Duration closeDuration = Duration.ofSeconds(30);

    @Comment("W jakich sekundach ma byc nadawane ogloszenie?")
    public Map<Long, Notice> announcements = new ImmutableMap.Builder<Long, Notice>()
            .put(30L, new Notice(Notice.Type.CHAT, "&5&lOTCHLAN: &7Zostanie otwarta za &6{time}&7."))
            .put(15L, new Notice(Notice.Type.CHAT, "&5&lOTCHLAN: &7Zostanie otwarta za &6{time}&7."))
            .put(10L, new Notice(Notice.Type.CHAT, "&5&lOTCHLAN: &7Zostanie otwarta za &6{time}&7."))
            .put(5L, new Notice(Notice.Type.CHAT, "&5&lOTCHLAN: &7Zostanie otwarta za &6{time}&7."))
            .put(4L, new Notice(Notice.Type.CHAT, "&5&lOTCHLAN: &7Zostanie otwarta za &6{time}&7."))
            .put(3L, new Notice(Notice.Type.CHAT, "&5&lOTCHLAN: &7Zostanie otwarta za &6{time}&7."))
            .put(2L, new Notice(Notice.Type.CHAT, "&5&lOTCHLAN: &7Zostanie otwarta za &6{time}&7."))
            .put(1L, new Notice(Notice.Type.CHAT, "&5&lOTCHLAN: &7Zostanie otwarta za &6{time}&7."))
            .put(0L, new Notice(Notice.Type.CHAT, "&5&lOTCHLAN: &7Zostala &aotwarta&7. (&f/otchlan&7)"))
            .build();

    @Comment("Jaka wiadomosc wyslac do graczy, gdy otchlan sie zamknie?")
    public Notice closeNotice = new Notice(Notice.Type.CHAT, "&5&lOTCHLAN: &7Zostala &czamknieta&7.");

    @Comment("Jaki cooldown ma byc dla komendy otchlan?")
    public Duration otchlanOpenCoolDown = Duration.ofSeconds(5);
}
