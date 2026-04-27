package com.jinrui.urbantransportationexpansion.screen;

import com.jinrui.urbantransportationexpansion.block.entity.TrafficLightBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ArrayPropertyDelegate;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.util.math.BlockPos;

public class TrafficLightScreenHandler extends ScreenHandler {
    private final PropertyDelegate propertyDelegate;
    private final BlockPos blockPos;

    public TrafficLightScreenHandler(int syncId, PlayerInventory inventory, PacketByteBuf buf) {
        this(syncId, inventory, buf.readBlockPos(), new ArrayPropertyDelegate(5));
    }

    public TrafficLightScreenHandler(int syncId, PlayerInventory inventory, TrafficLightBlockEntity blockEntity, PropertyDelegate propertyDelegate) {
        this(syncId, inventory, blockEntity.getBlockPosForScreen(), propertyDelegate);
    }

    private TrafficLightScreenHandler(int syncId, PlayerInventory inventory, BlockPos blockPos, PropertyDelegate propertyDelegate) {
        super(ModScreenHandlers.TRAFFIC_LIGHT, syncId);
        this.blockPos = blockPos;
        this.propertyDelegate = propertyDelegate;
        this.addProperties(propertyDelegate);
    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int slot) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return true;
    }

    public BlockPos getBlockPos() {
        return this.blockPos;
    }

    public int getPhaseIndex() {
        return this.propertyDelegate.get(0);
    }

    public int getRemainingTicks() {
        return this.propertyDelegate.get(1);
    }

    public int getGreenTicks() {
        return this.propertyDelegate.get(2);
    }

    public int getYellowTicks() {
        return this.propertyDelegate.get(3);
    }

    public int getRedTicks() {
        return this.propertyDelegate.get(4);
    }
}
