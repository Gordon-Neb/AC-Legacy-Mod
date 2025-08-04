package dev.adventurecraft.awakening.tile;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.tile.entity.TileEntity;

public class AC_TileBlueprint extends TileEntity {

    // Store the full texture data (sheet + index).
    public long overriddenTexture = -1L;

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        // Use hasKey, which is correct for your version.
        if (tag.hasKey("textureOverride")) {
            this.overriddenTexture = tag.getLong("textureOverride");
        }
    }

    @Override
    public void save(CompoundTag tag) {
        super.save(tag);
        tag.putLong("textureOverride", this.overriddenTexture);
    }
}