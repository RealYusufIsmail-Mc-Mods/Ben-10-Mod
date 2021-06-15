package com.yusuf.bentenmod.core.init;

import com.yusuf.bentenmod.Main;
import com.yusuf.bentenmod.core.machine.bententable.TableBlock;
import com.yusuf.bentenmod.core.itemgroup.MainItemGroup;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.RotatedPillarBlock;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

import static com.yusuf.bentenmod.core.init.ItemInit.ITEMS;

public class BlockInit {
	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Main.MOD_ID);

	public static final RegistryObject<GeneralBlock> LEGENDARY_BLOCK = register("legendary_block", Blocks.NETHERITE_BLOCK);
	public static final RegistryObject<GeneralBlock> OMNITRIX_BLOCK = register("omnitrix_block", Blocks.NETHERITE_BLOCK);
	public static final RegistryObject<GeneralBlock> BLACK_DIAMOND_BLOCK = register("black_diamond_block", Blocks.NETHERITE_BLOCK);
	public static final RegistryObject<GeneralBlock> INFINITUM_BLOCK = register("infinitum_block", Blocks.NETHERITE_BLOCK);
	public static final RegistryObject<GeneralBlock> FIRE_BLOCK = register("fire_block", Blocks.NETHERITE_BLOCK);
	public static final RegistryObject<GeneralBlock> SPEED_BLOCK = register("speed_block", Blocks.NETHERITE_BLOCK);
	public static final RegistryObject<GeneralBlock> RUBY_BLOCK = register("ruby_block", Blocks.NETHERITE_BLOCK);
	public static final RegistryObject<GeneralBlock> IMPERIUM_BLOCK = register("imperium_block", Blocks.IRON_BLOCK);
	public static final RegistryObject<GeneralBlock> LEGENDARY_ORE_BLOCK = register("legendary_ore_block", Blocks.NETHERITE_BLOCK);


	//deepslate ores
	public static final RegistryObject<GeneralBlock> DEEPSLATE_LEGENDARY_ORE_BLOCK = register("deepslate_legendary_ore_block", Blocks.ANCIENT_DEBRIS);
	public static final RegistryObject<GeneralBlock> DEEPSLATE_RUBY_ORE = register("deepslate_ruby_ore", Blocks.DIAMOND_ORE);
	public static final RegistryObject<GeneralBlock> DEEPSLATE_BLACK_DIAMOND_ORE = register("deepslate_black_diamond_ore", Blocks.ANCIENT_DEBRIS);
	public static final RegistryObject<GeneralBlock> DEEPSLATE_IMPERIUM_ORE = register("deepslate_imperium_ore", Blocks.IRON_ORE);
	public static final RegistryObject<GeneralBlock> DEEPSLATE_SPEED_ORE = register("deepslate_speed_ore", Blocks.ANCIENT_DEBRIS);
	public static final RegistryObject<GeneralBlock> DEEPSLATE_OMNITRIX_ORE = register("deepslate_omnitrix_ore", Blocks.DIAMOND_ORE);

	/* Black for ores */
	public static final RegistryObject<GeneralBlock> RUBY_ORE = register("ruby_ore", Blocks.ANCIENT_DEBRIS);
	public static final RegistryObject<GeneralBlock> IMPERIUM_ORE = register("imperium_ore", Blocks.DIAMOND_ORE);
	public static final RegistryObject<GeneralBlock> BLACK_DIAMOND_ORE = register("black_diamond_ore", Blocks.ANCIENT_DEBRIS);
	public static final RegistryObject<GeneralBlock> FIRE_ORE = register("fire_ore", Blocks.ANCIENT_DEBRIS);
	public static final RegistryObject<GeneralBlock> SPEED_ORE = register("speed_ore", Blocks.ANCIENT_DEBRIS);
	public static final RegistryObject<GeneralBlock> OMNITRIX_ORE = register("omnitrix_ore", Blocks.DIAMOND_ORE);
	public static final RegistryObject<RotatedPillarBlock> INFINITUM_ORE
			= registerSpecial("infinitum_ore", () -> new RotatedPillarBlock(AbstractBlock.Properties.copy(Blocks.ANCIENT_DEBRIS)));

	/* ore blocks */

	/* other blocks */

	public static final RegistryObject<TableBlock> TABLE_BLOCK;

	static {
		TABLE_BLOCK = BLOCKS.register("table", TableBlock::new);
	}

	//helper methods for register both block and BlockItem at the same time.
	//Instead of put them in main class
	private static <T extends Block> RegistryObject<T> registerSpecial(String name, Supplier<T> supplier) {
		RegistryObject<T> blockReg = BLOCKS.register(name, supplier);
		ITEMS.register(name, () -> new BlockItem(blockReg.get(), new Item.Properties().tab(MainItemGroup.MAIN)));
		return blockReg;
	}

	private static RegistryObject<GeneralBlock> register(String name, Supplier<GeneralBlock> supplier) {
		RegistryObject<GeneralBlock> blockReg = BLOCKS.register(name, supplier);
		ITEMS.register(name, () -> new BlockItem(blockReg.get(), new Item.Properties().tab(MainItemGroup.MAIN)));
		return blockReg;
	}


}
