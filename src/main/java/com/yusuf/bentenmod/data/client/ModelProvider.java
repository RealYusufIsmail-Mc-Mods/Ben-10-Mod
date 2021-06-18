package com.yusuf.bentenmod.data.client;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.yusuf.bentenmod.Main;
import com.yusuf.bentenmod.core.init.BlockInit;
import com.yusuf.bentenmod.core.init.GeneralBlock;
import net.minecraft.block.Block;
import net.minecraft.data.*;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.fml.RegistryObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import net.minecraftforge.client.model.generators.BlockStateProvider;



import javax.annotation.Nonnull;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * @see net.minecraft.data.BlockModelProvider
 */
import static com.yusuf.bentenmod.core.init.BlockInit.*;
import static net.minecraft.util.Util.name;

public class ModelProvider {

    public static final class Item extends net.minecraftforge.client.model.generators.ItemModelProvider {
        public Item(DataGenerator generator, ExistingFileHelper existingFileHelper) {
            super(generator, Main.MOD_ID, existingFileHelper);
        }

        @Override
        protected void registerModels() {
            //block("black_diamond_block");
            //block("black_diamond_ore");
             //block("fire_block");
             //block("fire_ore");
             //block("legendary_block");
             //block("omnitrix_block");
             //block("speed_block");
             //block("ruby_block");
             //block("ruby_ore");
             //block("infinitum_block");
             //block("speed_ore");
             //block("omnitrix_ore");
             //block("imperium_ore");
             //block("legendary_ore_block");
             //block("imperium_block");
             //block("deepslate_legendary_ore_block");
             //block("deepslate_ruby_ore");
             //block("deepslate_black_diamond_ore");
             //block("deepslate_imperium_ore");
             //block("deepslate_speed_ore");
             //block("deepslate_omnitrix_ore");


            ModelFile itemGenerated = getExistingFile(mcLoc("item/generated"));
            ModelFile itemhandHeld = getExistingFile(mcLoc("item/handheld"));

            //items
            build(itemGenerated, "black_diamond");
            build(itemGenerated, "black_diamond_scrap");
            build(itemGenerated, "infinitum");
            build(itemGenerated, "infinitum_scrap");
            build(itemGenerated, "boot");
            build(itemGenerated, "chestplate");
            build(itemGenerated, "legendary_ore");
            build(itemGenerated, "omnitrix");
            build(itemGenerated, "fire");
            build(itemGenerated, "ruby");
            build(itemGenerated, "speed");
            build(itemGenerated, "helmet");
            build(itemGenerated, "leggings");
            build(itemGenerated, "jacket");
            build(itemGenerated, "heatblast_helmet");
            build(itemGenerated, "heatblast_chestplate");
            build(itemGenerated, "heatblast_leggings");
            build(itemGenerated, "heatblast_boot");
            build(itemGenerated, "xlr8_helmet");
            build(itemGenerated, "xlr8_chestplate");
            build(itemGenerated, "xlr8_leggings");
            build(itemGenerated, "xlr8_boot");
            build(itemGenerated, "omnitrix_apple");
            build(itemGenerated, "enchanted_omnitrix_apple");
            build(itemGenerated, "fire_apple");
            build(itemGenerated, "ruby_apple");
            build(itemGenerated, "vilgax_spawn_egg");
            build(itemGenerated, "black_diamond_helmet");
            build(itemGenerated, "black_diamond_chestplate");
            build(itemGenerated, "black_diamond_leggings");
            build(itemGenerated, "black_diamond_boots");
            build(itemGenerated, "infinitum_helmet");
            build(itemGenerated, "infinitum_chestplate");
            build(itemGenerated, "infinitum_leggings");
            build(itemGenerated, "infinitum_boots");
            build(itemGenerated, "fourarms_helmet");
            build(itemGenerated, "fourarms_chestplate");
            build(itemGenerated, "fourarms_leggings");
            build(itemGenerated, "fourarms_boot");
            build(itemGenerated, "imperium");
            build(itemGenerated, "raw_legendary");
            build(itemGenerated, "raw_omnitrix");
            build(itemGenerated, "raw_imperium");


            //tools
            tool(itemhandHeld, "axe");
            tool(itemhandHeld, "sword");
            tool(itemhandHeld, "heatblast_sword");
            tool(itemhandHeld, "black_diamond_sword");
            tool(itemhandHeld, "black_diamond_pickaxe");
            tool(itemhandHeld, "black_diamond_shovel");
            tool(itemhandHeld, "black_diamond_axe");
            tool(itemhandHeld, "black_diamond_hoe");
            tool(itemhandHeld, "infinitum_sword");
            tool(itemhandHeld, "infinitum_axe");
            tool(itemhandHeld, "infinitum_pickaxe");
            tool(itemhandHeld, "infinitum_shovel");
            tool(itemhandHeld, "infinitum_hoe");
            tool(itemhandHeld, "imperium_sword");
            tool(itemhandHeld, "imperium_pickaxe");
            tool(itemhandHeld, "imperium_pickaxe_upgraded");
            tool(itemhandHeld, "imperium_axe");

        }

