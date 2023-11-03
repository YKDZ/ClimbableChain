package cn.encmys.ykdz.forest.climbablechain.player;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class ClimbingPlayer {
    protected Player player;
    protected Block firstChain;
    protected String mode = "UP";

    public ClimbingPlayer(Player player, Block firstChain) {
        this.player = player;
        this.firstChain = firstChain;
    }

    public Location getLocation() {
        return player.getLocation();
    }

    public Block getFirstChain() {
        return this.firstChain;
    }

    public Player getPlayer() {
        return this.player;
    }

    public void tick() {
        setUpDown();
        applyUpDown();
    }

    public void setUpDown() {
        float pitch = player.getLocation().getPitch();
        this.mode = pitch > 0 ? "UP" : "DOWN";
    }

    public void applyUpDown() {
        switch (this.mode) {
            case "UP":
                player.removePotionEffect(PotionEffectType.SLOW_FALLING);
                player.addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION, 222222222, 1, false, false, false));
            case "DOWN":
                player.removePotionEffect(PotionEffectType.LEVITATION);
                player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_FALLING, 222222222, 1, false, false, false));
        }
    }

    public void cancel() {
        player.removePotionEffect(PotionEffectType.LEVITATION);
        player.removePotionEffect(PotionEffectType.SLOW_FALLING);
    }
}
