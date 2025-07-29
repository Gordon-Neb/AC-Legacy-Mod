package dev.adventurecraft.awakening.tile;

import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelSource;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.tile.Tile;
import net.minecraft.world.level.tile.TileEntityTile;
import net.minecraft.world.level.tile.entity.TileEntity;
import net.minecraft.world.phys.AABB;

public class AC_BlockBlueprintHalfStep extends TileEntityTile implements IBlueprintBlock {

    public AC_BlockBlueprintHalfStep(int id) {
        super(id, 0, Material.WOOD);
        // FIX: Accessing the static field correctly via the class name 'Tile'.
        Tile.isEntityTile[this.id] = true;
    }

    // FIX: This is a valid override because we now extend TileEntityTile.
    @Override
    protected TileEntity newTileEntity() {
        return new AC_TileBlueprint();
    }

    // --- Logic copied from your AC_BlockHalfStep to ensure correct shape ---
    @Override
    public void updateShape(LevelSource world, int x, int y, int z) {
        updateBlockBounds(world, x, y, z);
    }

    @Override
    public AABB getAABB(Level world, int x, int y, int z) {
        updateBlockBounds(world, x, y, z);
        return super.getAABB(world, x, y, z);
    }

    private void updateBlockBounds(LevelSource world, int x, int y, int z) {
        int meta = world.getData(x, y, z);
        // This sets the shape to a top or bottom slab based on metadata.
        if (meta % 2 == 0) {
            this.setShape(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
        } else {
            this.setShape(0.0F, 0.5F, 0.0F, 1.0F, 1.0F, 1.0F);
        }
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