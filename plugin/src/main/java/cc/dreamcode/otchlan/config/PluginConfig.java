package cc.dreamcode.otchlan.config;

import cc.dreamcode.otchlan.content.config.OtchlanConfig;
import cc.dreamcode.otchlan.content.config.OtchlanMenuConfig;
import cc.dreamcode.otchlan.stereotypes.Configuration;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import eu.okaeri.configs.annotation.Header;
import eu.okaeri.configs.annotation.NameModifier;
import eu.okaeri.configs.annotation.NameStrategy;
import eu.okaeri.configs.annotation.Names;

@Configuration(
        child = "config.yml"
)
@Header("## Dream-Otchlan (Main-Config) ##")
@Names(strategy = NameStrategy.HYPHEN_CASE, modifier = NameModifier.TO_LOWER_CASE)
public class PluginConfig extends OkaeriConfig {

    @Comment("Uzupelnij ponizsze menu danymi.")
    public OtchlanConfig otchlanConfig = new OtchlanConfig();
    public OtchlanMenuConfig otchlanMenuConfig = new OtchlanMenuConfig();

}
