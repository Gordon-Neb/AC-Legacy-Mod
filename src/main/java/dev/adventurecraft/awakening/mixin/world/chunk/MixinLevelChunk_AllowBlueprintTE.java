package dev.adventurecraft.awakening.mixin.world.chunk;

import dev.adventurecraft.awakening.tile.AC_Blocks;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.tile.Tile;
import net.minecraft.world.level.tile.entity.TileEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;




@Mixin(LevelChunk.class)
public class MixinLevelChunk_AllowBlueprintTE {

    @Inject(method = "getTileEntity", at = @At("HEAD"))
    private void allowBlueprintTE(int x, int y, int z, CallbackInfoReturnable<TileEntity> cir) {
        // We need to cast "this" to LevelChunk to access getTile
        LevelChunk chunk = (LevelChunk) (Object) this;
        int id = chunk.getTile(x, y, z);

        if (id == AC_Blocks.blueprintBlock.id) {
            Tile.isEntityTile[id] = true;
        }
    }
}
