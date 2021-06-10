package com.yusuf.bentenmod.core.init;

import com.yusuf.bentenmod.Main;
import com.yusuf.bentenmod.block.OmnitrixOre;
import com.yusuf.bentenmod.core.bententable.TableBlock;
import com.yusuf.bentenmod.core.itemgroup.MainItemGroup;
import net.minecraft.block.*;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

import static com.yusuf.bentenmod.core.init.ItemInit.ITEMS;

public class BlockInit {
	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS,
			Main.MOD_ID);

	//blocks
	public static final RegistryObject<Block> LEGENDARY_BLOCK = BLOCKS
			.register("legendary_block",
			() -> new Block(AbstractBlock.Properties.copy(Blocks.NETHERITE_BLOCK)));

	public static final RegistryObject<Block> OMNITRIX_BLOCK = BLOCKS
			.register("omnitrix_block",
			() -> new Block(AbstractBlock.Properties.copy(Blocks.NETHERITE_BLOCK)));

	public static final RegistryObject<Block> BLACK_DIAMOND_BLOCK = BLOCKS
			.register("black_diamond_block",
					() -> new Block(AbstractBlock.Properties.copy(Blocks.NETHERITE_BLOCK)));

	public static final RegistryObject<Block> INFINITUM_BLOCK = BLOCKS
			.register("infinitum_block",
					() -> new Block(AbstractBlock.Properties.copy(Blocks.NETHERITE_BLOCK)));

	public static final RegistryObject<Block> FIRE_BLOCK = BLOCKS
			.register("fire_block",
			() -> new Block(AbstractBlock.Properties.copy(Blocks.NETHERITE_BLOCK)));

	public static final RegistryObject<Block> SPEED_BLOCK = BLOCKS
			.register("speed_block",
			() -> new Block(AbstractBlock.Properties.copy(Blocks.NETHERITE_BLOCK)));

	public static final RegistryObject<Block> RUBY_BLOCK = BLOCKS
			.register("ruby_block",
					() -> new Block(AbstractBlock.Properties.copy(Blocks.NETHERITE_BLOCK)));

	public static final RegistryObject<Block> IMPERIUM_BLOCK = BLOCKS
			.register("imperium_block",
					() -> new Block(RedstoneOreBlock.Properties.copy(Blocks.IRON_BLOCK)));
	/* ores */

	public static final RegistryObject<Block> LEGENDARY_ORE_BLOCK = BLOCKS
			.register("legendary_ore_block",
					() -> new Block(AbstractBlock.Properties.copy(Blocks.NETHERITE_BLOCK)));
	public static final RegistryObject<Block> RUBY_ORE = BLOCKS
			.register("ruby_ore",
			() -> new Block(AbstractBlock.Properties.copy(Blocks.ANCIENT_DEBRIS)));

	public static final RegistryObject<RotatedPillarBlock> INFINITUM_ORE = BLOCKS
			.register("infinitum_ore",
					() -> new RotatedPillarBlock(AbstractBlock.Properties.copy(Blocks.ANCIENT_DEBRIS)));

	public static final RegistryObject<Block> IMPERIUM_ORE = BLOCKS
			.register("imperium_ore",
					() -> new Block(AbstractBlock.Properties.copy(Blocks.DIAMOND_ORE)));


	public static final RegistryObject<Block> BLACK_DIAMOND_ORE = BLOCKS
			.register("black_diamond_ore",
					() -> new Block(AbstractBlock.Properties.copy(Blocks.ANCIENT_DEBRIS)));

	public static final RegistryObject<Block> FIRE_ORE = BLOCKS
			.register("fire_ore",
			() -> new Block(AbstractBlock.Properties.copy(Blocks.ANCIENT_DEBRIS)));

	public static final RegistryObject<Block> SPEED_ORE = BLOCKS
			.register("speed_ore",
			() -> new Block(AbstractBlock.Properties.copy(Blocks.ANCIENT_DEBRIS)));




	/* other blocks */





	public static final RegistryObject<OmnitrixOre> OMNITRIX_ORE = BLOCKS
			.register("omnitrix_ore",
					() -> new OmnitrixOre(OmnitrixOre.Properties.copy(Blocks.ANCIENT_DEBRIS)));



	public static final RegistryObject<TableBlock> TABLE_BLOCK;


	static {
		TABLE_BLOCK = BLOCKS.register("table", TableBlock::new);
		ITEMS.register("table", () -> new BlockItem(TABLE_BLOCK.get(), new Item.Properties().tab(MainItemGroup.MAIN)));
	}

	private static <T extends Block> RegistryObject<T> registerSpecial(String name, Supplier<T> supplier) {
		RegistryObject<T> blockReg = BLOCKS.register(name, supplier);
		ITEMS.register(name, () -> new BlockItem(blockReg.get(), new Item.Properties().tab(MainItemGroup.MAIN)));
		return blockReg;
	}





}
