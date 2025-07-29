package dev.adventurecraft.awakening.mixin.client.render;

import dev.adventurecraft.awakening.ACMod;
import dev.adventurecraft.awakening.tile.IBlueprintBlock;
import dev.adventurecraft.awakening.tile.AC_TileBlueprint;
import net.minecraft.client.renderer.TileRenderer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.tile.Tile;
import net.minecraft.world.level.tile.entity.TileEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(TileRenderer.class)
public abstract class MixinTileRenderer {

    // This gets a reference to the 'fixedTexture' field inside the TileRenderer.
    @Shadow
    private int fixedTexture;

    // We inject our code at the very beginning of the main rendering method.
    @Inject(
        method = "tesselateInWorld(Lnet/minecraft/world/level/tile/Tile;III)Z",
        at = @At("HEAD")
    )
    private void preRenderBlueprint(Tile tile, int x, int y, int z, CallbackInfoReturnable<Boolean> cir) {
        // We only care about our blueprint blocks.
        if (tile instanceof IBlueprintBlock) {
            // Use our static "escape hatch" to get the real client Level.
            Level clientLevel = ACMod.MC_INSTANCE.level;
            if (clientLevel != null) {
                TileEntity te = clientLevel.getTileEntity(x, y, z);
                if (te instanceof AC_TileBlueprint) {
                    int customTexture = ((AC_TileBlueprint) te).overriddenTexture;
                    if (customTexture >= 0) {
                        // Set the fixedTexture field. The game will now use this
                        // texture ID for all faces of the block that is about to be rendered.
                        this.fixedTexture = customTexture;
                    }
                }
            }
        }
    }

    // We inject our code at the very end of the main rendering method.
    @Inject(
        method = "tesselateInWorld(Lnet/minecraft/world/level/tile/Tile;III)Z",
        at = @At("TAIL")
    )
    private void postRenderBlueprint(Tile tile, int x, int y, int z, CallbackInfoReturnable<Boolean> cir) {
        // We MUST reset the fixedTexture after the block is done rendering,
        // otherwise every block rendered after ours will have the wrong texture.
        // It's safe to reset it here for all blocks.
        this.fixedTexture = -1;
    }
}