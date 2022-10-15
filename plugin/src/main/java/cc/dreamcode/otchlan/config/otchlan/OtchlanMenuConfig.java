package cc.dreamcode.otchlan.config.otchlan;

import cc.dreamcode.menu.serdes.bukkit.BukkitMenuBuilder;
import cc.dreamcode.otchlan.builder.ItemBuilder;
import com.cryptomorin.xseries.XMaterial;
import com.google.common.collect.ImmutableMap;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import eu.okaeri.configs.annotation.NameModifier;
import eu.okaeri.configs.annotation.NameStrategy;
import eu.okaeri.configs.annotation.Names;
import org.bukkit.inventory.ItemStack;

@Names(strategy = NameStrategy.HYPHEN_CASE, modifier = NameModifier.TO_LOWER_CASE)
public class OtchlanMenuConfig extends OkaeriConfig {

    @Comment("Ustaw konfiguracje menu otchlani:")
    public BukkitMenuBuilder otchlanMenu = new BukkitMenuBuilder(
            "&6&lOtchlan &7Strona: &a&l{page}&7&l",
            6,
            false,
            new ImmutableMap.Builder<Integer, ItemStack>()
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
