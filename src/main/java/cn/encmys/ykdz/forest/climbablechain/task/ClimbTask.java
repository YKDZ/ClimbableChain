package cn.encmys.ykdz.forest.climbablechain.task;

import cn.encmys.ykdz.forest.climbablechain.ClimbableChain;
import cn.encmys.ykdz.forest.climbablechain.manager.ClimbManager;
import cn.encmys.ykdz.forest.climbablechain.player.ClimbingPlayer;
import cn.encmys.ykdz.forest.climbablechain.utils.BlockUtils;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class ClimbTask extends BukkitRunnable {

    protected ClimbableChain instance = ClimbableChain.getInstance();
    protected ClimbingPlayer climbingPlayer;
    protected int taskId;
    protected boolean isCancelled;
    protected ClimbManager climbManager = ClimbableChain.getClimbManager();

    public ClimbTask(ClimbingPlayer climbingPlayer) {
        this.climbingPlayer = climbingPlayer;
        this.taskId = this.runTaskTimer(instance, 0, 5).getTaskId();
        instance.getLogger().info("创建任务");
    }

    @Override
    public void run() {
        instance.getLogger().info("任务运行");
        Player player = climbingPlayer.getPlayer();
        Block firstChain = climbingPlayer.getFirstChain();
        Location firstChainLoc = firstChain.getLocation();
        ArrayList<Block> chains = new ArrayList<>();
        Block nextChain = Objects.requireNonNull(firstChainLoc.getWorld()).getBlockAt(firstChainLoc.add(0, 1, 0));

        while (nextChain.getType().toString().contains("CHAIN")) {
            instance.getLogger().info("查找锁链");
            chains.add(nextChain);
            nextChain = firstChainLoc.getWorld().getBlockAt(firstChainLoc.add(0, 1, 0));
        }

        Location center = player.getLocation();
        List<Block> nears = BlockUtils.getBlocksAround(center, 1);

        Iterator<Block> iterator = nears.iterator();
        while(iterator.hasNext()) {
            instance.getLogger().info("便利周围方块");
            Block b = iterator.next();
            if(chains.contains(b) && player.isSneaking()) {
                instance.getLogger().info("tick玩家");
                climbingPlayer.tick();
                return;
            }
        }
        this.cancel();
    }

    @Override
    public void cancel() {
        Player player = climbingPlayer.getPlayer();

        climbingPlayer.cancel();
        climbManager.removeClimbingPlayer(player);
        climbManager.removeClimbTask(player);
        instance.getServer().getScheduler().cancelTask(taskId);
    }

    @Override
    public int getTaskId() {
        return taskId;
    }
}
