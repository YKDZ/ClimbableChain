package cn.encmys.ykdz.forest.climbablechain.manager;

import cn.encmys.ykdz.forest.climbablechain.data.ChainData;
import cn.encmys.ykdz.forest.climbablechain.player.ClimbingPlayer;
import cn.encmys.ykdz.forest.climbablechain.task.ClimbTask;
import cn.encmys.ykdz.forest.climbablechain.utils.BlockUtils;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;

import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class ClimbManager implements Listener {
    private final ConcurrentHashMap<UUID, ClimbTask> climbTaskMap = new ConcurrentHashMap<>();
    protected final ClimbManager climbManager;

    public ClimbManager() {
        this.climbManager = this;
    }

    @EventHandler
    public void onClimb(PlayerToggleSneakEvent e) {
        Player player = e.getPlayer();
        UUID uuid = player.getUniqueId();
        if(e.isSneaking()) {
            List<Block> front = BlockUtils.getBlocksInFront(player, 1);

            Iterator<Block> iterator = front.iterator();
            while (iterator.hasNext()) {
                Block chain = iterator.next();
                if (chain.getType() == Material.CHAIN) {
                    climbTaskMap.put(uuid, new ClimbTask(new ClimbingPlayer(player, chain)));
                    return;
                }
            }
        } else if(!e.isSneaking() && climbTaskMap.containsKey(uuid)) {
            ClimbTask task = this.getClimbTask(uuid);
            this.cancelClimb(task, true);
        }
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent e) {
        Block placed = e.getBlockPlaced();
        Block against = e.getBlockAgainst();
        for(ClimbTask task : climbTaskMap.values()) {
            ChainData chainData = task.getClimbingPlayer().getChainData();
            if((chainData.contains(placed.getLocation().getWorld().getBlockAt(placed.getLocation().add(0, -1, 0))) || chainData.contains(against))
                    && placed.getType() == Material.CHAIN) {
                task.getClimbingPlayer().updateChainData();
            }
        }
    }

    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        Block broke = e.getBlock();
        for(ClimbTask task : climbTaskMap.values()) {
            if(task.getClimbingPlayer().getChainData().contains(broke)) {
                task.getClimbingPlayer().updateChainData();
            }
        }
    }

    public ClimbTask getClimbTask(UUID uuid) {
        return climbTaskMap.get(uuid);
    }

    public void cancelClimb(UUID uuid, boolean eject) {
        ClimbTask task = getClimbTask(uuid);
        task.cancelClimbTask(eject);
        this.removeClimbTask(uuid);
    }

    public void cancelClimb(ClimbTask task, boolean eject) {
        UUID uuid = task.getClimbingPlayer().getUniqueId();
        task.cancelClimbTask(eject);
        this.removeClimbTask(uuid);
    }

    public void removeClimbTask(UUID uuid) {
        climbTaskMap.remove(uuid);
    }

}
