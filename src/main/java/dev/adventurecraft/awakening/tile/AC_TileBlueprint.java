package dev.adventurecraft.awakening.tile;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.tile.entity.TileEntity;

public class AC_TileBlueprint extends TileEntity {
    public int overriddenTexture = -1;

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        overriddenTexture = tag.getInt("textureOverride");
    }

    @Override
    public void save(CompoundTag tag) {
        super.save(tag);
        tag.putInt("textureOverride", overriddenTexture);
    }
}