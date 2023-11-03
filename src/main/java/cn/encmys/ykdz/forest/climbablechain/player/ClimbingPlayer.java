package cn.encmys.ykdz.forest.climbablechain.player;

import cn.encmys.ykdz.forest.climbablechain.data.ChainData;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.UUID;

public class ClimbingPlayer {
    protected Player player;
    protected ChainData chainData;
    protected ClimbMode mode;

    public ClimbingPlayer(Player player, Block firstChain) {
        this.player = player;
        this.chainData = new ChainData(firstChain);
        player.setGravity(false);
    }

    public Location getLocation() {
        return player.getLocation();
    }

    public void tick() {
        setUpDown();
        applyUpDown();
    }

    public void setUpDown() {
        float pitch = player.getLocation().getPitch();
        if(pitch > 30) {
            this.mode = ClimbMode.DOWN;
        } else if(pitch < -30) {
            this.mode = ClimbMode.UP;
        } else {
            this.mode = ClimbMode.STOP;
        }
    }

    public void applyUpDown() {
        switch (this.mode) {
            case UP:
                moveUp();
                player.playSound(player.getLocation(), Sound.BLOCK_CHAIN_STEP, 1, 1);
                break;
            case DOWN:
                moveDown();
                player.playSound(player.getLocation(), Sound.BLOCK_CHAIN_STEP, 1, 1);
                break;
            case STOP:
                moveStop();
        }
    }

    public void moveUp() {
        player.setVelocity(new Vector(0, 0.1, 0));
    }

    public void moveDown() {
        player.setVelocity(new Vector(0, -0.25, 0));
    }

    public void moveStop() {
        player.setVelocity(new Vector(0, 0, 0));
    }

    public void stop(boolean eject) {
        player.setGravity(true);
        if(eject) {
            player.setVelocity(player.getLocation().getDirection().add(new Vector(0, 1, 0)).normalize().multiply(-0.5));
        }
    }

    public Player getPlayer() {
        return player;
    }

    public ChainData getChainData() {
        return chainData;
    }

    public boolean isSneaking() {
        return player.isSneaking();
    }

    public UUID getUniqueId() {
        return player.getUniqueId();
    }

    public enum ClimbMode {
        UP,
        DOWN,
        STOP
    }
}
