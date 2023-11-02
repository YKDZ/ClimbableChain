package cn.encmys.ykdz.forest.climbablechain.utils;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;

import java.util.ArrayList;
import java.util.List;

public class BlockUtils {
    public static List<Block> getBlocksAround(Location center, int radius) {
        World world = center.getWorld();
        int minX = center.getBlockX() - radius;
        int minY = center.getBlockY() - radius;
        int minZ = center.getBlockZ() - radius;
        int maxX = center.getBlockX() + radius;
        int maxY = center.getBlockY() + radius;
        int maxZ = center.getBlockZ() + radius;

        List<Block> blocks = new ArrayList<>();
        for (int x = minX; x <= maxX; x++) {
            for (int y = minY; y <= maxY; y++) {
                for (int z = minZ; z <= maxZ; z++) {
                    Block block = null;
                    if (world != null) {
                        block = world.getBlockAt(x, y, z);
                    }
                    blocks.add(block);
                }
            }
        }

        return blocks;
    }
}
