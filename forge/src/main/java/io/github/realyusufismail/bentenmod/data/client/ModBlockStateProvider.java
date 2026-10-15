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
package io.github.realyusufismail.bentenmod.data.client;

import io.github.realyusufismail.bentenmod.BenTenMod;
import io.github.realyusufismail.bentenmod.core.blocks.PrisonVaultBlock;
import io.github.realyusufismail.bentenmod.core.init.BlockInit;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.core.Direction;
import net.minecraftforge.client.model.generators.BlockModelBuilder;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nonnull;


public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen.getPackOutput(), BenTenMod.MOD_ID, exFileHelper);

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
        normalBlock(BlockInit.OMNITRIX_POD.get());
        normalBlock(BlockInit.DEEPSLATE_BLACK_DIAMOND_ORE.get());
        normalBlock(BlockInit.DEEPSLATE_SPEED_ORE.get());
        normalBlock(BlockInit.DEEPSLATE_RUBY_ORE.get());
        normalBlock(BlockInit.DEEPSLATE_IMPERIUM_ORE.get());
        normalBlock(BlockInit.DEEPSLATE_OMNITRIX_ORE.get());
        normalBlock(BlockInit.DEEPSLATE_LEGENDARY_ORE.get());
        normalBlock(BlockInit.NULL_VOID_STONE.get());
        normalBlock(BlockInit.NULL_VOID_STONE_BRICKS.get());
        normalBlock(BlockInit.NULL_CRYSTAL_ORE.get());
        normalBlock(BlockInit.TAYDENITE_ORE.get());
        normalBlock(BlockInit.TAYDENITE_BLOCK.get());
        normalBlock(BlockInit.PRISON_WALL.get());
        normalBlock(BlockInit.PLUMBER_HULL.get());
        prisonVault();
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

        BlockModelBuilder builder = this.models()
            .withExistingParent(name.getPath(), modLoc("block/omnitrix_crafter_device"));

        this.simpleBlockItem(BlockInit.OmnitrixCrafter.get(), builder);
        this.simpleBlock(BlockInit.OmnitrixCrafter.get(), builder);
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
        this.simpleBlock(BlockInit.INFINITUM_ORE.get(), builder);
    }

    private void prisonVault() {
        BlockModelBuilder locked = this.models()
            .withExistingParent("prison_vault", mcLoc("block/orientable"))
            .texture("front", modLoc("block/prison_vault_front"))
            .texture("side", modLoc("block/prison_vault_side"))
            .texture("top", modLoc("block/prison_vault_top"));
        BlockModelBuilder opened = this.models()
            .withExistingParent("prison_vault_open", mcLoc("block/orientable"))
            .texture("front", mcLoc("block/iron_block"))
            .texture("side", modLoc("block/prison_vault_side"))
            .texture("top", modLoc("block/prison_vault_top"));

        this.getVariantBuilder(BlockInit.PRISON_VAULT.get()).forAllStates(state -> {
            Direction facing = state.getValue(PrisonVaultBlock.FACING);
            BlockModelBuilder model = state.getValue(PrisonVaultBlock.OPEN) ? opened : locked;
            return ConfiguredModel.builder()
                .modelFile(model)
                .rotationY((int) facing.toYRot())
                .build();
        });
        this.simpleBlockItem(BlockInit.PRISON_VAULT.get(), locked);
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
        this.simpleBlock(block, builder);
    }
}


