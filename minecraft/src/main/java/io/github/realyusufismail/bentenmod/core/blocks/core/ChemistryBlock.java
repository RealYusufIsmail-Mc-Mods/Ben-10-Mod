/*
 * BSD 3-Clause License
 *
 * Copyright (c) 2021, Yusuf Ismail
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * 3. Neither the name of the copyright holder nor the names of its
 *    contributors may be used to endorse or promote products derived from
 *    this software without specific prior written permission.
 *
 *
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package io.github.realyusufismail.bentenmod.core.blocks.core;

import io.github.realyusufismail.bentenmod.core.init.BlockInit;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class ChemistryBlock extends Block {
    public final int radioactivity;

    public ChemistryBlock(@NotNull Builder builder) {
        super(builder.properties);
        this.radioactivity = builder.radioactivity;
        if (builder.blockItem.getLeft()) {
            BlockInit.BLOCK_ITEM_WHITELIST.put(this, builder.blockItem.getRight());
        }
    }

    public static class Builder {
        private final BlockBehaviour.Properties properties;
        private Pair<Boolean, Item.Properties> blockItem = Pair.of(false, null);
        private int radioactivity = 0;

        private Builder(BlockBehaviour.Properties properties) {
            this.properties = properties;
        }

        @Contract("_ -> new")
        public static @NotNull Builder build(BlockBehaviour.Properties properties) {
            return new Builder(properties);
        }

        public Builder blockItem(boolean shouldHave) {
            this.blockItem = Pair.of(shouldHave, this.blockItem.getValue());
            return this;
        }

        public Builder blockItem(boolean shouldHave, Item.Properties properties) {
            this.blockItem = Pair.of(shouldHave, properties);
            return this;
        }

        public Builder radioactivity(int radioactivity) {
            this.radioactivity = radioactivity;
            return this;
        }
    }
}
