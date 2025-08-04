package dev.adventurecraft.awakening.tile;

import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.tile.Tile;
import net.minecraft.world.level.tile.TileEntityTile;
import net.minecraft.world.level.tile.entity.TileEntity;

// This is the new, simple base class for ALL blueprint blocks.
// This is the new, simple base class for ALL blueprint blocks.
public abstract class AC_BlockBlueprintBase extends TileEntityTile implements IBlueprintBlock {

    protected AC_BlockBlueprintBase(int id, Material material) {
        super(id, 0, material);
        Tile.isEntityTile[this.id] = true;
    }

    @Override
    protected TileEntity newTileEntity() {
        return new AC_TileBlueprint();
    }
}
