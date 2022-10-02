package cc.dreamcode.otchlan.config;

import cc.dreamcode.otchlan.features.notice.Notice;
import cc.dreamcode.otchlan.stereotypes.Configuration;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Header;
import eu.okaeri.configs.annotation.Headers;
import eu.okaeri.configs.annotation.NameModifier;
import eu.okaeri.configs.annotation.NameStrategy;
import eu.okaeri.configs.annotation.Names;

@Configuration(
        child = "message.yml"
)
@Headers({
        @Header("## Dream-Otchlan (Message-Config) ##"),
        @Header("Dostepne type: (CHAT, ACTION_BAR, SUBTITLE, TITLE, TITLE_SUBTITLE, SIDEBAR)")
})
@Names(strategy = NameStrategy.HYPHEN_CASE, modifier = NameModifier.TO_LOWER_CASE)
public class MessageConfig extends OkaeriConfig {
    public Notice noPermission = new Notice(Notice.Type.CHAT, "&4Nie posiadasz uprawnien.");
    public Notice noPlayer = new Notice(Notice.Type.CHAT, "&4Podanego gracza &cnie znaleziono.");
    public Notice cooldown = new Notice(Notice.Type.CHAT, "&4Aby to zrobic&c, poczekaj {time}.");

    public Notice firstPageReach = new Notice(Notice.Type.CHAT, "&cOsiagnieto pierwsza strone, dalej juz nic nie ma!");
    public Notice lastPageReach = new Notice(Notice.Type.CHAT, "&cOsiagnieto ostatnia strone, dalej juz nic nie ma!");
    public Notice otchlanClosed = new Notice(Notice.Type.CHAT, "&cOtchlan jest zamknieta, nie mozesz jej otworzyc!");

}
