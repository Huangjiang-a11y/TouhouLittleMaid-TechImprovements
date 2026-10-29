package com.github.huangjiang.tlmtech.client;

import com.github.huangjiang.tlmtech.TlmTechMod;
import com.github.tartaricacid.touhoulittlemaid.client.model.bedrock.SimpleBedrockEntityModel;
import com.github.tartaricacid.touhoulittlemaid.client.resource.bedrock.InternalBedrockModelRegistry;
import net.minecraft.resources.Identifier;

/**
 * 油库里史莱姆的基岩模型注册。
 * <p>
 * 主模组的 {@code InternalBedrockModelRegistry.addEntityModel} 把命名空间写死成它自己，
 * 这里直接往公开的 {@code ENTITY_MODELS} 表里放本模组的 id，模型文件就落在
 * {@code assets/tlm_tech/models/bedrock/entity/<名字>.json}。
 * <p>
 * 注册必须早于主模组 {@code InternalBedrockModelManager.create()}（它在客户端初始化里
 * 遍历这张表并冻结），所以由 {@link com.github.huangjiang.tlmtech.client.mixin.ClientReloadListenerRegistryMixin}
 * 在那个方法开头代劳。
 */
public final class TlmTechBedrockModels {
    public static final Identifier REIMU_YUKKURI = TlmTechMod.id("bedrock/entity/reimu_yukkuri");
    public static final Identifier MARISA_YUKKURI = TlmTechMod.id("bedrock/entity/marisa_yukkuri");

    private TlmTechBedrockModels() {
    }

    public static void register() {
        InternalBedrockModelRegistry.ENTITY_MODELS.put(REIMU_YUKKURI, SimpleBedrockEntityModel::new);
        InternalBedrockModelRegistry.ENTITY_MODELS.put(MARISA_YUKKURI, SimpleBedrockEntityModel::new);
    }
}
