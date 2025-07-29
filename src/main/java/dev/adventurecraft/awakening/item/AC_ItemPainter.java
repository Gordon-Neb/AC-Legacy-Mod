package dev.adventurecraft.awakening.item;

import dev.adventurecraft.awakening.tile.AC_TileBlueprint;
import dev.adventurecraft.awakening.tile.IBlueprintBlock;
import net.minecraft.world.ItemInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.tile.Tile;
import net.minecraft.world.level.tile.entity.TileEntity; // Make sure this is imported

public class AC_ItemPainter extends Item {

    private static int storedTexture = -1;

    public AC_ItemPainter(int id) {
        super(id);
        this.setDescriptionId("painter");
    }

    @Override
    public boolean useOn(ItemInstance stack, Player player, Level level, int x, int y, int z, int side) {
        int blockId = level.getTile(x, y, z);
        Tile tile = Tile.tiles[blockId];
        System.out.println("[Painter] useOn called on block ID " + blockId + " at " + x + "," + y + "," + z);

        if (tile == null) {
            return false;
        }

        // Step 1: If the clicked block is NOT a blueprint, store its texture.
        if (!(tile instanceof IBlueprintBlock)) {
            storedTexture = tile.getTexture(side, 0);
            System.out.println("[Painter] Stored texture ID " + storedTexture + " from block at " + x + "," + y + "," + z);
            return true; // Successfully stored texture.
        }

        // Step 2: If the clicked block IS a blueprint, apply the stored texture.
        if (storedTexture >= 0) {
            // --- THIS IS THE MISSING LOGIC ---
            TileEntity te = level.getTileEntity(x, y, z);
            if (te instanceof AC_TileBlueprint) {
                AC_TileBlueprint blueprint = (AC_TileBlueprint) te;
                blueprint.overriddenTexture = storedTexture;
                blueprint.setChanged(); // Mark the TileEntity as needing to be saved.

                // This forces the client to re-render the block, making the change visible.
                if (!level.isClientSide) {
                    level.tileUpdated(x, y, z, blockId);
                }

                System.out.println("[Painter] Applied texture ID " + storedTexture + " to blueprint at " + x + "," + y + "," + z);
                return true; // Successfully applied texture.
            } else {
                System.err.println("[Painter] Clicked on a blueprint block, but couldn't find its TileEntity at " + x + "," + y + "," + z);
            }
        }

        // Return false if we tried to apply a texture but didn't have one stored.
        return false;
    }
}