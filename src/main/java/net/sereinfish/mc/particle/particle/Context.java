package net.sereinfish.mc.particle.particle;

import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.sereinfish.mc.particle.entity.TickTask;

/**
 * 执行数据
 */
public class Context {
    private long lastTick = 0;
    private ServerPlayerEntity player;
    private TickTask tickTask;

    public Context(ServerPlayerEntity player, TickTask tickTask) {
        this.player = player;
        this.tickTask = tickTask;
    }

    public ServerWorld getServerWorld() {
        return player.getWorld().toServerWorld();
    }

    public ServerPlayerEntity getPlayer() {
        return player;
    }

    public TickTask getTickTask() {
        return tickTask;
    }

    public long getLastTick() {
        return lastTick;
    }

    public void setLastTick(long lastTick) {
        this.lastTick = lastTick;
    }
}
