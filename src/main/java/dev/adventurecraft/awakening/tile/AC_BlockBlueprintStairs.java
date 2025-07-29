package dev.adventurecraft.awakening.tile;

import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.tile.TileEntityTile;
import net.minecraft.world.level.tile.entity.TileEntity;

public class AC_BlockBlueprintStairs extends TileEntityTile implements IBlueprintBlock {

    public AC_BlockBlueprintStairs(int id) {
        super(id, 0, Material.WOOD); // Placeholder texture and material
        this.isEntityTile[this.id] = true;
    }

    @Override
    protected TileEntity newTileEntity() {
        return new AC_TileBlueprint();
    }

    // This is the render ID for the stairs model.
    @Override
    public int getRenderShape() {
        return 10;
    }

    // Stairs are not full, solid cubes.
    @Override
    public boolean isCubeShaped() {
        return false;
    }

    @Override
    public boolean isSolidRender() {
        return false;
    }
}