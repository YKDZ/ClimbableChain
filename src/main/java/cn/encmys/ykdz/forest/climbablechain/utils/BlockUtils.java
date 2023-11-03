package cn.encmys.ykdz.forest.climbablechain.utils;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

public class BlockUtils {
    public static List<Block> getBlocksInRadius(Location center, int radius) {
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

    public static List<Block> getBlocksInFront(Player player, double distance) {
        Location chestLoc = player.getEyeLocation().add(0, -0.2, 0);
        World world = chestLoc.getWorld();
        Vector vec = player.getLocation().getDirection().normalize().multiply(0.05);
        ArrayList<Block> blocks = new ArrayList<>();
        Block temp = null;
        for(int i = 0; i * 0.05 < distance; i++) {
            Block block = world.getBlockAt(chestLoc.add(vec));
            if(block != temp) {
                blocks.add(block);
            }
            temp = block;
        }
        return blocks;
    }
}
