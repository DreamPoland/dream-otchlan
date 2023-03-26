package cc.dreamcode.otchlan.config;

import cc.dreamcode.menu.serdes.bukkit.BukkitMenuBuilder;
import cc.dreamcode.platform.bukkit.component.configuration.Configuration;
import cc.dreamcode.utilities.builder.MapBuilder;
import cc.dreamcode.utilities.bukkit.builder.ItemBuilder;
import com.cryptomorin.xseries.XMaterial;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import eu.okaeri.configs.annotation.Header;
import eu.okaeri.configs.annotation.NameModifier;
import eu.okaeri.configs.annotation.NameStrategy;
import eu.okaeri.configs.annotation.Names;
import org.bukkit.inventory.ItemStack;

import java.time.Duration;

@Configuration(
        child = "config.yml"
)
@Header("## Dream-Otchlan (Main-Config) ##")
@Names(strategy = NameStrategy.HYPHEN_CASE, modifier = NameModifier.TO_LOWER_CASE)
public class PluginConfig extends OkaeriConfig {
    @Comment("Debug pokazuje dodatkowe informacje do konsoli. Lepiej wylaczyc. :P")
    public boolean debug = true;

    @Comment("Co jaki czas ma byc uruchamiana otchlan?")
    public Duration startDuration = Duration.ofMinutes(1);

    @Comment("Po jakim czasie ma byc zamknieta otchlan?")
    public Duration closeDuration = Duration.ofSeconds(30);

    @Comment("Jaki cooldown ma byc dla komendy otchlan?")
    public Duration otchlanOpenCoolDown = Duration.ofSeconds(5);

    @Comment("Ustaw konfiguracje menu otchlani:")
    public BukkitMenuBuilder otchlanMenu = new BukkitMenuBuilder(
            "&6&lOtchlan &7Strona: &a&l{page}&7&l",
            6,
            false,
            new MapBuilder<Integer, ItemStack>()
                    .put(45, new ItemBuilder(XMaterial.RED_WOOL.parseItem())
                            .setName("&cPowrot do porzedniej strony.")
                            .setLore("&7Kliknij, aby zmienic strone.")
                            .toItemStack())
                    .put(46, new ItemBuilder(XMaterial.BLACK_STAINED_GLASS_PANE.parseItem())
                            .setName(" ")
                            .toItemStack())
                    .put(47, new ItemBuilder(XMaterial.BLACK_STAINED_GLASS_PANE.parseItem())
                            .setName(" ")
                            .toItemStack())
                    .put(48, new ItemBuilder(XMaterial.BLACK_STAINED_GLASS_PANE.parseItem())
                            .setName(" ")
                            .toItemStack())
                    .put(49, new ItemBuilder(XMaterial.BLACK_STAINED_GLASS_PANE.parseItem())
                            .setName(" ")
                            .toItemStack())
                    .put(50, new ItemBuilder(XMaterial.BLACK_STAINED_GLASS_PANE.parseItem())
                            .setName(" ")
                            .toItemStack())
                    .put(51, new ItemBuilder(XMaterial.BLACK_STAINED_GLASS_PANE.parseItem())
                            .setName(" ")
                            .toItemStack())
                    .put(52, new ItemBuilder(XMaterial.BLACK_STAINED_GLASS_PANE.parseItem())
                            .setName(" ")
                            .toItemStack())
                    .put(53, new ItemBuilder(XMaterial.GREEN_WOOL.parseItem())
                            .setName("&aPrzejdz do nastepnej strony.")
                            .setLore("&7Kliknij, aby zmienic strone.")
                            .toItemStack())
                    .build()
    );

    @Comment("Pod ktorym slotem ma sie znajdowac przycisk od poprzedniej strony?")
    public int previousPageSlot = 45;

    @Comment("Pod ktorym slotem ma sie znajdowac przycisk od nastepnej strony?")
    public int nextPageSlot = 53;
}