        private void build(ModelFile itemGenerated, String path) {
            getBuilder(path).parent(itemGenerated).texture("layer0", "item/" + path);
        }


        private ItemModelBuilder tool(ModelFile itemhandHeld, String name) {
            return getBuilder(name).parent(itemhandHeld).texture("layer0", "item/" + name);
        }

        private void block(String name) {
            withExistingParent(name, modLoc("block/" + name));
        }

        /**
         * @see BlockStateProvider#simpleBlock(Block, net.minecraftforge.client.model.generators.ConfiguredModel...)
         * @see BlockStateProvider#getVariantBuilder(Block)
         * @see VariantBlockStateBuilder#partialState()
         * @see VariantBlockStateBuilder#setModels(VariantBlockStateBuilder.PartialBlockstate, ConfiguredModel...)
         * @see Blocks#FURNACE
         *
         */
    }


    public static final class BlockState extends BlockStateProvider {
        private static final Logger LOGGER = LogManager.getLogger();
        private static final Gson GSON = (new GsonBuilder()).setPrettyPrinting().disableHtmlEscaping().create();
        private final DataGenerator generator;
        public BlockState(DataGenerator generator, ExistingFileHelper existingFileHelper) {
            super(generator, Main.MOD_ID, existingFileHelper);
            this.generator = generator;
        }

        @SuppressWarnings("deprecation")
        @Override
        public void run(DirectoryCache cache) {
            Path path = this.generator.getOutputFolder();
            Map<Block, IFinishedBlockState> map = Maps.newHashMap();
            Consumer<IFinishedBlockState> consumer = (p_240085_1_) -> {
                Block block = p_240085_1_.getBlock();
                IFinishedBlockState ifinishedblockstate = map.put(block, p_240085_1_);
                if (ifinishedblockstate != null) {
                    throw new IllegalStateException("Duplicate blockstate definition for " + block);
                }
            };
            Map<ResourceLocation, Supplier<JsonElement>> map1 = Maps.newHashMap();
            Set<net.minecraft.item.Item> set = Sets.newHashSet();
            BiConsumer<ResourceLocation, Supplier<JsonElement>> biconsumer = (p_240086_1_, p_240086_2_) -> {
                Supplier<JsonElement> supplier = map1.put(p_240086_1_, p_240086_2_);
                if (supplier != null) {
                    throw new IllegalStateException("Duplicate model definition for " + p_240086_1_);
                }
            };
            Consumer<net.minecraft.item.Item> consumer1 = set::add;
            (new net.minecraft.data.BlockModelProvider(consumer, biconsumer, consumer1)).run();
            (new BlockModels(consumer, biconsumer, consumer1)).run();
            List<Block> list = Registry.BLOCK.stream()
                    .filter((p_240084_1_) -> !map.containsKey(p_240084_1_)).collect(Collectors.toList());
            if (!list.isEmpty()) {
                throw new IllegalStateException("Missing blockstate definitions for: " + list);
            } else {
                Registry.BLOCK.forEach((p_240087_2_) -> {
                    net.minecraft.item.Item item = net.minecraft.item.Item.BY_BLOCK.get(p_240087_2_);
                    if (item != null) {
                        if (set.contains(item)) {
                            return;
                        }

                        ResourceLocation resourcelocation = ModelsResourceUtil.getModelLocation(item);
                        if (!map1.containsKey(resourcelocation)) {
                            map1.put(resourcelocation, new BlockModelWriter(ModelsResourceUtil.getModelLocation(p_240087_2_)));
                        }
                    }

                });
                saveCollection(cache, path, map, this::createBlockStatePath);
                saveCollection(cache, path, map1, this::createModelPath);
            }
        }

