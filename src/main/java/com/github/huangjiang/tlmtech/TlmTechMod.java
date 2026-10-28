package com.github.huangjiang.tlmtech;

import net.fabricmc.api.ModInitializer;
import net.minecraft.resources.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 车万女仆：技术性改进 —— Touhou Little Maid 的附属模组。
 * <p>
 * 把移植过程中做的、不适合放在主模组里的技术性改进集中到一处：
 * ① 语音识别（STT）的 WebSocket / OpenAI Realtime 站点兼容；
 * ② 自制手册书壳（不依赖 Patchouli 的 memorizable_gensokyo）；
 * ③ 四项可开关的原版纹理/模型替换（史莱姆模型、经验球、图腾、经验瓶）。
 */
public final class TlmTechMod implements ModInitializer {
    public static final String MOD_ID = "tlm_tech";
    public static final Logger LOGGER = LoggerFactory.getLogger("TlmTech");

    public static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(MOD_ID, path);
    }

    @Override
    public void onInitialize() {
        LOGGER.info("[TLM Tech] 车万女仆：技术性改进 附属模组已加载");
    }
}
