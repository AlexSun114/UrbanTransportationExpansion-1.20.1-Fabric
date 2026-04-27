package com.jinrui.urbantransportationexpansion.block.custom;

import com.jinrui.urbantransportationexpansion.block.entity.ModBlockEntities;
import com.jinrui.urbantransportationexpansion.block.entity.TrafficLightBlockEntity;
import com.jinrui.urbantransportationexpansion.item.ModItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TrafficLightBlock extends BlockWithEntity implements BlockEntityProvider {
    public static final EnumProperty<TrafficLightBlockEntity.Phase> PHASE = EnumProperty.of("phase", TrafficLightBlockEntity.Phase.class);

    public TrafficLightBlock(Settings settings) {
        super(settings.luminance(state -> {
            TrafficLightBlockEntity.Phase phase = state.get(PHASE);
            return phase == TrafficLightBlockEntity.Phase.GREEN ? 12 : phase == TrafficLightBlockEntity.Phase.YELLOW ? 10 : 8;
        }));
        this.setDefaultState(this.getStateManager().getDefaultState().with(PHASE, TrafficLightBlockEntity.Phase.GREEN));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(PHASE);
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new TrafficLightBlockEntity(pos, state);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        if (world instanceof ServerWorld && type == ModBlockEntities.TRAFFIC_LIGHT) {
            return (world1, pos, state1, blockEntity) -> TrafficLightBlockEntity.serverTick(world1, pos, state1, (TrafficLightBlockEntity) blockEntity);
        }
        return null;
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        ItemStack held = player.getStackInHand(hand);
        if (!held.isOf(ModItems.WRENCH)) {
            return ActionResult.PASS;
        }
        if (!world.isClient) {
            BlockEntity be = world.getBlockEntity(pos);
            if (be instanceof TrafficLightBlockEntity trafficLightBlockEntity) {
                player.openHandledScreen(trafficLightBlockEntity);
            }
        }
        return ActionResult.SUCCESS;
    }
}
