package com.github.huangjiang.tlmtech;

import com.github.tartaricacid.touhoulittlemaid.ai.service.SerializerRegister;
import com.github.tartaricacid.touhoulittlemaid.ai.service.ServiceType;
import com.github.tartaricacid.touhoulittlemaid.api.ILittleMaid;
import com.github.huangjiang.tlmtech.stt.RealtimeSttSite;

/**
 * 主模组的附属扩展入口（fabric.mod.json 里 entrypoint 名为 {@code little_maid_extension}）。
 * <p>
 * 在这里把本模组实现的站点解析器注册进主模组：主模组读 available_sites.yml 时，
 * 遇到 {@code api_type: openai_realtime} 就会用我们的 codec 解析成 {@link RealtimeSttSite}。
 */
public final class TlmTechExtension implements ILittleMaid {
    @Override
    public void registerAIChatSerializer(SerializerRegister register) {
        register.register(ServiceType.STT, RealtimeSttSite.API_TYPE, new RealtimeSttSite.Serializer());
        TlmTechMod.LOGGER.info("[TLM Tech] 已注册 WebSocket STT 站点解析器：{}", RealtimeSttSite.API_TYPE);
    }
}
