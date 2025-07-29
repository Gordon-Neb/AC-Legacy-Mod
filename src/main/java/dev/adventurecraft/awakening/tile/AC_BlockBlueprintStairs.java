package dev.adventurecraft.awakening.tile;

import net.minecraft.util.Mth;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.tile.TileEntityTile;
import net.minecraft.world.level.tile.entity.TileEntity;
import net.minecraft.world.phys.AABB;

import java.util.ArrayList;

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

    @Override
    public void addAABBs(Level world, int x, int y, int z, AABB aabb, ArrayList list) {
        int meta = world.getData(x, y, z);
        if (meta == 0) {
            list.add(AABB.create(x, y, z, x + 0.5, y + 0.5, z + 1.0));
            list.add(AABB.create(x + 0.5, y, z, x + 1.0, y + 1.0, z + 1.0));
        } else if (meta == 1) {
            list.add(AABB.create(x, y, z, x + 0.5, y + 1.0, z + 1.0));
            list.add(AABB.create(x + 0.5, y, z, x + 1.0, y + 0.5, z + 1.0));
        } else if (meta == 2) {
            list.add(AABB.create(x, y, z, x + 1.0, y + 0.5, z + 0.5));
            list.add(AABB.create(x, y, z + 0.5, x + 1.0, y + 1.0, z + 1.0));
        } else if (meta == 3) {
            list.add(AABB.create(x, y, z, x + 1.0, y + 1.0, z + 0.5));
            list.add(AABB.create(x, y, z + 0.5, x + 1.0, y + 0.5, z + 1.0));
        }
    }

    @Override
    public void setPlacedBy(Level world, int x, int y, int z, Mob placer) {
        int meta = world.getData(x, y, z);
        int direction = Mth.floor((double) (placer.yRot * 4.0F / 360.0F) + 0.5D) & 3;
        int offset = switch (direction) {
            case 0 -> 2;
            case 1 -> 1;
            case 2 -> 3;
            default -> 0;
        };
        // The "& ~3" is important to preserve other metadata flags if you add them later.
        world.setData(x, y, z, (meta & ~3) | offset);
    }
}