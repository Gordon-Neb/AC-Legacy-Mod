package dev.adventurecraft.awakening.tile;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.tile.TileEntityTile;
import net.minecraft.world.level.tile.entity.TileEntity;

public class AC_BlockBlueprintSolid extends AC_BlockBlueprintBase {

    public AC_BlockBlueprintSolid(int id) {
        // We give it a default texture (tex) for the inventory icon and as a fallback.
        super(id, Material.WOOD);
    }

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