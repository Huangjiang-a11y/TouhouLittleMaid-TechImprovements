package com.github.huangjiang.tlmtech.book;

import com.github.huangjiang.tlmtech.init.TlmTechTriggers;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.advancements.triggers.Criterion;
import net.minecraft.advancements.triggers.SimpleCriterionTrigger;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

import java.util.Optional;

/**
 * 首次进服赠送手册的判据触发器。
 * <p>
 * 原实现在主模组（GivePatchouliBookConfigTrigger + MiscConfig.GIVE_PATCHOULI_BOOK），
 * 随书壳一起搬到本模组。玩家进服时由 {@code ServerPlayConnectionEvents.JOIN} 触发，
 * 对应的 advancement 完成后按 loot_table 发一本手册。
 */
public class GiveBookTrigger extends SimpleCriterionTrigger<GiveBookTrigger.Instance> {
    public static Criterion<Instance> create() {
        return TlmTechTriggers.GIVE_BOOK.createCriterion(new Instance(Optional.empty()));
    }

    public void trigger(ServerPlayer serverPlayer) {
        super.trigger(serverPlayer, _ -> true);
    }

    @Override
    public Codec<Instance> codec() {
        return Instance.CODEC;
    }

    public record Instance(Optional<Holder<LootItemCondition>> player) implements SimpleInstance {
        public static final Codec<Instance> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                        LootItemCondition.CODEC.optionalFieldOf("player").forGetter(Instance::player))
                .apply(instance, Instance::new));
    }
}
