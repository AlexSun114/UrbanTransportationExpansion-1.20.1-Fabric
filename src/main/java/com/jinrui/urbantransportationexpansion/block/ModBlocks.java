package com.jinrui.urbantransportationexpansion.block;

import com.jinrui.urbantransportationexpansion.UrbanTransportationExpansion;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModBlocks {

    public static final Block STEEL_BLOCK = register("steel_block", new Block(AbstractBlock.Settings.create().requiresTool().strength(1.5f,3.0f)));
    public static final Block STEEL_ORE = register("steel_ore", new Block(AbstractBlock.Settings.create().requiresTool().strength(1.5f,3.0f)));
    public static final Block JADE_BLOCK = register("jade_block", new Block(AbstractBlock.Settings.create().requiresTool().strength(1.5f,3.0f)));


    public static Block register(String id, Block block) {
        registerBlockItems(id, block);
        return Registry.register(Registries.BLOCK, new Identifier(UrbanTransportationExpansion.MOD_ID, id),block);
    }

    public static void registerBlockItems(String id, Block block) {
        Registry.register(Registries.ITEM, new Identifier(UrbanTransportationExpansion.MOD_ID, id),
                new BlockItem(block, new Item.Settings()));
    }

    public static void registerModBlocks (){

    }
}
