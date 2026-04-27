package com.jinrui.urbantransportationexpansion.block.entity;

import com.jinrui.urbantransportationexpansion.UrbanTransportationExpansion;
import com.jinrui.urbantransportationexpansion.block.ModBlocks;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModBlockEntities {
    public static final BlockEntityType<TrafficLightBlockEntity> TRAFFIC_LIGHT = Registry.register(
            Registries.BLOCK_ENTITY_TYPE,
            new Identifier(UrbanTransportationExpansion.MOD_ID, "traffic_light"),
            FabricBlockEntityTypeBuilder.create(TrafficLightBlockEntity::new, ModBlocks.TRAFFIC_LIGHT).build()
    );

    public static void register() {
    }
}
