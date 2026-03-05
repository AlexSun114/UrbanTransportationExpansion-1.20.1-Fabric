package com.jinrui.urbantransportationexpansion.item;

import com.jinrui.urbantransportationexpansion.UrbanTransportationExpansion;
import com.jinrui.urbantransportationexpansion.block.ModBlocks;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModItemGroups {
    public static final RegistryKey<ItemGroup> UTE_MOD = register("ute_mod");

    private static RegistryKey<ItemGroup> register(String id) {
        return RegistryKey.of(RegistryKeys.ITEM_GROUP, new Identifier(UrbanTransportationExpansion.MOD_ID, id));
    }

    public static void registerGroups() {
        Registry.register(
                Registries.ITEM_GROUP,
                UTE_MOD,
                ItemGroup.create(ItemGroup.Row.TOP, 7)
                        .displayName(Text.translatable("itemGroup.ute_mod"))
                        .icon(() -> new ItemStack(ModItems.WRENCH))
                        .entries((displayContext, entries) -> {
                            entries.add(ModItems.WRENCH);
                            entries.add(ModItems.WRENCH_STICK);

                        }).build());
    }

    public static final ItemGroup UTE_MATERIAL = Registry.register(
            Registries.ITEM_GROUP,
            new Identifier(UrbanTransportationExpansion.MOD_ID, "ute_material"),
            ItemGroup.create(null, -1)
                    .displayName(Text.translatable("itemGroup.ute_material"))
                    .icon(() -> new ItemStack(ModItems.STEEL_INGOT))
                    .entries((displayContext, entries) -> {
                        entries.add(ModItems.STEEL_INGOT);
                        entries.add(ModItems.RAW_STEEL);
                        entries.add(ModBlocks.STEEL_ORE);
                        entries.add(ModBlocks.STEEL_BLOCK);
                        entries.add(ModBlocks.JADE_BLOCK);


                    }).build());
}
