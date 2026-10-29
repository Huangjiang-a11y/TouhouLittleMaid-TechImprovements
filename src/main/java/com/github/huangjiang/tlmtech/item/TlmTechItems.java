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
    /**
     * 手册物品破例沿用主模组的命名空间。
     * <p>
     * 这个 id 本来就是主模组的。物品 id 是直接写进存档 NBT 的，拆出去改成 tlm_tech
     * 会让玩家已有的手册因为 id 对不上而变成空气；其余东西（配置、书 id、渲染器、数据包内容）
     * 都不进存档，没有这个问题，所以只有这里用回 touhou_little_maid。
     * <p>
     * 前提是主模组那边已经不再注册同名物品（这次拆分里一并删掉了）。
     */
    private static final String MANUAL_NAMESPACE = "touhou_little_maid";

    public static final Item MEMORIZABLE_GENSOKYO_MANUAL =
            register(MANUAL_NAMESPACE, "memorizable_gensokyo_manual", GuideBookItem::new);

    private TlmTechItems() {
    }

    /** 空方法，调用只为触发类加载让静态字段完成注册。 */
    public static void init() {
    }

    private static Item register(String namespace, String path, Function<Identifier, Item> func) {
        Identifier loc = Identifier.fromNamespaceAndPath(namespace, path);
        return Registry.register(BuiltInRegistries.ITEM, loc, func.apply(loc));
    }
}
