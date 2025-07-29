package dev.adventurecraft.awakening.tile;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.tile.TileEntityTile;
import net.minecraft.world.level.tile.entity.TileEntity;

public class AC_BlockBlueprintSolid extends TileEntityTile implements IBlueprintBlock {

    public AC_BlockBlueprintSolid(int id, int tex) {
        // We give it a default texture (tex) for the inventory icon and as a fallback.
        super(id, tex, Material.WOOD);
    }

    @Override
    protected TileEntity newTileEntity() {
        return new AC_TileBlueprint();
    }

    // The getTexture override is now GONE. The Mixin handles it.
    // The default implementation from Tile will be used, which the Mixin intercepts.

    @Override
    public boolean use(Level level, int x, int y, int z, Player player) {
        return false;
    }

    @Override
    public boolean isSolidRender() {
        return true;
    }

    @Override
    public int getRenderShape() {
        return 0;
    }
}