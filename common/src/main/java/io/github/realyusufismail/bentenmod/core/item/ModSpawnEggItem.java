/*
 * Copyright 2026 Yusuf Ismail
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.github.realyusufismail.bentenmod.core.item;

import com.google.common.base.Suppliers;
import net.minecraft.core.BlockSource;
import net.minecraft.core.Direction;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.level.block.DispenserBlock;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

/**
 * A spawn egg whose entity type is looked up lazily, because items can be created before entity
 * types are registered. Colours are registered by each loader's client entry point.
 */
public class ModSpawnEggItem extends SpawnEggItem {
    private static final List<ModSpawnEggItem> EGGS = new ArrayList<>();
    private final Supplier<? extends EntityType<? extends Mob>> entityType;

    public ModSpawnEggItem(Supplier<? extends EntityType<? extends Mob>> entityType,
            int primaryColour, int secondaryColour, Item.Properties properties) {
        super(null, primaryColour, secondaryColour, properties);
        this.entityType = Suppliers.memoize(entityType::get);
        EGGS.add(this);
    }

    public static List<ModSpawnEggItem> modEggs() {
        return Collections.unmodifiableList(EGGS);
    }

    /** Lets dispensers hatch the mod's eggs, like vanilla ones. */
    public static void initSpawnEggs() {
        DefaultDispenseItemBehavior dispenseBehaviour = new DefaultDispenseItemBehavior() {
            @Override
            protected ItemStack execute(BlockSource source, ItemStack stack) {
                Direction direction = source.getBlockState().getValue(DispenserBlock.FACING);
                EntityType<?> type = ((SpawnEggItem) stack.getItem()).getType(stack.getTag());
                type.spawn(source.getLevel(), stack, null, source.getPos().relative(direction),
                        MobSpawnType.DISPENSER, direction != Direction.UP, false);
                stack.shrink(1);
                return stack;
            }
        };
        for (ModSpawnEggItem egg : EGGS) {
            DispenserBlock.registerBehavior(egg, dispenseBehaviour);
        }
    }

    @Override
    public EntityType<?> getType(@Nullable CompoundTag tag) {
        EntityType<?> fromTag = super.getType(tag);
        return fromTag == null ? this.entityType.get() : fromTag;
    }

    @Override
    public FeatureFlagSet requiredFeatures() {
        return this.entityType.get().requiredFeatures();
    }
}
