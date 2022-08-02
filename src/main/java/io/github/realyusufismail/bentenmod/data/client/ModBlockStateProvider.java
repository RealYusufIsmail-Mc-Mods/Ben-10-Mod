/*
 * BSD 3-Clause License
 *
 * Copyright (c) 2021, Yusuf Ismail
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are permitted
 * provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this list of conditions
 * and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice, this list of
 * conditions and the following disclaimer in the documentation and/or other materials provided with
 * the distribution.
 *
 * 3. Neither the name of the copyright holder nor the names of its contributors may be used to
 * endorse or promote products derived from this software without specific prior written permission.
 *
 *
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR
 * IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY
 * WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package io.github.realyusufismail.bentenmod.data.client;

import io.github.realyusufismail.bentenmod.BenTenMod;
import io.github.realyusufismail.bentenmod.core.init.BlockInit;
import net.minecraft.data.DataGenerator;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.client.model.generators.BlockModelBuilder;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nonnull;


public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen, BenTenMod.MOD_ID, exFileHelper);

    }

    @Nonnull
    @Override
    public String getName() {
        return "Ben 10 Mod - Block States/Models";
    }

    @Override
    protected void registerStatesAndModels() {
        normalBlock(BlockInit.BLACK_DIAMOND_BLOCK.get());
        normalBlock(BlockInit.BLACK_DIAMOND_ORE.get());
        normalBlock(BlockInit.INFINITUM_BLOCK.get());
        normalBlock(BlockInit.FIRE_BLOCK.get());
        normalBlock(BlockInit.FIRE_ORE.get());
        normalBlock(BlockInit.LEGENDARY_BLOCK.get());
        normalBlock(BlockInit.LEGENDARY_ORE.get());
        normalBlock(BlockInit.OMNITRIX_BLOCK.get());
        normalBlock(BlockInit.OMNITRIX_ORE.get());
        normalBlock(BlockInit.RUBY_BLOCK.get());
        normalBlock(BlockInit.RUBY_ORE.get());
        normalBlock(BlockInit.SPEED_BLOCK.get());
        normalBlock(BlockInit.SPEED_ORE.get());
        normalBlock(BlockInit.IMPERIUM_BLOCK.get());
        normalBlock(BlockInit.IMPERIUM_ORE.get());
        normalBlock(BlockInit.DEEPSLATE_BLACK_DIAMOND_ORE.get());
        normalBlock(BlockInit.DEEPSLATE_SPEED_ORE.get());
        normalBlock(BlockInit.DEEPSLATE_RUBY_ORE.get());
        normalBlock(BlockInit.DEEPSLATE_IMPERIUM_ORE.get());
        normalBlock(BlockInit.DEEPSLATE_OMNITRIX_ORE.get());
        normalBlock(BlockInit.DEEPSLATE_LEGENDARY_ORE.get());
        omnitrixBlock();
        infinitumOre();
    }

    @Override
    public void horizontalBlock(Block block, ModelFile model) {
        super.horizontalBlock(block, model);
        this.simpleBlockItem(block, model);
    }

    public void omnitrixBlock() {
        ResourceLocation name = ForgeRegistries.BLOCKS.getKey(BlockInit.OmnitrixCrafter.get());

        if (name == null) {
            BenTenMod.LOGGER.error("Could not find block key for Omnitrix Crafter");
            return;
        }

        BlockModelBuilder builder = this.models().withExistingParent(name.getPath(), "block/cube");

        builder.texture("down", modLoc("block/"
                + ForgeRegistries.BLOCKS.getKey(BlockInit.OMNITRIX_BLOCK.get()).getPath()));
        builder.texture("east", modLoc("block/" + name.getPath() + "_side"));
        builder.texture("north", modLoc("block/" + name.getPath() + "_front"));
        builder.texture("particle", modLoc("block/" + name.getPath() + "_front"));
        builder.texture("south", modLoc("block/" + name.getPath() + "_side"));
        builder.texture("up", modLoc("block/" + name.getPath() + "_top"));
        builder.texture("west", modLoc("block/" + name.getPath() + "_front"));

        this.simpleBlockItem(BlockInit.OmnitrixCrafter.get(), builder);
        this.simpleBlock(BlockInit.OmnitrixCrafter.get());
    }

    public void infinitumOre() {
        ResourceLocation name = ForgeRegistries.BLOCKS.getKey(BlockInit.INFINITUM_ORE.get());

        if (name == null) {
            BenTenMod.LOGGER.error("Could not find block key for Infinitum Ore");
            return;
        }

        BlockModelBuilder builder =
                this.models().withExistingParent(name.getPath(), "block/cube_column");

        builder.texture("end", modLoc("block/" + name.getPath() + "_top"));
        builder.texture("side", modLoc("block/" + name.getPath() + "_side"));

        this.simpleBlockItem(BlockInit.INFINITUM_ORE.get(), builder);
        this.simpleBlock(BlockInit.INFINITUM_ORE.get());
    }

    public void normalBlock(Block block) {
        ResourceLocation name = ForgeRegistries.BLOCKS.getKey(block);

        if (name == null) {
            BenTenMod.LOGGER.error("Could not find block key for " + block.getName());
            return;
        }

        BlockModelBuilder builder =
                this.models().withExistingParent(name.getPath(), "block/cube_all");
        builder.texture("all", modLoc("block/" + name.getPath()));
        this.simpleBlockItem(block, builder);
        this.simpleBlock(block);
    }
}


