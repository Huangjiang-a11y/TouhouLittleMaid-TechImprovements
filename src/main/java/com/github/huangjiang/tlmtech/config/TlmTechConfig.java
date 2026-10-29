package com.github.huangjiang.tlmtech.config;

import net.neoforged.neoforge.common.ModConfigSpec;

/**
 * 本模组的配置（config/tlm_tech-common.toml）。
 * <p>
 * 这四项原先在主模组的 VanillaConfig 里，随对应的替换实现一起搬来：
 * 史莱姆/岩浆怪模型、经验球纹理、不死图腾纹理、附魔之瓶纹理。
 */
public final class TlmTechConfig {
    private static final String TRANSLATE_KEY = "config.tlm_tech.vanilla";

    public static ModConfigSpec CONFIG;
    public static ModConfigSpec.BooleanValue REPLACE_SLIME_MODEL;
    public static ModConfigSpec.BooleanValue REPLACE_XP_TEXTURE;
    public static ModConfigSpec.BooleanValue REPLACE_TOTEM_TEXTURE;
    public static ModConfigSpec.BooleanValue REPLACE_XP_BOTTLE_TEXTURE;

    private TlmTechConfig() {
    }

    public static ModConfigSpec init() {
        ModConfigSpec.Builder builder = new ModConfigSpec.Builder();
        builder.translation(TRANSLATE_KEY).push("vanilla");

        builder.comment("Whether to replace the vanilla slime model with the yukkuri.")
                .translation(translateKey("replace_slime_model"));
        REPLACE_SLIME_MODEL = builder.define("ReplaceSlimeModel", true);

        builder.comment("Whether to replace the vanilla xp orb texture with the point items.")
                .translation(translateKey("replace_xp_texture"));
        REPLACE_XP_TEXTURE = builder.define("ReplaceXPTexture", true);

        builder.comment("Whether to replace the vanilla totem texture with the life point.")
                .translation(translateKey("replace_totem_texture"));
        REPLACE_TOTEM_TEXTURE = builder.define("ReplaceTotemTexture", true);

        builder.comment("Whether to replace the vanilla bottle of xp texture with the point items.")
                .translation(translateKey("replace_xp_bottle_texture"));
        REPLACE_XP_BOTTLE_TEXTURE = builder.define("ReplaceXPBottleTexture", true);

        builder.pop();
        CONFIG = builder.build();
        return CONFIG;
    }

    private static String translateKey(String key) {
        return TRANSLATE_KEY + "." + key;
    }
}
