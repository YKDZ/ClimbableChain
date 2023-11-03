package cn.encmys.ykdz.forest.climbablechain.task;

import cn.encmys.ykdz.forest.climbablechain.ClimbableChain;
import cn.encmys.ykdz.forest.climbablechain.data.ChainData;
import cn.encmys.ykdz.forest.climbablechain.manager.ClimbManager;
import cn.encmys.ykdz.forest.climbablechain.player.ClimbingPlayer;
import cn.encmys.ykdz.forest.climbablechain.utils.BlockUtils;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

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
        Location center = climbingPlayer.getLocation();
        List<Block> nears = BlockUtils.getBlocksInFront(climbingPlayer.getPlayer(), 1);

        for (Block near : nears) {
            ChainData chainData = climbingPlayer.getChainData();
            if (chainData.contains(near) && climbingPlayer.isSneaking()) {
                climbingPlayer.tick();
                return;
            }
        }
        climbManager.cancelClimb(this, true);
    }

    public void cancelClimbTask(boolean eject) {
        climbingPlayer.stop(eject);
        instance.getServer().getScheduler().cancelTask(taskId);
    }

    public ClimbingPlayer getClimbingPlayer() {
        return this.climbingPlayer;
    }

    @Override
    public int getTaskId() {
        return taskId;
    }
}
