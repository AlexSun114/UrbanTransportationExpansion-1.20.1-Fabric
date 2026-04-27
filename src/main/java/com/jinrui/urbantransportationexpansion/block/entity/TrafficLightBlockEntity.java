package com.jinrui.urbantransportationexpansion.block.entity;

import com.jinrui.urbantransportationexpansion.block.custom.TrafficLightBlock;
import com.jinrui.urbantransportationexpansion.screen.TrafficLightScreenHandler;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ArrayPropertyDelegate;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class TrafficLightBlockEntity extends BlockEntity implements ExtendedScreenHandlerFactory {
    public enum Phase implements StringIdentifiable {
        GREEN("green"),
        YELLOW("yellow"),
        RED("red");

        private final String name;

        Phase(String name) {
            this.name = name;
        }

        @Override
        public String asString() {
            return this.name;
        }
    }

    private static final int DEFAULT_GREEN_TICKS = 30 * 20;
    private static final int DEFAULT_YELLOW_TICKS = 3 * 20;
    private static final int DEFAULT_RED_TICKS = 30 * 20;

    private Phase phase = Phase.GREEN;
    private int elapsedTicks = 0;
    private int greenTicks = DEFAULT_GREEN_TICKS;
    private int yellowTicks = DEFAULT_YELLOW_TICKS;
    private int redTicks = DEFAULT_RED_TICKS;

    private final PropertyDelegate propertyDelegate = new ArrayPropertyDelegate(5) {
        @Override
        public int get(int index) {
            return switch (index) {
                case 0 -> phase.ordinal();
                case 1 -> getRemainingTicks();
                case 2 -> greenTicks;
                case 3 -> yellowTicks;
                case 4 -> redTicks;
                default -> 0;
            };
        }

        @Override
        public void set(int index, int value) {
            switch (index) {
                case 2 -> greenTicks = clampTicks(value);
                case 3 -> yellowTicks = clampTicks(value);
                case 4 -> redTicks = clampTicks(value);
                default -> {
                }
            }
        }
    };

    public TrafficLightBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.TRAFFIC_LIGHT, pos, state);
    }

    public static void serverTick(World world, BlockPos pos, BlockState state, TrafficLightBlockEntity blockEntity) {
        blockEntity.elapsedTicks++;
        int duration = blockEntity.getPhaseDuration(blockEntity.phase);
        if (blockEntity.elapsedTicks >= duration) {
            blockEntity.elapsedTicks = 0;
            blockEntity.phase = switch (blockEntity.phase) {
                case GREEN -> Phase.YELLOW;
                case YELLOW -> Phase.RED;
                case RED -> Phase.GREEN;
            };
            world.setBlockState(pos, state.with(TrafficLightBlock.PHASE, blockEntity.phase), Block.NOTIFY_ALL);
            blockEntity.markDirty();
        }
    }

    public int getRemainingTicks() {
        return Math.max(0, getPhaseDuration(this.phase) - this.elapsedTicks);
    }

    public int getPhaseDuration(Phase phase) {
        return switch (phase) {
            case GREEN -> this.greenTicks;
            case YELLOW -> this.yellowTicks;
            case RED -> this.redTicks;
        };
    }

    private int clampTicks(int ticks) {
        return Math.max(20, Math.min(20 * 600, ticks));
    }

    public void updateDurationsSeconds(int greenSeconds, int yellowSeconds, int redSeconds) {
        this.greenTicks = clampTicks(greenSeconds * 20);
        this.yellowTicks = clampTicks(yellowSeconds * 20);
        this.redTicks = clampTicks(redSeconds * 20);
        this.elapsedTicks = 0;
        this.markDirty();
    }

    public BlockPos getBlockPosForScreen() {
        return this.pos;
    }

    public PropertyDelegate getPropertyDelegate() {
        return this.propertyDelegate;
    }

    @Override
    public Text getDisplayName() {
        return Text.translatable("block.urbantransportationexpansion.traffic_light");
    }

    @Override
    public void writeScreenOpeningData(ServerPlayerEntity player, PacketByteBuf buf) {
        buf.writeBlockPos(this.pos);
    }

    @Override
    public @Nullable ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new TrafficLightScreenHandler(syncId, playerInventory, this, this.propertyDelegate);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        this.phase = Phase.values()[Math.max(0, Math.min(2, nbt.getInt("phase")))];
        this.elapsedTicks = Math.max(0, nbt.getInt("elapsed_ticks"));
        this.greenTicks = clampTicks(nbt.contains("green_ticks") ? nbt.getInt("green_ticks") : DEFAULT_GREEN_TICKS);
        this.yellowTicks = clampTicks(nbt.contains("yellow_ticks") ? nbt.getInt("yellow_ticks") : DEFAULT_YELLOW_TICKS);
        this.redTicks = clampTicks(nbt.contains("red_ticks") ? nbt.getInt("red_ticks") : DEFAULT_RED_TICKS);
        if (this.world != null) {
            this.world.setBlockState(this.pos, this.getCachedState().with(TrafficLightBlock.PHASE, this.phase), Block.NOTIFY_LISTENERS);
        }
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        nbt.putInt("phase", this.phase.ordinal());
        nbt.putInt("elapsed_ticks", this.elapsedTicks);
        nbt.putInt("green_ticks", this.greenTicks);
        nbt.putInt("yellow_ticks", this.yellowTicks);
        nbt.putInt("red_ticks", this.redTicks);
    }
}
