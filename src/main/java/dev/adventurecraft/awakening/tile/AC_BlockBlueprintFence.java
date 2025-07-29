package dev.adventurecraft.awakening.tile;

import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.tile.Tile;
import net.minecraft.world.level.tile.TileEntityTile;
import net.minecraft.world.level.tile.entity.TileEntity;

public class AC_BlockBlueprintFence extends TileEntityTile implements IBlueprintBlock {

    public AC_BlockBlueprintFence(int id) {
        super(id, 0, Material.WOOD);
        this.isEntityTile[this.id] = true;
    }

    @Override
    protected TileEntity newTileEntity() {
        return new AC_TileBlueprint();
    }

    // This is the render ID for the fence model.
    @Override
    public int getRenderShape() {
        return 11;
    }

    @Override
    public boolean isCubeShaped() {
        return false;
    }

    @Override
    public boolean isSolidRender() {
        return false;
    }

    // This logic allows blueprint fences to connect to vanilla fences and other blueprint fences.

    public boolean canConnectFenceTo(Level level, int x, int y, int z) {
        int blockId = level.getTile(x, y, z);
        if (blockId == this.id || blockId == Tile.OAK_FENCE.id) {
            return true;
        }
        Tile block = Tile.tiles[blockId];
        return block != null && block.material.isSolid() && block.isCubeShaped();
    }
}