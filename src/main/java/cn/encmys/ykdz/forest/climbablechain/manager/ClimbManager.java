package cn.encmys.ykdz.forest.climbablechain.manager;

import cn.encmys.ykdz.forest.climbablechain.player.ClimbingPlayer;
import cn.encmys.ykdz.forest.climbablechain.task.ClimbTask;
import cn.encmys.ykdz.forest.climbablechain.utils.BlockUtils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
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
            List<Block> nears = BlockUtils.getBlocksInFront(player, 1);

            Iterator<Block> iterator = nears.iterator();
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
