package dev.adventurecraft.awakening.tile;

import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.tile.Tile;
import net.minecraft.world.level.tile.TileEntityTile;
import net.minecraft.world.level.tile.entity.TileEntity;

public class AC_BlockBlueprintSlope extends TileEntityTile implements IBlueprintBlock {

    public AC_BlockBlueprintSlope(int id) {
        super(id, 0, Material.WOOD);
        // FIX: Accessing static field correctly.
        Tile.isEntityTile[this.id] = true;
    }

    // FIX: Valid override.
    @Override
    protected TileEntity newTileEntity() {
        return new AC_TileBlueprint();
    }

    // Copying the essential behavior from your AC_BlockSlope.
    @Override
    public int getRenderShape() {
        return AC_BlockShapes.BLOCK_SLOPE;
    }

    // --- Properties for correct lighting and behavior ---
    @Override
    public boolean isCubeShaped() {
        return false;
    }

    @Override
    public boolean isSolidRender() {
        return false;
    }
}