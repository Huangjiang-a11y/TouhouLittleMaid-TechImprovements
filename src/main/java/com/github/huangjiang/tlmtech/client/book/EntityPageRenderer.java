package com.github.huangjiang.tlmtech.client.book;

import com.github.tartaricacid.touhoulittlemaid.client.renderer.EntityPreviewRenderer;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.renderer.entity.state.EntityRenderState;

/**
 * 书页里的实体插图渲染。
 * <p>
 * 底层的实体构建与居中绘制走主模组公开的 {@link EntityPreviewRenderer}（JEI 的祭坛配方
 * 预览也在用它），这里只负责书页语义：读 entity / scale / offset 三个字段并推进排版 y。
 */
public final class EntityPageRenderer {
    private static final float DEFAULT_SCALE = 45.0F;

    private EntityPageRenderer() {
    }

    /** 画实体，返回内容区新的 y（不画文字）。 */
    public static int render(GuiGraphicsExtractor graphics, BookPage page, int x, int y, int width) {
        EntityRenderState state = EntityPreviewRenderer.state(page.str("entity"));
        if (state == null) {
            return y;
        }
        float scale = DEFAULT_SCALE * page.decimal("scale", 1.0F);
        float offset = page.decimal("offset", 0.0F);
        int entityH = Math.max(32, Math.round(state.boundingBoxHeight * scale) + 8);
        int cx = x + width / 2;
        int centerY = y + entityH / 2;
        int half = Math.max(8, Math.round(scale / 2.0F));
        EntityPreviewRenderer.drawCentered(graphics, state, scale, cx - half, centerY - half, cx + half, centerY + half, offset);
        return y + entityH + 6;
    }
}
