package net.sereinfish.mc.particle.utils;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.scoreboard.Team;
import net.minecraft.server.world.ServerWorld;
import net.sereinfish.mc.particle.Start;
import net.sereinfish.mc.particle.text.HexLiteralText;

import java.util.Random;

public class PrefixUtil {

    /**
     * 设置前缀
     */
    public static void setPrefix(ServerWorld serverWorld, PlayerEntity player, String prefix, String color){
        //判断设置
        if(!Start.modConfig.get(player).isPrefixEnable()){
            return;
        }
        //判断队伍是否已存在
        if (serverWorld.getServer().getScoreboard().getTeamNames().contains(player.getGameProfile().getName())){
            serverWorld.getServer().getScoreboard().removeTeam(serverWorld.getScoreboard().getTeam(player.getGameProfile().getName()));
        }
        //新建队伍
        Team team = serverWorld.getScoreboard().addTeam(player.getGameProfile().getName());
        team.setPrefix(new HexLiteralText("[" + prefix + "] ").formatted(color));
        serverWorld.getScoreboard().addPlayerToTeam(player.getGameProfile().getName(), team);
    }

    /**
     * 取消前缀
     */
    public static void removePrefix(ServerWorld serverWorld, PlayerEntity player){
        //判断队伍是否已存在
        if (serverWorld.getServer().getScoreboard().getTeamNames().contains(player.getGameProfile().getName())){
            serverWorld.getServer().getScoreboard().removeTeam(serverWorld.getScoreboard().getTeam(player.getGameProfile().getName()));
        }
    }

    /**
     * 得到一个可控范围随机数
     * @param start
     * @param end
     * @return
     */
    public static int getRandom(int start,int end) {
        Random random = new Random();
        return random.nextInt(end - start + 1) + start;
    }

    /**
     * 得到一个可控范围随机数
     * @param start
     * @param end
     * @return
     */
    public static float getRandomFloat(float start,float end) {
        if (start < 0){
            start = 0;
        }
        if (end < 0){
            end = 0;
        }

        if (start == end){
            return start;
        }

        int s = (int) (start * 100);
        int e = (int) (end * 100);

        Random random = new Random();
        int v = random.nextInt(e - s + 1) + s;

        return (float) v / 100;
    }
}
