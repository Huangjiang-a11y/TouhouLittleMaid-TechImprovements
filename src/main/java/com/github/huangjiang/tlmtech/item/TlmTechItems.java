package com.github.huangjiang.tlmtech.item;

import com.github.huangjiang.tlmtech.TlmTechMod;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;

import java.util.function.Function;

/**
 * 本模组的物品。手册物品自带书壳，右键即可阅读，不依赖 Patchouli。
 */
public final class TlmTechItems {
    public static final Item MEMORIZABLE_GENSOKYO_MANUAL =
            register("memorizable_gensokyo_manual", GuideBookItem::new);

    private TlmTechItems() {
    }

    /** 空方法，调用只为触发类加载让静态字段完成注册。 */
    public static void init() {
    }

    private static Item register(String id, Function<Identifier, Item> func) {
        Identifier loc = TlmTechMod.id(id);
        return Registry.register(BuiltInRegistries.ITEM, loc, func.apply(loc));
    }
}
