package net.sereinfish.mc.particle.particle;

import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.util.ActionResult;
import net.minecraft.world.GameMode;
import net.sereinfish.mc.particle.Start;
import net.sereinfish.mc.particle.entity.TickTask;
import net.sereinfish.mc.particle.event.ServerWorldForTickCallback;
import net.sereinfish.mc.particle.net.NetWorkHandler;
import net.sereinfish.mc.particle.utils.PrefixUtil;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TickTaskManager {
    public static TickTaskManager INSTANCE;

    private int taskNum = 0;//任务数
    private Map<Integer, Context> taskList = new ConcurrentHashMap<>();

    private TickTaskManager(){
        execute();
    }

    public static void init(){
        INSTANCE = new TickTaskManager();
    }

    public int register(Context context){
        int id = taskNum;
        context.setLastTick(context.getServerWorld().getTime());
        taskList.put(id, context);
        taskNum++;
        return id;
    }

    public void delete(int id){
        taskList.remove(id);
    }

    private void execute(){
        ServerWorldForTickCallback.EVENT.register((serverWorld, tick) -> {
            for (Map.Entry<Integer,Context> entry:taskList.entrySet()){
                Context context = entry.getValue();

                //判断玩家在线状态
                if (context.getPlayer().isDisconnected()){
                    //Start.LOGGER.info("前缀取消：{}",context.getPlayer().getGameProfile().getName());
                    PrefixUtil.removePrefix(serverWorld, context.getPlayer());
                    //取消粒子
                    delete(entry.getKey());
                    ParticleManager.INSTANCE.delete(context.getPlayer());
                    continue;
                }
                //判断玩家是否旁观
                if (context.getPlayer().isSpectator()){
//                    //Start.LOGGER.info("前缀取消：{}",context.getPlayer().getGameProfile().getName());
//                    PrefixUtil.removePrefix(serverWorld, context.getPlayer());
//                    //取消粒子
//                    delete(entry.getKey());
//                    ParticleManager.INSTANCE.delete(context.getPlayer());
                    continue;
                }

//                if (context.getPlayer().getMovementSpeed() > 0.1f){
//                    context.getPlayer().sendMessage(new LiteralText("移动"), true);
//                }

                for (TickTask.Data data:context.getTickTask().getPackList(tick, context)) {
                    //发包
                    for (ServerPlayerEntity player:serverWorld.getPlayers()){
                        if(NetWorkHandler.sendToPlayerIfNearby(context.getPlayer(), player, serverWorld, data.isForce(), data.getParticleS2CPacket())){
                            Start.LOGGER.debug("成功发送包：{}->{}", context.getPlayer().getGameProfile().getName(), player.getGameProfile().getName());
                        }
                    }
                }
            }
            return ActionResult.SUCCESS;
        });
    }

    }
