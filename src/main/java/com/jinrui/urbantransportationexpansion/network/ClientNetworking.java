package com.jinrui.urbantransportationexpansion.network;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.math.BlockPos;

public class ClientNetworking {
    public static void sendTrafficLightUpdate(BlockPos pos, int greenSeconds, int yellowSeconds, int redSeconds) {
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeBlockPos(pos);
        buf.writeInt(greenSeconds);
        buf.writeInt(yellowSeconds);
        buf.writeInt(redSeconds);
        ClientPlayNetworking.send(ModNetworking.TRAFFIC_LIGHT_UPDATE, buf);
    }
}
