package dev.adventurecraft.awakening.item;

import dev.adventurecraft.awakening.extension.block.AC_TexturedBlock;
import dev.adventurecraft.awakening.tile.AC_TileBlueprint;
import dev.adventurecraft.awakening.tile.IBlueprintBlock;
import net.minecraft.world.ItemInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.tile.Tile;
import net.minecraft.world.level.tile.entity.TileEntity;

public class AC_ItemPainter extends Item {

    private static long storedTexture = -1L;

    public AC_ItemPainter(int id) {
        super(id);
        this.setDescriptionId("painter");
    }

    @Override
    public boolean useOn(ItemInstance stack, Player player, Level level, int x, int y, int z, int side) {
        int blockId = level.getTile(x, y, z);
        Tile tile = Tile.tiles[blockId];
        if (tile == null) return false;

        if (!(tile instanceof IBlueprintBlock)) {
            // Clone the full texture data from the clicked face.
            storedTexture = ((AC_TexturedBlock) tile).getTextureForSideEx(level, x, y, z, side);
            System.out.println(String.format("[Painter] Cloned Full Texture Data: %d", storedTexture));
            return true;
        }

        if (storedTexture >= 0L) {
            TileEntity te = level.getTileEntity(x, y, z);
            if (te instanceof AC_TileBlueprint) {
                AC_TileBlueprint blueprint = (AC_TileBlueprint) te;
                blueprint.overriddenTexture = storedTexture;
                blueprint.setChanged(); // Mark the TileEntity as dirty for saving.

                // FIX: Use tileUpdated for your Minecraft version.
                // This forces a visual update by marking the chunk as dirty.
                level.tileUpdated(x, y, z, blockId);

                System.out.println("[Painter] Applied Full Texture Data to blueprint.");
                return true;
            }
        }
        return false;
    }
}