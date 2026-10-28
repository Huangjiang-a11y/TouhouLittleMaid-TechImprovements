package com.github.huangjiang.tlmtech.stt;

import com.github.tartaricacid.touhoulittlemaid.ai.service.SerializableSite;
import com.github.tartaricacid.touhoulittlemaid.ai.service.stt.STTSite;
import com.github.huangjiang.tlmtech.client.stt.RealtimeSttFormLayout;
import com.github.tartaricacid.touhoulittlemaid.client.gui.entity.maid.ai.layout.STTSiteFormLayout;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.Identifier;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

/**
 * OpenAI Realtime 语音识别站点（WebSocket，wss://api.openai.com/v1/realtime?intent=transcription）。
 * <p>
 * 与 普通的 HTTP 一次性转录不同，这个走 Realtime 的 WebSocket：
 * 连接后先发 session.update（session.type=transcription），再把 PCM16 音频用
 * input_audio_buffer.append 送上去，最后 commit，等服务端回
 * conversation.item.input_audio_transcription.completed 拿文本。
 */
public class RealtimeSttSite implements STTSite {
    public static final String API_TYPE = "openai_realtime";
    /**
     * 复用 LLM 那边已有的 OpenAI 图标
     */
    public static final Identifier DEFAULT_ICON = Identifier.fromNamespaceAndPath("touhou_little_maid", "textures/gui/ai_chat/openai.png");

    private final String id;
    private final Identifier icon;

    private boolean enabled;
    private String url;
    private String secretKey;
    private String model;

    public RealtimeSttSite(String id, Identifier icon, boolean enabled, String url, String secretKey, String model) {
        this.id = id;
        this.icon = icon;
        this.enabled = enabled;
        this.url = url;
        this.secretKey = secretKey;
        this.model = model;
    }

    @Override
    public String id() {
        return this.id;
    }

    @Override
    public boolean enabled() {
        return this.enabled;
    }

    @Override
    public Identifier icon() {
        return this.icon;
    }

    @Override
    public String url() {
        return this.url;
    }

    @Override
    public Map<String, String> headers() {
        return Map.of();
    }

    @Override
    public String getApiType() {
        return API_TYPE;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public String getModel() {
        return model;
    }

    @Override
    public RealtimeSttClient client() {
        return new RealtimeSttClient(this);
    }

    @Override
    public STTSiteFormLayout formLayout() {
        return new RealtimeSttFormLayout(this);
    }

    @Override
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public static class Serializer implements SerializableSite<RealtimeSttSite> {
        public static final Codec<RealtimeSttSite> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                Codec.STRING.fieldOf(ID).forGetter(RealtimeSttSite::id),
                Identifier.CODEC.fieldOf(ICON).forGetter(RealtimeSttSite::icon),
                Codec.BOOL.fieldOf(ENABLED).forGetter(RealtimeSttSite::enabled),
                Codec.STRING.fieldOf(URL).forGetter(RealtimeSttSite::url),
                Codec.STRING.fieldOf(SECRET_KEY).forGetter(RealtimeSttSite::getSecretKey),
                Codec.STRING.fieldOf("model").forGetter(RealtimeSttSite::getModel)
        ).apply(instance, RealtimeSttSite::new));

        @Override
        public Codec<RealtimeSttSite> codec() {
            return CODEC;
        }

        @Override
        public RealtimeSttSite defaultSite() {
            return new RealtimeSttSite(
                    API_TYPE,
                    DEFAULT_ICON,
                    false,
                    "wss://api.openai.com/v1/realtime?intent=transcription",
                    StringUtils.EMPTY,
                    "gpt-4o-transcribe"
            );
        }
    }
}
