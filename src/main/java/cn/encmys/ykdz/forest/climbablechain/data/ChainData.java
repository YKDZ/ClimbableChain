package cn.encmys.ykdz.forest.climbablechain.data;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;

import java.util.ArrayList;

public class ChainData {
    protected ArrayList<Block> chainBlocks = new ArrayList<>();
    protected Block starter;

    public ChainData(Block starter) {
        this.starter = starter;
        this.update();
    }

    public void update() {
        chainBlocks.clear();
        Location startLoc = starter.getLocation();
        World world = startLoc.getWorld();
        if (world != null) {
            for(Location p1 = startLoc.clone(), p2 = startLoc.clone(); world.getBlockAt(p1).getType() == Material.CHAIN || world.getBlockAt(p2).getType() == Material.CHAIN; p1.add(0, 1, 0), p2.add(0, -1, 0)) {
                Block b1 = world.getBlockAt(p1);
                Block b2 = world.getBlockAt(p2);
                if(b1.getType() == Material.CHAIN) {
                    chainBlocks.add(b1);
                }
                if(b2.getType() == Material.CHAIN) {
                    chainBlocks.add(b2);
                }
            }
        }
    }

    public boolean contains(Block chain) {
        return chainBlocks.contains(chain);
    }
}
