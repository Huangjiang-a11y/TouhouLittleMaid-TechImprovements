package com.github.huangjiang.tlmtech.client;

import com.github.huangjiang.tlmtech.client.book.SelfBookOpen;
import com.github.huangjiang.tlmtech.client.renderer.entity.EntityMarisaYukkuriSlimeRender;
import com.github.huangjiang.tlmtech.client.renderer.entity.EntityYukkuriSlimeRender;
import com.github.huangjiang.tlmtech.client.renderer.entity.ReplaceExperienceOrbRenderer;
import com.github.huangjiang.tlmtech.client.renderer.item.ReplaceableSpriteProperty;
import com.github.tartaricacid.touhoulittlemaid.api.event.client.OpenPatchouliBookEvent;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.renderer.item.properties.conditional.ConditionalItemModelProperties;
import net.minecraft.world.entity.EntityTypes;

/**
 * 客户端入口。
 * <p>
 * 做三件事：接管主模组侧栏的手册按钮、注册四项原版替换里需要渲染器的那两项
 * （史莱姆/岩浆怪模型、经验球纹理），以及注册图腾/附魔之瓶用的条件物品模型属性。
 * 基岩模型的注册时机更早，见 {@code ClientReloadListenerRegistryMixin}。
 */
public final class TlmTechClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        // 主模组女仆界面侧栏那个手册按钮现在统一发 OpenPatchouliBookEvent（书壳已从主模组剥离），
        // 这里接住它，打开本模组的自包含书壳。
        OpenPatchouliBookEvent.CALLBACK.register(event -> SelfBookOpen.openDefault());

        // 原版替换（换渲染器的那两项，开关在 TlmTechConfig）
        EntityRendererRegistry.register(EntityTypes.SLIME, EntityYukkuriSlimeRender::new);
        EntityRendererRegistry.register(EntityTypes.MAGMA_CUBE, EntityMarisaYukkuriSlimeRender::new);
        EntityRendererRegistry.register(EntityTypes.EXPERIENCE_ORB, ReplaceExperienceOrbRenderer::new);

        // 原版替换（换贴图的那两项）：走原版 minecraft:condition 物品模型，
        // assets/minecraft/items/{totem_of_undying,experience_bottle}.json 引用这两个属性。
        ConditionalItemModelProperties.ID_MAPPER
                .put(ReplaceableSpriteProperty.REPLACE_TOTEM_TEXTURE, ReplaceableSpriteProperty.TOTEM_CODEC);
        ConditionalItemModelProperties.ID_MAPPER
                .put(ReplaceableSpriteProperty.REPLACE_XP_BOTTLE_TEXTURE, ReplaceableSpriteProperty.XP_BOTTLE_CODEC);
    }
}