        private <T> void saveCollection(DirectoryCache p_240081_1_, Path p_240081_2_, Map<T, ? extends Supplier<JsonElement>> p_240081_3_, BiFunction<Path, T, Path> p_240081_4_) {
            p_240081_3_.forEach((p_240088_3_, p_240088_4_) -> {
                Path path = p_240081_4_.apply(p_240081_2_, p_240088_3_);

                try {
                    IDataProvider.save(GSON, p_240081_1_, p_240088_4_.get(), path);
                } catch (Exception exception) {
                    LOGGER.error("Couldn't save {}", path, exception);
                }

            });
        }



        private Path createBlockStatePath(Path p_240082_0_, Block p_240082_1_) {
            ResourceLocation resourcelocation = Registry.BLOCK.getKey(p_240082_1_);
            return p_240082_0_.resolve("assets/" + resourcelocation.getNamespace() + "/blockstates/" + resourcelocation.getPath() + ".json");
        }

        private Path createModelPath(Path p_240083_0_, ResourceLocation p_240083_1_) {
            return p_240083_0_.resolve("assets/" + p_240083_1_.getNamespace() + "/models/" + p_240083_1_.getPath() + ".json");
        }

        @Override
        protected void registerStatesAndModels() {}


    }


    private static final class BlockModels extends BlockModelProvider {
        private final Consumer<IFinishedBlockState> blockStateOutput;
        private final BiConsumer<ResourceLocation, Supplier<JsonElement>> modelOutput;
        public BlockModels(Consumer<IFinishedBlockState> p_i232514_1_, BiConsumer<ResourceLocation, Supplier<JsonElement>> p_i232514_2_, Consumer<net.minecraft.item.Item> p_i232514_3_) {
            super(p_i232514_1_, p_i232514_2_, p_i232514_3_);

            blockStateOutput = p_i232514_1_;
            modelOutput = p_i232514_2_;
        }
        private static BlockStateVariantBuilder createBooleanModelDispatch(BooleanProperty lit, ResourceLocation p_239894_1_, ResourceLocation p_239894_2_) {
            return BlockStateVariantBuilder.property(BlockStateProperties.LIT).select(true, BlockModelDefinition.variant().with(BlockModelFields.MODEL, p_239894_1_)).select(false, BlockModelDefinition.variant().with(BlockModelFields.MODEL, p_239894_2_));
        }

        private static BlockStateVariantBuilder createHorizontalFacingDispatch() {
            return BlockStateVariantBuilder.property(BlockStateProperties.HORIZONTAL_FACING).select(Direction.EAST, BlockModelDefinition.variant().with(BlockModelFields.Y_ROT, BlockModelFields.Rotation.R90)).select(Direction.SOUTH, BlockModelDefinition.variant().with(BlockModelFields.Y_ROT, BlockModelFields.Rotation.R180)).select(Direction.WEST, BlockModelDefinition.variant().with(BlockModelFields.Y_ROT, BlockModelFields.Rotation.R270)).select(Direction.NORTH, BlockModelDefinition.variant());
        }

        private static FinishedVariantBlockState createSimpleBlock(Block p_239978_0_, ResourceLocation p_239978_1_) {
            return FinishedVariantBlockState.multiVariant(p_239978_0_, BlockModelDefinition.variant().with(BlockModelFields.MODEL, p_239978_1_));
        }


