package com.github.huangjiang.tlmtech.client.renderer.item;

import com.github.huangjiang.tlmtech.config.TlmTechConfig;

/**
 * 原版替换的开关来源，对应 {@link VanillaConfig} 里的两个物品贴图选项
 * （史莱姆模型/经验球纹理是渲染器级别的，不走这里）。
 */
public enum SpriteSwitch {
    TOTEM,
    XP_BOTTLE;

    public boolean isEnabled() {
        return switch (this) {
            case TOTEM -> TlmTechConfig.REPLACE_TOTEM_TEXTURE.get();
            case XP_BOTTLE -> TlmTechConfig.REPLACE_XP_BOTTLE_TEXTURE.get();
        };
    }
}
