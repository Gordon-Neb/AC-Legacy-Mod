package dev.adventurecraft.awakening.util;

import net.minecraft.world.level.Level;

import java.util.HashMap;
import java.util.Map;

public class AC_TextureOverrideRegistry {

    // Level + position to texture ID
    private static final Map<Long, Integer> overrides = new HashMap<>();

    public static void set(Level level, int x, int y, int z, int textureId) {
        overrides.put(toKey(level, x, y, z), textureId);
    }

    public static int get(Level level, int x, int y, int z) {
        return overrides.getOrDefault(toKey(level, x, y, z), -1);
    }

    public static boolean has(Level level, int x, int y, int z) {
        return overrides.containsKey(toKey(level, x, y, z));
    }

    private static long toKey(Level level, int x, int y, int z) {
        return (((long) System.identityHashCode(level)) & 0xFFFFL) << 48
            | (((long) x & 0xFFFFFFL) << 24)
            | (((long) y & 0xFFL) << 16)
            | ((long) z & 0xFFFFL);
    }

    public static Map<Long, Integer> getAll() {
        return overrides;
    }

    public static void loadFrom(Map<Long, Integer> saved) {
        overrides.clear();
        overrides.putAll(saved);
    }
}
