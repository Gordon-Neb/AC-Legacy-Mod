package dev.adventurecraft.awakening.mixin.entity;

import dev.adventurecraft.awakening.tile.AC_TileBlueprint;
import net.minecraft.world.level.tile.entity.TileEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TileEntity.class)
public abstract class MixinTileEntity {

    // This creates a public, static way to call the private "setId" method.
    @Invoker("setId")
    private static void invokeSetId(Class<?> teClass, String id) {
        // The body of this method is ignored; Mixin will implement it at runtime.
        throw new AssertionError();
    }

    // This injects our registration call into the static initializer of TileEntity.
    @Inject(method = "<clinit>", at = @At("TAIL"))
    private static void registerBlueprintTileEntity(CallbackInfo ci) {
        // Register our custom tile entity with its string ID.
        invokeSetId(AC_TileBlueprint.class, "ac_blueprint");
    }
}