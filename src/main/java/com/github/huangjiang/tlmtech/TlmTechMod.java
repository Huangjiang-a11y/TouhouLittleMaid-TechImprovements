package com.github.huangjiang.tlmtech;

import com.github.huangjiang.tlmtech.config.TlmTechConfig;
import com.github.huangjiang.tlmtech.init.TlmTechTriggers;
import com.github.huangjiang.tlmtech.item.TlmTechItems;
import fuzs.forgeconfigapiport.fabric.api.v5.ConfigRegistry;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.neoforged.fml.config.ModConfig;
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

    /** 主模组里那个创造模式标签页（InitCreativeTabs 注册的 "main"） */
    public static final ResourceKey<CreativeModeTab> TLM_MAIN_TAB = ResourceKey.create(
            Registries.CREATIVE_MODE_TAB, Identifier.fromNamespaceAndPath("touhou_little_maid", "main"));

    public static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(MOD_ID, path);
    }

    @Override
    public void onInitialize() {
        ConfigRegistry.INSTANCE.register(MOD_ID, ModConfig.Type.COMMON, TlmTechConfig.init());
        TlmTechItems.init();
        TlmTechTriggers.init();

        CreativeModeTabEvents.modifyOutputEvent(TLM_MAIN_TAB)
                .register(output -> output.accept(TlmTechItems.MEMORIZABLE_GENSOKYO_MANUAL));

        // 首次进服赠送手册：原主模组是 EnterServerEvent 触发 give_patchouli_book_config，
        // advancement 完成后按 loot_table 发书，这里照搬同一套，只是换成自己的触发器与战利品表。
        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) ->
                TlmTechTriggers.GIVE_BOOK.trigger(handler.getPlayer()));

        LOGGER.info("[TLM Tech] 车万女仆：技术性改进 附属模组已加载");
    }
}
