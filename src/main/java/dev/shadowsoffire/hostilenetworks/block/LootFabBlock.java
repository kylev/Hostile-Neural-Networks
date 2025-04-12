package dev.shadowsoffire.hostilenetworks.block;

import dev.shadowsoffire.hostilenetworks.gui.LootFabContainer;
import dev.shadowsoffire.hostilenetworks.tile.LootFabTileEntity;
import dev.shadowsoffire.placebo.block_entity.TickingEntityBlock;
import dev.shadowsoffire.placebo.menu.MenuUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class LootFabBlock extends BaseMachineBlock implements TickingEntityBlock {

    public LootFabBlock() {
        super();
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new LootFabTileEntity(pPos, pState);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        return MenuUtil.openGui(player, pos, LootFabContainer::new);
    }

    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving) {
        if (!pState.is(pNewState.getBlock())) {
            BlockEntity te = pLevel.getBlockEntity(pPos);
            if (te instanceof LootFabTileEntity fab) {
                Containers.dropContents(pLevel, pPos, fab.getInventory().getItems());
            }
            super.onRemove(pState, pLevel, pPos, pNewState, pIsMoving);
        }
    }
}