        /*
        private void createFurnace(Block p_239977_1_, TexturedModel.ISupplier p_239977_2_) {
            ResourceLocation resourcelocation = p_239977_2_.create(p_239977_1_, this.modelOutput);
            ResourceLocation resourcelocation1 = ModelTextures.getBlockTexture(p_239977_1_, "_front_on");
            ResourceLocation resourcelocation2 = p_239977_2_.get(p_239977_1_).updateTextures((p_239963_1_) -> {
                p_239963_1_.put(StockTextureAliases.FRONT, resourcelocation1);
            }).createWithSuffix(p_239977_1_, "_on", this.modelOutput);
            this.blockStateOutput.accept(FinishedVariantBlockState.multiVariant(p_239977_1_).with(createBooleanModelDispatch(BlockStateProperties.LIT, resourcelocation2, resourcelocation)).with(createHorizontalFacingDispatch()));
        }


         */




        private void createFurnace(Block p_239977_1_, TexturedModel.ISupplier p_239977_2_) {
            ResourceLocation resourcelocation = p_239977_2_.create(p_239977_1_, this.modelOutput);
            ResourceLocation resourcelocation1 = p_239977_2_.get(p_239977_1_).updateTextures((p_239963_1_) -> {
            }).createWithSuffix(p_239977_1_, "_on", this.modelOutput);
            this.blockStateOutput.accept(FinishedVariantBlockState.multiVariant(p_239977_1_).with(createBooleanModelDispatch(BlockStateProperties.LIT, resourcelocation1, resourcelocation)).with(createHorizontalFacingDispatch()));
        }

        //
/*
        private void createInfinitum(Block p_239977_1_, TexturedModel.ISupplier p_239977_2_) {
            ResourceLocation resourcelocation = p_239977_2_.create(p_239977_1_, this.modelOutput);
            this.blockStateOutput.accept(FinishedVariantBlockState.multiVariant(p_239977_1_).with(createBooleanModelDispatch(BlockStateProperties.LIT, resourcelocation)).with(createHorizontalFacingDispatch()));
        }


 */
        private void createTrivialCube(Block p_239975_1_) {
            this.createTrivialBlock(p_239975_1_, TexturedModel.CUBE);
        }

        private void createTrivialBlock(Block p_239956_1_, TexturedModel.ISupplier p_239956_2_) {
            this.blockStateOutput.accept(createSimpleBlock(p_239956_1_, p_239956_2_.create(p_239956_1_, this.modelOutput)));
        }

        private void CreateSmithingModel(Block p_239956_1_) {
            this.blockStateOutput.accept(createSimpleBlock(p_239956_1_, TexturedModel.CUBE.create(p_239956_1_, this.modelOutput)));
        }




            @Override
            public void run() {
                createFurnace(TABLE_BLOCK.get(), TexturedModel.ORIENTABLE_ONLY_TOP);
               createTrivialBlock(INFINITUM_ORE.get(), TexturedModel.COLUMN);
                createTrivialCube(BLACK_DIAMOND_BLOCK.get());
                createTrivialCube(BLACK_DIAMOND_ORE.get());
                createTrivialCube(INFINITUM_BLOCK.get());
                createTrivialCube(FIRE_BLOCK.get());
                createTrivialCube(FIRE_ORE.get());
                createTrivialCube(LEGENDARY_BLOCK.get());
                createTrivialCube(LEGENDARY_ORE_BLOCK.get());
                createTrivialCube(OMNITRIX_BLOCK.get());
                createTrivialCube(OMNITRIX_ORE.get());
                createTrivialCube(RUBY_BLOCK.get());
                createTrivialCube(RUBY_ORE.get());
                createTrivialCube(SPEED_BLOCK.get());
                createTrivialCube(SPEED_ORE.get());
                createTrivialCube(IMPERIUM_ORE.get());
                createTrivialCube(IMPERIUM_BLOCK.get());
                createTrivialCube(DEEPSLATE_BLACK_DIAMOND_ORE.get());
                createTrivialCube(DEEPSLATE_SPEED_ORE.get());
                createTrivialCube(DEEPSLATE_RUBY_ORE.get());
                createTrivialCube(DEEPSLATE_IMPERIUM_ORE.get());
                createTrivialCube(DEEPSLATE_OMNITRIX_ORE.get());
                createTrivialCube(DEEPSLATE_LEGENDARY_ORE_BLOCK.get());

            }


    }
}