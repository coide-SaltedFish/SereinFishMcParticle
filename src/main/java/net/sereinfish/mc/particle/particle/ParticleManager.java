package net.sereinfish.mc.particle.particle;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.sereinfish.mc.particle.Start;
import net.sereinfish.mc.particle.entity.ParticleNBT;
import net.sereinfish.mc.particle.entity.TickTask;
import net.sereinfish.mc.particle.event.PlayerArmorStackCallback;
import net.sereinfish.mc.particle.event.PlayerMainHandStackChangeCallback;
import net.sereinfish.mc.particle.event.PlayerOffHandStackChangeCallback;
import net.sereinfish.mc.particle.utils.PrefixUtil;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 粒子数据发送管理器
 */
public class ParticleManager {
    public static final int FEET = 0;
    public static final int LEGS = 1;
    public static final int CHEST = 2;
    public static final int HEAD = 3;
    public static final int MAIN_HAND = 4;
    public static final int OFF_HAND = 5;

    public static ParticleManager INSTANCE;

    private Map<String, TickTask> taskList = new ConcurrentHashMap<>();//任务列表，<uuid,taskId>

    private ParticleManager(){
        event();
    }

    public static void init(){
        INSTANCE = new ParticleManager();
    }

    private void event(){
        //主手物品切换事件
        PlayerMainHandStackChangeCallback.EVENT.register((player, serverWorld, itemStack) -> {
            execute(player, serverWorld, itemStack, MAIN_HAND);
            return ActionResult.SUCCESS;
        });

        //副手物品切换事件
        PlayerOffHandStackChangeCallback.EVENT.register((player, serverWorld, itemStack) -> {
            execute(player, serverWorld, itemStack, OFF_HAND);
            return ActionResult.SUCCESS;
        });

        //盔甲
        PlayerArmorStackCallback.EVENT.register((player, serverWorld, itemStack,  equipmentSlotType) -> {
            execute(player, serverWorld, itemStack, equipmentSlotType);
            return ActionResult.SUCCESS;
        });
    }

    public void delete(PlayerEntity player){
        taskList.remove(player.getUuid().toString());
    }

    /**
     * 得到任务表
     * @param player
     * @return
     */
    private TickTask getTickTask(PlayerEntity player){
        if (!taskList.containsKey(player.getUuid().toString())){
            taskList.put(player.getUuid().toString(), new TickTask(player));
        }

        return taskList.get(player.getUuid().toString());
    }

    /**
     * 执行
     * @param player
     * @param serverWorld
     * @param itemStack
     */
    private void execute(ServerPlayerEntity player, ServerWorld serverWorld, ItemStack itemStack, int type){
        //清除之前的效果
        if (taskList.containsKey(player.getUuid().toString())){
            //前缀
            //Start.LOGGER.info("前缀取消：{}",player.getGameProfile().getName());
            PrefixUtil.removePrefix(serverWorld, player);

            //Start.LOGGER.info("关停粒子：{}",player.getGameProfile().getName());
            TickTaskManager.INSTANCE.delete(taskList.get(player.getUuid().toString()).getTaskID());
            taskList.remove(player.getUuid().toString());
        }

        NbtCompound nbtCompound = itemStack.getNbt();
        if (nbtCompound != null){
            nbtCompound = nbtCompound.getCompound("SFParticle");

            //Start.LOGGER.info("物品[{}]：" + nbtCompound, player.getGameProfile().getName());
            TickTask tickTask = getTickTask(player);
            if (nbtCompound != null){
                ParticleNBT particleNBT = new ParticleNBT(nbtCompound);

                //如果名称为空
                if(particleNBT.getName() == null || particleNBT.getName().equals("")){
                    Start.LOGGER.error("错误的物品名称：null");
                    return;
                }

                //前缀
                if (particleNBT.getPrefix() != null && !particleNBT.getPrefix().equals("") && particleNBT.isPrefixEnable()){
                    //Start.LOGGER.info("前缀设置：{}",String.format("[%s] %s", particleNBT.getPrefix(),player.getGameProfile().getName()));
                    PrefixUtil.setPrefix(serverWorld, player, particleNBT.getPrefix(), particleNBT.getPrefixColor());
                }
                tickTask.set(particleNBT, type);
                //开始处理粒子
                //Start.LOGGER.info("开始处理粒子：{}",player.getGameProfile().getName());
                int id = TickTaskManager.INSTANCE.register(new Context(player, tickTask));
                tickTask.setTaskID(id);
                taskList.put(player.getUuid().toString(), tickTask);
            }else {
                tickTask.set(null ,type);
            }
        }
    }
}
