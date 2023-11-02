package cn.encmys.ykdz.forest.climbablechain.task;

import cn.encmys.ykdz.forest.climbablechain.ClimbableChain;
import cn.encmys.ykdz.forest.climbablechain.manager.ClimbManager;
import cn.encmys.ykdz.forest.climbablechain.player.ClimbingPlayer;
import cn.encmys.ykdz.forest.climbablechain.utils.BlockUtils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class ClimbTask extends BukkitRunnable {

    protected ClimbableChain instance = ClimbableChain.getInstance();
    protected ClimbingPlayer climbingPlayer;
    protected int taskId;
    protected ClimbManager climbManager = ClimbableChain.getClimbManager();

    public ClimbTask(ClimbingPlayer climbingPlayer) {
        this.climbingPlayer = climbingPlayer;
        this.taskId = this.runTaskTimer(instance, 0, 5).getTaskId();
    }

    @Override
    public void run() {
        Block firstChain = climbingPlayer.getFirstChain();
        Location firstChainLoc = firstChain.getLocation();
        ArrayList<Block> chains = new ArrayList<>();
        Block nextChain = Objects.requireNonNull(firstChainLoc.getWorld()).getBlockAt(firstChainLoc.add(0, 1, 0));
        while (nextChain.getType().toString().contains("CHAIN")) {
            chains.add(nextChain);
            nextChain = firstChainLoc.getWorld().getBlockAt(firstChainLoc.add(0, 1, 0));
        }
        climbingPlayer.tick();
    }

    @Override
    public void cancel() {
        climbingPlayer.cancel();
    }

    @Override
    public int getTaskId() {
        return taskId;
    }
}
