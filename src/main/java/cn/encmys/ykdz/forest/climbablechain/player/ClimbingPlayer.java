package cn.encmys.ykdz.forest.climbablechain.player;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class ClimbingPlayer {
    protected Player player;
    protected Block firstChain;

    public ClimbingPlayer(Player player, Block firstChain) {
        this.player = player;
        this.firstChain = firstChain;
    }

    public Location getLocation() {
        return player.getLocation();
    }

    public Block getFirstChain() {
        return firstChain;
    }

    public void tick() {
        changeUpDown(getUpDown());
    }

    public String getUpDown() {
        float pitch = player.getLocation().getPitch();
        return pitch > 0 ? "UP" : "DOWN";
    }

    public void changeUpDown(String mode) {
        switch (mode) {
            case "UP":
                player.removePotionEffect(PotionEffectType.SLOW_FALLING);
                player.setGravity(false);
            case "DOWN":
                player.setGravity(true);
                player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_FALLING, 5, 1, false, false, false));
        }
    }

    public void cancel() {
        player.setGravity(true);
        player.removePotionEffect(PotionEffectType.SLOW_FALLING);
    }
}
