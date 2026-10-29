package com.github.huangjiang.tlmtech.init;

import com.github.huangjiang.tlmtech.TlmTechMod;
import com.github.huangjiang.tlmtech.book.GiveBookTrigger;
import net.minecraft.advancements.triggers.CriterionTrigger;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;

public final class TlmTechTriggers {
    public static final GiveBookTrigger GIVE_BOOK = register("give_book", new GiveBookTrigger());

    private TlmTechTriggers() {
    }

    /** 空方法，调用只为触发类加载让静态字段完成注册。 */
    public static void init() {
    }

    private static <T extends CriterionTrigger<?>> T register(String id, T trigger) {
        return Registry.register(BuiltInRegistries.TRIGGER_TYPES, TlmTechMod.id(id), trigger);
    }
}
