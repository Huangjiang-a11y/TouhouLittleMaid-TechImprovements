package com.github.huangjiang.tlmtech.client.stt;

import com.github.tartaricacid.touhoulittlemaid.ai.service.stt.STTSite;
import com.github.huangjiang.tlmtech.stt.RealtimeSttSite;
import net.minecraft.network.chat.Component;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

import static com.github.tartaricacid.touhoulittlemaid.client.gui.entity.maid.ai.FormField.*;
import static com.github.tartaricacid.touhoulittlemaid.client.gui.entity.maid.ai.Translations.SECRET_KEY_IS_EMPTY;
import static com.github.tartaricacid.touhoulittlemaid.client.gui.entity.maid.ai.Translations.URL_IS_EMPTY;

public class RealtimeSttFormLayout extends STTSiteFormLayout {
    public RealtimeSttFormLayout(STTSite sourceSite) {
        super(sourceSite);
    }

    @Override
    public List<FieldDescriptor> getFieldDescriptors() {
        RealtimeSttSite site = (RealtimeSttSite) this.sourceSite;
        return List.of(
                new FieldDescriptor(URL, site.url(), true, false),
                new FieldDescriptor(SECRET_KEY, site.getSecretKey(), true, true),
                new FieldDescriptor(MODELS, site.getModel(), true, false)
        );
    }

    @Override
    public @Nullable STTSite buildSite(Function<String, String> fieldValues, Consumer<Component> showStatus) {
        RealtimeSttSite site = (RealtimeSttSite) this.sourceSite;
        String url = StringUtils.trimToEmpty(fieldValues.apply(URL));
        if (StringUtils.isBlank(url)) {
            showStatus.accept(URL_IS_EMPTY);
            return null;
        }
        if (StringUtils.isBlank(fieldValues.apply(SECRET_KEY))) {
            showStatus.accept(SECRET_KEY_IS_EMPTY);
            return null;
        }
        return new RealtimeSttSite(site.id(), site.icon(), site.enabled(), url,
                StringUtils.trimToEmpty(fieldValues.apply(SECRET_KEY)),
                StringUtils.trimToEmpty(fieldValues.apply(MODELS)));
    }
}
