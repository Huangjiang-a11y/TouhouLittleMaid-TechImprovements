package com.github.huangjiang.tlmtech.client.mixin;

import com.github.huangjiang.tlmtech.client.TlmTechBedrockModels;
import com.github.tartaricacid.touhoulittlemaid.client.init.ClientReloadListenerRegistry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * 赶在主模组创建基岩模型管理器之前，把油库里模型的注册塞进它的 ENTITY_MODELS 表。
 * <p>
 * 那个 create() 会把表拷成不可变并冻结，之后再注册就没用了；而两个模组的客户端入口
 * 谁先谁后没有保证，所以挂在这里最稳。
 */
@Mixin(ClientReloadListenerRegistry.class)
public class ClientReloadListenerRegistryMixin {
    @Inject(method = "onRegisterClientReloadListeners", at = @At("HEAD"))
    private static void tlmtech$registerBedrockModels(CallbackInfo ci) {
        TlmTechBedrockModels.register();
    }
}
