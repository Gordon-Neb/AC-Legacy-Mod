package dev.adventurecraft.awakening.item;

import dev.adventurecraft.awakening.extension.block.AC_TexturedBlock;
import dev.adventurecraft.awakening.extension.block.ExBlock;
import dev.adventurecraft.awakening.extension.inventory.ExPlayerInventory;
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
        if (player.isSneaking()) {
            return handleSneakMode(player, level, x, y, z, side);
        } else {
            return handleNormalMode(player, level, x, y, z, side);
        }
    }

    private boolean handleSneakMode(Player player, Level level, int x, int y, int z, int side) {
        int blockId = level.getTile(x, y, z);
        Tile tile = Tile.tiles[blockId];
        if (tile == null) {
            return false;
        }

        if (!(tile instanceof IBlueprintBlock)) {
            if (tile instanceof AC_TexturedBlock) {
                storedTexture = ((AC_TexturedBlock) tile).getTextureForSideEx(level, x, y, z, side);
                System.out.println(String.format("[Painter] Sneak Mode: Cloned Full Texture Data: %d", storedTexture));
                return true;
            }
            return false;
        }

        if (storedTexture >= 0L) {
            TileEntity te = level.getTileEntity(x, y, z);
            if (te instanceof AC_TileBlueprint) {
                AC_TileBlueprint blueprint = (AC_TileBlueprint) te;
                blueprint.overriddenTexture = storedTexture;
                blueprint.setChanged();
                level.tileUpdated(x, y, z, blockId);
                System.out.println("[Painter] Sneak Mode: Applied stored texture to blueprint.");
                return true;
            }
        }
        return false;
    }

    private boolean handleNormalMode(Player player, Level level, int x, int y, int z, int side) {
        int blockId = level.getTile(x, y, z);
        Tile tile = Tile.tiles[blockId];

        if (tile == null || !(tile instanceof IBlueprintBlock)) {
            return false;
        }

        ItemInstance offhandStack = ((ExPlayerInventory) player.inventory).getOffhandItemStack();

        if (offhandStack == null || offhandStack.id >= Tile.tiles.length || Tile.tiles[offhandStack.id] == null) {
            return false;
        }
        Tile offhandBlock = Tile.tiles[offhandStack.id];

        long textureSheet = ((ExBlock) offhandBlock).getTextureNum(null, 0, 0, 0);

        // --- REFINED LOGIC ---
        // Instead of using the clicked 'side', we use a constant value (e.g., 2 for 'north')
        // to ensure the same texture is always pulled from the off-hand block.
        final int CONSISTENT_SIDE_TO_USE = 2; // North face
        int textureIndex = offhandBlock.getTexture(CONSISTENT_SIDE_TO_USE, offhandStack.getAuxValue());

        long textureToApply = (textureSheet << 32) | (textureIndex & 0xFFFFFFFFL);

        TileEntity te = level.getTileEntity(x, y, z);
        if (te instanceof AC_TileBlueprint) {
            AC_TileBlueprint blueprint = (AC_TileBlueprint) te;
            blueprint.overriddenTexture = textureToApply;
            blueprint.setChanged();
            level.tileUpdated(x, y, z, blockId);

            System.out.println(String.format("[Painter] Normal Mode: Applied texture '%d' (Sheet: %d, Index: %d) from offhand item with ID '%d'.",
                textureToApply, textureSheet, textureIndex, offhandBlock.id));
            return true;
        }

        return false;
    }
}