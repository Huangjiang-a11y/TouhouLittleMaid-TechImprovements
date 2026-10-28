package com.github.huangjiang.tlmtech.stt;

import com.github.tartaricacid.touhoulittlemaid.ai.manager.site.AvailableSites;
import com.github.tartaricacid.touhoulittlemaid.ai.service.stt.STTSite;

/**
 * 运行时判断：要不要把语音识别切到本模组提供的 WebSocket 站点。
 * <p>
 * 规则很简单——玩家在 available_sites.yml 里配了 {@code openai_realtime} 站点、
 * 并且在站点列表里点亮了它，就用它；否则维持主模组原来的选择。
 */
public final class RealtimeSttSupport {
    private RealtimeSttSupport() {
    }

    public static boolean shouldUseRealtime() {
        STTSite site = AvailableSites.getSTTSite(RealtimeSttSite.API_TYPE);
        return site != null && site.enabled();
    }
}
