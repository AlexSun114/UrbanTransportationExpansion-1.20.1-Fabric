package com.jinrui.urbantransportationexpansion.network;

import com.jinrui.urbantransportationexpansion.UrbanTransportationExpansion;
import com.jinrui.urbantransportationexpansion.block.entity.TrafficLightBlockEntity;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

public class ModNetworking {
    public static final Identifier TRAFFIC_LIGHT_UPDATE = new Identifier(UrbanTransportationExpansion.MOD_ID, "traffic_light_update");

    public static void registerServer() {
        ServerPlayNetworking.registerGlobalReceiver(TRAFFIC_LIGHT_UPDATE, (server, player, handler, buf, responseSender) -> {
            BlockPos pos = buf.readBlockPos();
            int greenSeconds = buf.readInt();
            int yellowSeconds = buf.readInt();
            int redSeconds = buf.readInt();

            server.execute(() -> {
                if (!player.getWorld().isChunkLoaded(pos)) {
                    return;
                }
                if (player.getWorld().getBlockEntity(pos) instanceof TrafficLightBlockEntity blockEntity) {
                    blockEntity.updateDurationsSeconds(greenSeconds, yellowSeconds, redSeconds);
                }
            });
        });
    }
}
