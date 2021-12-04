package net.sereinfish.mc.particle.net;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.Packet;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.sereinfish.mc.particle.Start;

public class NetWorkHandler {

    /**
     * 向指定玩家发送数据包
     * @param serverWorld
     * @param force
     * @param packet
     * @return
     */
    public static boolean sendToPlayerIfNearby(PlayerEntity source, ServerPlayerEntity receive, ServerWorld serverWorld, boolean force, Packet<?> packet) {
        if (!isReceive(source, receive)){
            return false;
        }

        if (receive.getWorld().toServerWorld() != serverWorld) {
            return false;
        }
        BlockPos blockPos = receive.getBlockPos();
        if (blockPos.isWithinDistance(new Vec3d(receive.getX(), receive.getY(), receive.getZ()), force ? 512.0 : 32.0)) {
            receive.networkHandler.sendPacket(packet);
            return true;
        }
        return false;
    }

    /**
     * 判断是否接收
     * @param source
     * @param receive
     * @return
     */
    public static boolean isReceive(PlayerEntity source, PlayerEntity receive){
        //总开关
        if (!Start.modConfig.get(source).isAllEnable()){
            return false;
        }
        if (!Start.modConfig.get(receive).isAllEnable()){
            return false;
        }
        //不给别人看
        if (Start.modConfig.get(source).isOnlyOwnEnable()){
            if (!source.getUuid().toString().equals(receive.getUuid().toString())){
                return false;
            }
        }

        //不看别人
        if (!Start.modConfig.get(receive).isOtherEnable()){
            if (!source.getUuid().toString().equals(receive.getUuid().toString())){
                return false;
            }
        }

        //不看自己
        if (source.getUuid().toString().equals(receive.getUuid().toString())){
            if (Start.modConfig.get(source).isOnlyOtherEnable()){
                return false;
            }
        }
        return true;
    }
}
