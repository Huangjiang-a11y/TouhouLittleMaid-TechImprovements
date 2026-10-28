package com.github.huangjiang.tlmtech.client.mixin;

import com.github.huangjiang.tlmtech.stt.RealtimeSttSite;
import com.github.huangjiang.tlmtech.stt.RealtimeSttSupport;
import com.github.tartaricacid.touhoulittlemaid.ai.service.stt.STTApiType;
import com.github.tartaricacid.touhoulittlemaid.client.input.STTChatKey;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/**
 * 让按住说话键优先走本模组的 WebSocket 站点。
 * <p>
 * 主模组里是 {@code AvailableSites.getSTTSite(AIConfig.STT_TYPE.get().getName())}——
 * 类型枚举的名字同时也是站点 id。枚举里没有我们的值，所以直接把取名字这一步重定向：
 * 只要 available_sites.yml 里配了启用的 {@code openai_realtime} 站点，就返回它。
 */
@Mixin(STTChatKey.class)
public class STTChatKeyMixin {
    @Redirect(method = {"sttStart", "sttStop"},
            at = @At(value = "INVOKE",
                    target = "Lcom/github/tartaricacid/touhoulittlemaid/ai/service/stt/STTApiType;getName()Ljava/lang/String;"))
    private static String tlmtech$preferRealtimeSite(STTApiType type) {
        if (RealtimeSttSupport.shouldUseRealtime()) {
            return RealtimeSttSite.API_TYPE;
        }
        return type.getName();
    }
}
