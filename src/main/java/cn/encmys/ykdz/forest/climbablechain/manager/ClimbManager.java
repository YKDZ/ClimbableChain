package cn.encmys.ykdz.forest.climbablechain.manager;

import cn.encmys.ykdz.forest.climbablechain.ClimbableChain;
import cn.encmys.ykdz.forest.climbablechain.player.ClimbingPlayer;
import cn.encmys.ykdz.forest.climbablechain.task.ClimbTask;
import cn.encmys.ykdz.forest.climbablechain.utils.BlockUtils;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleSneakEvent;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class ClimbManager implements Listener {
    private final ConcurrentHashMap<UUID, ClimbTask> climbTaskMap = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<UUID, ClimbingPlayer> climbingPlayerMap = new ConcurrentHashMap<>();
    protected final ClimbManager climbManager;

    public ClimbManager() {
        this.climbManager = this;
    }

    @EventHandler
    public void onClimb(PlayerToggleSneakEvent e) {
        if(!e.isSneaking()) { return; }

        Player player = e.getPlayer();
        Location center = player.getLocation();
        List<Block> nears = BlockUtils.getBlocksAround(center, 1);

        Iterator<Block> iterator = nears.iterator();
        while(iterator.hasNext()) {
            Block b = iterator.next();
            if(b.getType().toString().contains("CHAIN")) {
                UUID uuid = player.getUniqueId();
                ClimbingPlayer climbingPlayer = new ClimbingPlayer(player, b);
                climbingPlayerMap.put(uuid, climbingPlayer);
                climbTaskMap.put(uuid, new ClimbTask(climbingPlayer));
                return;
            }
        }
    }

    public void removeClimbingPlayer(Player player) {
        UUID uuid = player.getUniqueId();
        climbingPlayerMap.remove(uuid);
    }

    public void removeClimbTask(Player player) {
        UUID uuid = player.getUniqueId();
        climbingPlayerMap.remove(uuid);
    }

}
