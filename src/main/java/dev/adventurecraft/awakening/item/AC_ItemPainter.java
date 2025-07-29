package dev.adventurecraft.awakening.item;

import dev.adventurecraft.awakening.tile.AC_TileBlueprint;
import dev.adventurecraft.awakening.tile.IBlueprintBlock; // <-- IMPORTANT
import net.minecraft.world.ItemInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.tile.Tile;
import net.minecraft.world.level.tile.entity.TileEntity;

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

        if (tile == null) {
            return false;
        }

        // CORRECTED: Check if the tile is ANY kind of blueprint block.
        if (!(tile instanceof IBlueprintBlock)) {
            storedTexture = tile.getTexture(side, 0);
            return true;
        }

        if (storedTexture >= 0) {
            TileEntity te = level.getTileEntity(x, y, z);
            if (te instanceof AC_TileBlueprint) {
                AC_TileBlueprint blueprint = (AC_TileBlueprint) te;
                blueprint.overriddenTexture = storedTexture;
                blueprint.setChanged();

                if (!level.isClientSide) {
                    level.tileUpdated(x, y, z, blockId);
                }
                return true;
            }
        }

        return false;
    }
}