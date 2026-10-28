package com.github.huangjiang.tlmtech.client;

import com.github.huangjiang.tlmtech.client.book.SelfBookOpen;
import com.github.tartaricacid.touhoulittlemaid.api.event.client.OpenPatchouliBookEvent;
import net.fabricmc.api.ClientModInitializer;

/**
 * 客户端入口。
 * <p>
 * 主模组女仆界面侧栏那个手册按钮现在统一发 {@link OpenPatchouliBookEvent}
 * （书壳已从主模组剥离），这里接住它，打开本模组的自包含书壳。
 */
public final class TlmTechClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        OpenPatchouliBookEvent.CALLBACK.register(event -> SelfBookOpen.openDefault());
    }
}
