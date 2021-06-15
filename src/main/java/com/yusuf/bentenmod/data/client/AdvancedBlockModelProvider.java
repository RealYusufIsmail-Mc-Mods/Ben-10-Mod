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
import net.minecraft.item.Item;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.fml.RegistryObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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

import static com.yusuf.bentenmod.core.init.BlockInit.*;

public class AdvancedBlockModelProvider extends BlockStateProvider {


    public AdvancedBlockModelProvider(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen, Main.MOD_ID, exFileHelper);
        this.generator = gen;
    }



    private final DataGenerator generator;
    private static final Logger LOGGER = LogManager.getLogger();
    private static final Gson GSON = (new GsonBuilder()).setPrettyPrinting().disableHtmlEscaping().create();      private final DataGenerator generator;

    public void run(DirectoryCache p_200398_1_) {
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
        Set<Item> set = Sets.newHashSet();
        BiConsumer<ResourceLocation, Supplier<JsonElement>> biconsumer = (p_240086_1_, p_240086_2_) -> {
            Supplier<JsonElement> supplier = map1.put(p_240086_1_, p_240086_2_);
            if (supplier != null) {
                throw new IllegalStateException("Duplicate model definition for " + p_240086_1_);
            }
        };
        Consumer<Item> consumer1 = set::add;
        (new BlockModelProvider(consumer, biconsumer, consumer1)).run();
        (new Instance(consumer, biconsumer, consumer1)).run();
        List<Block> list = Registry.BLOCK.stream().filter((p_240084_1_) -> !map.containsKey(p_240084_1_)).collect(Collectors.toList());
        if (!list.isEmpty()) {
            //throw new IllegalStateException("Missing blockstate definitions for: " + list);
        } else {
            Registry.BLOCK.forEach((p_240087_2_) -> {
                Item item = Item.BY_BLOCK.get(p_240087_2_);
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
            this.saveCollection(p_200398_1_, path, map, this::createBlockStatePath);
            this.saveCollection(p_200398_1_, path, map1, this::createModelPath);
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

    private static final class Instance extends BlockModelProvider {
        protected final Consumer<IFinishedBlockState> blockStateOutput;
        public final BiConsumer<ResourceLocation, Supplier<JsonElement>> modelOutput;

        public Instance(Consumer<IFinishedBlockState> p_i232514_1_, BiConsumer<ResourceLocation, Supplier<JsonElement>> p_i232514_2_, Consumer<Item> p_i232514_3_) {
            super(p_i232514_1_, p_i232514_2_, p_i232514_3_);
            blockStateOutput = p_i232514_1_;
            modelOutput = p_i232514_2_;
        }

        private void createFurnace(Block p_239977_1_, TexturedModel.ISupplier p_239977_2_) {
            ResourceLocation resourcelocation = p_239977_2_.create(p_239977_1_, this.modelOutput);
            ResourceLocation resourcelocation1 = ModelTextures.getBlockTexture(p_239977_1_, "_front_on");
            ResourceLocation resourcelocation2 = p_239977_2_.get(p_239977_1_).updateTextures((p_239963_1_) -> {
                p_239963_1_.put(StockTextureAliases.FRONT, resourcelocation1);
            }).createWithSuffix(p_239977_1_, "_on", this.modelOutput);
            this.blockStateOutput.accept(FinishedVariantBlockState.multiVariant(p_239977_1_).with(createBooleanModelDispatch(BlockStateProperties.LIT, resourcelocation2, resourcelocation)).with(createHorizontalFacingDispatch()));
        }

        private BlockStateVariantBuilder createBooleanModelDispatch(BooleanProperty p_239894_0_, ResourceLocation p_239894_1_, ResourceLocation p_239894_2_) {
            return BlockStateVariantBuilder.property(p_239894_0_).select(true, BlockModelDefinition.variant().with(BlockModelFields.MODEL, p_239894_1_)).select(false, BlockModelDefinition.variant().with(BlockModelFields.MODEL, p_239894_2_));
        }

        private BlockStateVariantBuilder createHorizontalFacingDispatch() {
            return BlockStateVariantBuilder.property(BlockStateProperties.HORIZONTAL_FACING).select(Direction.EAST, BlockModelDefinition.variant().with(BlockModelFields.Y_ROT, BlockModelFields.Rotation.R90)).select(Direction.SOUTH, BlockModelDefinition.variant().with(BlockModelFields.Y_ROT, BlockModelFields.Rotation.R180)).select(Direction.WEST, BlockModelDefinition.variant().with(BlockModelFields.Y_ROT, BlockModelFields.Rotation.R270)).select(Direction.NORTH, BlockModelDefinition.variant());
        }

        @SafeVarargs
        private final void cube(@Nonnull RegistryObject<GeneralBlock>... registries) {
            for (RegistryObject<GeneralBlock> registry : registries)
                createTrivialCube(registry.get());
        }

        private void createTrivialCube(Block p_239975_1_) {
            this.createTrivialBlock(p_239975_1_, TexturedModel.CUBE);
        }

        private void createTrivialBlock(Block p_239956_1_, TexturedModel.ISupplier p_239956_2_) {
            this.blockStateOutput.accept(createSimpleBlock(p_239956_1_, p_239956_2_.create(p_239956_1_, this.modelOutput)));
        }

        private FinishedVariantBlockState createSimpleBlock(Block p_239978_0_, ResourceLocation p_239978_1_) {
            return FinishedVariantBlockState.multiVariant(p_239978_0_, BlockModelDefinition.variant().with(BlockModelFields.MODEL, p_239978_1_));
        }

        //name is wood provider but it use for RotatedPilarBlock (s) in your BlockInit
        private LogsVariantHelper woodProvider(Block p_240009_1_) {
            return new LogsVariantHelper(ModelTextures.logColumn(p_240009_1_), blockStateOutput, modelOutput);
        }

        @Override
        public void run() {
            cube(LEGENDARY_BLOCK, OMNITRIX_BLOCK, BLACK_DIAMOND_BLOCK, INFINITUM_BLOCK, FIRE_BLOCK, SPEED_BLOCK, RUBY_BLOCK, IMPERIUM_BLOCK, LEGENDARY_ORE_BLOCK,
                    RUBY_ORE, IMPERIUM_ORE, BLACK_DIAMOND_ORE, FIRE_ORE, SPEED_ORE, OMNITRIX_ORE);
            woodProvider(INFINITUM_ORE.get());
            createFurnace(BlockInit.TABLE_BLOCK.get(), TexturedModel.ORIENTABLE_ONLY_TOP);
        }



        private static IFinishedBlockState createAxisAlignedPillarBlock(Block p_239986_0_, ResourceLocation p_239986_1_) {
            return FinishedVariantBlockState.multiVariant(p_239986_0_, BlockModelDefinition.variant().with(BlockModelFields.MODEL, p_239986_1_)).with(createRotatedPillar());
        }

        private static BlockStateVariantBuilder createRotatedPillar() {
            return BlockStateVariantBuilder.property(BlockStateProperties.AXIS).select(Direction.Axis.Y, BlockModelDefinition.variant()).select(Direction.Axis.Z, BlockModelDefinition.variant().with(BlockModelFields.X_ROT, BlockModelFields.Rotation.R90)).select(Direction.Axis.X, BlockModelDefinition.variant().with(BlockModelFields.X_ROT, BlockModelFields.Rotation.R90).with(BlockModelFields.Y_ROT, BlockModelFields.Rotation.R90));
        }
    }

    static class LogsVariantHelper {
        protected final Consumer<IFinishedBlockState> blockStateOutput;
        public final BiConsumer<ResourceLocation, Supplier<JsonElement>> modelOutput;
        private final ModelTextures logMapping;
        public LogsVariantHelper(ModelTextures modelTextures,Consumer<IFinishedBlockState> blockStateOutput, BiConsumer<ResourceLocation, Supplier<JsonElement>> modelOutput) {
            logMapping = modelTextures;
            this.blockStateOutput = blockStateOutput;
            this.modelOutput = modelOutput;
        }

        public LogsVariantHelper wood(Block p_240070_1_) {
            ModelTextures modeltextures = this.logMapping.copyAndUpdate(StockTextureAliases.END, this.logMapping.get(StockTextureAliases.SIDE));
            ResourceLocation resourcelocation = StockModelShapes.CUBE_COLUMN.create(p_240070_1_, modeltextures, modelOutput);
            blockStateOutput.accept(Instance.createAxisAlignedPillarBlock(p_240070_1_, resourcelocation));
            return this;
        }

        public LogsVariantHelper log(Block p_240071_1_) {
            ResourceLocation resourcelocation = StockModelShapes.CUBE_COLUMN.create(p_240071_1_, this.logMapping,modelOutput);
            blockStateOutput.accept(Instance.createAxisAlignedPillarBlock(p_240071_1_, resourcelocation));
            return this;
        }
    }
}