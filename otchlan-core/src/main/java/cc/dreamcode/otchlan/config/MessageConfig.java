package cc.dreamcode.otchlan.config;

import cc.dreamcode.notice.minecraft.MinecraftNoticeType;
import cc.dreamcode.notice.minecraft.bukkit.BukkitNotice;
import cc.dreamcode.platform.bukkit.component.configuration.Configuration;
import com.google.common.collect.ImmutableMap;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Header;
import eu.okaeri.configs.annotation.Headers;
import eu.okaeri.configs.annotation.NameModifier;
import eu.okaeri.configs.annotation.NameStrategy;
import eu.okaeri.configs.annotation.Names;

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

    public BukkitNotice noPermission = new BukkitNotice(MinecraftNoticeType.CHAT, "&4Nie posiadasz uprawnien.");
    public BukkitNotice cooldown = new BukkitNotice(MinecraftNoticeType.CHAT, "&4Aby to zrobic&c, poczekaj {time}.");
    public BukkitNotice notPlayer = new BukkitNotice(MinecraftNoticeType.CHAT, "&4Musisz byc graczem, aby to zrobic.");

    public BukkitNotice firstPageReach = new BukkitNotice(MinecraftNoticeType.CHAT, "&cOsiagnieto pierwsza strone, dalej juz nic nie ma!");
    public BukkitNotice lastPageReach = new BukkitNotice(MinecraftNoticeType.CHAT, "&cOsiagnieto ostatnia strone, dalej juz nic nie ma!");
    public BukkitNotice otchlanClosed = new BukkitNotice(MinecraftNoticeType.CHAT, "&cOtchlan jest zamknieta, nie mozesz jej otworzyc!");

    public Map<Long, BukkitNotice> announcements = new ImmutableMap.Builder<Long, BukkitNotice>()
            .put(30L, new BukkitNotice(MinecraftNoticeType.CHAT, "&5&lOTCHLAN: &7Zostanie otwarta za &6{time}&7."))
            .put(15L, new BukkitNotice(MinecraftNoticeType.CHAT, "&5&lOTCHLAN: &7Zostanie otwarta za &6{time}&7."))
            .put(10L, new BukkitNotice(MinecraftNoticeType.CHAT, "&5&lOTCHLAN: &7Zostanie otwarta za &6{time}&7."))
            .put(5L, new BukkitNotice(MinecraftNoticeType.CHAT, "&5&lOTCHLAN: &7Zostanie otwarta za &6{time}&7."))
            .put(4L, new BukkitNotice(MinecraftNoticeType.CHAT, "&5&lOTCHLAN: &7Zostanie otwarta za &6{time}&7."))
            .put(3L, new BukkitNotice(MinecraftNoticeType.CHAT, "&5&lOTCHLAN: &7Zostanie otwarta za &6{time}&7."))
            .put(2L, new BukkitNotice(MinecraftNoticeType.CHAT, "&5&lOTCHLAN: &7Zostanie otwarta za &6{time}&7."))
            .put(1L, new BukkitNotice(MinecraftNoticeType.CHAT, "&5&lOTCHLAN: &7Zostanie otwarta za &6{time}&7."))
            .put(0L, new BukkitNotice(MinecraftNoticeType.CHAT, "&5&lOTCHLAN: &7Zostala &aotwarta&7. (&f/otchlan&7)"))
            .build();

    public BukkitNotice closeNotice = new BukkitNotice(MinecraftNoticeType.CHAT, "&5&lOTCHLAN: &7Zostala &czamknieta&7.");

}
