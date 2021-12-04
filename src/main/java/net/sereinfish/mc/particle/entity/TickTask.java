package net.sereinfish.mc.particle.entity;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.packet.s2c.play.ParticleS2CPacket;
import net.minecraft.particle.DefaultParticleType;
import net.sereinfish.mc.particle.Start;
import net.sereinfish.mc.particle.particle.Context;
import net.sereinfish.mc.particle.particle.ParticleManager;
import net.sereinfish.mc.particle.particle.ParticleNameMapping;
import net.sereinfish.mc.particle.particle.TickTaskManager;
import net.sereinfish.mc.particle.utils.PrefixUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 粒子任务
 */
public class TickTask {
    private int taskID;
    private PlayerEntity player;

    private ParticleNBT mainHand;
    private ParticleNBT offHand;

    private ParticleNBT feet;
    private ParticleNBT legs;
    private ParticleNBT chest;
    private ParticleNBT head;

    public TickTask(PlayerEntity player) {
        this.player = player;
    }

    public void set(ParticleNBT particleNBT, int type){
        switch (type){
            case ParticleManager.MAIN_HAND:
                mainHand = particleNBT;
                break;
            case ParticleManager.OFF_HAND:
                offHand = particleNBT;
                break;
            case ParticleManager.FEET:
                feet = particleNBT;
                break;
            case ParticleManager.LEGS:
                legs = particleNBT;
                break;
            case ParticleManager.CHEST:
                chest = particleNBT;
                break;
            case ParticleManager.HEAD:
                head = particleNBT;
                break;
            default:
                Start.LOGGER.error("未知的类型：{}", type);
        }
    }

    public List<Data> getPackList(long tick, Context context){
        List<Data> list = new ArrayList<>();

        ParticleNBT[] particleNBTS = new ParticleNBT[]{mainHand, offHand, feet, legs, chest, head};

        for (ParticleNBT particleNBT:particleNBTS){
            if (particleNBT == null){
                continue;
            }

            if (tick - context.getLastTick() >= particleNBT.getInterval()) {
                //执行
                context.setLastTick(tick);
                //产生粒子效果
                DefaultParticleType defaultParticleType = ParticleNameMapping.INSTANCE.get(particleNBT.getName());

                if (defaultParticleType == null) {
                    Start.LOGGER.error("未知的粒子效果：" + particleNBT.getName());
                    TickTaskManager.INSTANCE.delete(taskID);
                }
                //随机范围
                float randomX = PrefixUtil.getRandomFloat(0, particleNBT.getRandomX());
                float randomY = PrefixUtil.getRandomFloat(0, particleNBT.getRandomY());
                float randomZ = PrefixUtil.getRandomFloat(0, particleNBT.getRandomZ());

                if (PrefixUtil.getRandom(0,1) == 1){
                    randomX = -1 * randomX;
                }
                if (PrefixUtil.getRandom(0,1) == 1){
                    randomY = -1 * randomY;
                }
                if (PrefixUtil.getRandom(0,1) == 1){
                    randomZ = -1 * randomZ;
                }

                list.add(new Data(new ParticleS2CPacket(defaultParticleType, particleNBT.isLongDistance(),
                        context.getPlayer().getX() + particleNBT.getOffsetX() + randomX,
                        context.getPlayer().getY() + particleNBT.getOffsetY() + randomY,
                        context.getPlayer().getZ() + particleNBT.getOffsetZ() + randomZ,
                        0, 0, 0, particleNBT.getSpeed(), particleNBT.getCount()), particleNBT.isForce()));
            }
        }
        return list;
    }

    public int getTaskID() {
        return taskID;
    }

    public void setTaskID(int taskID) {
        this.taskID = taskID;
    }

    public PlayerEntity getPlayer() {
        return player;
    }

    public void setPlayer(PlayerEntity player) {
        this.player = player;
    }

    public ParticleNBT getMainHand() {
        return mainHand;
    }

    public void setMainHand(ParticleNBT mainHand) {
        this.mainHand = mainHand;
    }

    public ParticleNBT getOffHand() {
        return offHand;
    }

    public void setOffHand(ParticleNBT offHand) {
        this.offHand = offHand;
    }

    public ParticleNBT getFeet() {
        return feet;
    }

    public void setFeet(ParticleNBT feet) {
        this.feet = feet;
    }

    public ParticleNBT getLegs() {
        return legs;
    }

    public void setLegs(ParticleNBT legs) {
        this.legs = legs;
    }

    public ParticleNBT getChest() {
        return chest;
    }

    public void setChest(ParticleNBT chest) {
        this.chest = chest;
    }

    public ParticleNBT getHead() {
        return head;
    }

    public void setHead(ParticleNBT head) {
        this.head = head;
    }

    public class Data{
        ParticleS2CPacket particleS2CPacket;
        boolean force;

        public Data(ParticleS2CPacket particleS2CPacket, boolean force) {
            this.particleS2CPacket = particleS2CPacket;
            this.force = force;
        }

        public ParticleS2CPacket getParticleS2CPacket() {
            return particleS2CPacket;
        }

        public boolean isForce() {
            return force;
        }
    }
}
