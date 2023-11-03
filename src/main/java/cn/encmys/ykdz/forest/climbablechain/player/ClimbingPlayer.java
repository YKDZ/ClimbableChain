package cn.encmys.ykdz.forest.climbablechain.player;

import cn.encmys.ykdz.forest.climbablechain.ClimbableChain;
import cn.encmys.ykdz.forest.climbablechain.data.ChainData;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.UUID;

public class ClimbingPlayer {
    protected Player player;
    protected ChainData chainData;
    protected ClimbMode mode = ClimbMode.UP;

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
        this.mode = pitch < 0 ? ClimbMode.UP : ClimbMode.DOWN;
    }

    public void applyUpDown() {
        switch (this.mode) {
            case UP:
                ClimbableChain.getInstance().getLogger().info("apply up effect");
                moveUp();
                break;
            case DOWN:
                ClimbableChain.getInstance().getLogger().info("apply down effect");
                moveDown();
        }
        player.playSound(player.getLocation(), Sound.BLOCK_LADDER_STEP, 1, 1);
    }

    public void moveUp() {
        player.setVelocity(new Vector(0, 0.6, 0));
    }

    public void moveDown() {
        player.setVelocity(new Vector(0, -1, 0));
    }

    public void stop(boolean eject) {
        player.setGravity(true);
        if(eject) {
            player.setVelocity(player.getLocation().getDirection().multiply(-1));
        }
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
        DOWN
    }
}
