package net.sereinfish.mc.particle.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.minecraft.server.command.CommandManager;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Formatting;
import net.sereinfish.mc.particle.Start;
import net.sereinfish.mc.particle.utils.PrefixUtil;

public class ModCommand {
    public static ModCommand INSTANCE;

    private final String errTip = "[SereinFishMod] 糟糕，出现了意料之外的错误，请联系服务器管理员";

    private ModCommand(){
        CommandRegistrationCallback.EVENT.register((dispatcher, dedicated) -> {
            dispatcher.register(CommandManager.literal("SFParticle")
                    .then(
                            CommandManager.literal("allEnable")
                                    .then(CommandManager.literal("off").executes(context -> {
                                        try {
                                            Start.modConfig.get(context.getSource().getPlayer()).allEnable(false);
                                            context.getSource().getPlayer().sendMessage(new LiteralText("已关闭全局特殊粒子效果显示").formatted(Formatting.GREEN), false);
                                            return Command.SINGLE_SUCCESS;
                                        }catch (Exception e){
                                            Start.LOGGER.error("命令执行错误", e);
                                        }
                                        throw new SimpleCommandExceptionType(new LiteralText(errTip).formatted(Formatting.RED)).create();
                                    }))
                                    .then(CommandManager.literal("on").executes(context -> {
                                        try {
                                            Start.modConfig.get(context.getSource().getPlayer()).allEnable(true);
                                            context.getSource().getPlayer().sendMessage(new LiteralText("已开启全局特殊粒子效果显示").formatted(Formatting.GREEN), false);
                                            return  Command.SINGLE_SUCCESS;
                                        }catch (Exception e){
                                            Start.LOGGER.error("命令执行错误", e);
                                        }
                                        throw new SimpleCommandExceptionType(new LiteralText(errTip).formatted(Formatting.RED)).create();
                                    }))
                                    .then(CommandManager.literal("state").executes(context -> {
                                        try {
                                            context.getSource().getPlayer().sendMessage(new LiteralText("全局特殊粒子效果:" + Start.modConfig.get(context.getSource().getPlayer()).isAllEnable())
                                                    .formatted(Formatting.GREEN), true);
                                            return  Command.SINGLE_SUCCESS;
                                        }catch (Exception e){
                                            Start.LOGGER.error("命令执行错误", e);
                                        }
                                        throw new SimpleCommandExceptionType(new LiteralText(errTip).formatted(Formatting.RED)).create();
                                    }))
                        )
                    .then(
                            CommandManager.literal("otherEnable")
                                    .then(CommandManager.literal("off").executes(context -> {
                                        try {
                                            Start.modConfig.get(context.getSource().getPlayer()).otherEnable(false);
                                            context.getSource().getPlayer().sendMessage(new LiteralText("已设置不可见他人粒子效果").formatted(Formatting.GREEN), false);
                                            return Command.SINGLE_SUCCESS;
                                        }catch (Exception e){
                                            Start.LOGGER.error("命令执行错误", e);
                                        }
                                        throw new SimpleCommandExceptionType(new LiteralText(errTip).formatted(Formatting.RED)).create();
                                    }))
                                    .then(CommandManager.literal("on").executes(context -> {
                                        try {
                                            Start.modConfig.get(context.getSource().getPlayer()).otherEnable(true);
                                            context.getSource().getPlayer().sendMessage(new LiteralText("已设置可见他人粒子效果").formatted(Formatting.GREEN), false);
                                            return  Command.SINGLE_SUCCESS;
                                        }catch (Exception e){
                                            Start.LOGGER.error("命令执行错误", e);
                                        }
                                        throw new SimpleCommandExceptionType(new LiteralText(errTip).formatted(Formatting.RED)).create();
                                    }))
                                    .then(CommandManager.literal("state").executes(context -> {
                                        try {
                                            context.getSource().getPlayer().sendMessage(new LiteralText("可见他人粒子效果:" + Start.modConfig.get(context.getSource().getPlayer()).isOtherEnable())
                                                    .formatted(Formatting.GREEN), true);
                                            return  Command.SINGLE_SUCCESS;
                                        }catch (Exception e){
                                            Start.LOGGER.error("命令执行错误", e);
                                        }
                                        throw new SimpleCommandExceptionType(new LiteralText(errTip).formatted(Formatting.RED)).create();
                                    }))
                        )
                    .then(
                            CommandManager.literal("onlyOwnEnable")
                                    .then(CommandManager.literal("off").executes(context -> {
                                        try {
                                            Start.modConfig.get(context.getSource().getPlayer()).onlyOwnEnable(false);
                                            context.getSource().getPlayer().sendMessage(new LiteralText("已设置他人可见自己的效果").formatted(Formatting.GREEN), false);
                                            return Command.SINGLE_SUCCESS;
                                        }catch (Exception e){
                                            Start.LOGGER.error("命令执行错误", e);
                                        }
                                        throw new SimpleCommandExceptionType(new LiteralText(errTip).formatted(Formatting.RED)).create();
                                    }))
                                    .then(CommandManager.literal("on").executes(context -> {
                                        try {
                                            Start.modConfig.get(context.getSource().getPlayer()).onlyOwnEnable(true);
                                            context.getSource().getPlayer().sendMessage(new LiteralText("已设置他人不可见自己的效果").formatted(Formatting.GREEN), false);
                                            return  Command.SINGLE_SUCCESS;
                                        }catch (Exception e){
                                            Start.LOGGER.error("命令执行错误", e);
                                        }
                                        throw new SimpleCommandExceptionType(new LiteralText(errTip).formatted(Formatting.RED)).create();
                                    }))
                                    .then(CommandManager.literal("state").executes(context -> {
                                        try {
                                            context.getSource().getPlayer().sendMessage(new LiteralText("他人可见自己的效果:" + Start.modConfig.get(context.getSource().getPlayer()).isOnlyOwnEnable())
                                                    .formatted(Formatting.GREEN), true);
                                            return  Command.SINGLE_SUCCESS;
                                        }catch (Exception e){
                                            Start.LOGGER.error("命令执行错误", e);
                                        }
                                        throw new SimpleCommandExceptionType(new LiteralText(errTip).formatted(Formatting.RED)).create();
                                    }))
                        )
                    .then(
                            CommandManager.literal("onlyOtherEnable")
                                    .then(CommandManager.literal("off").executes(context -> {
                                        try {
                                            Start.modConfig.get(context.getSource().getPlayer()).onlyOtherEnable(false);
                                            context.getSource().getPlayer().sendMessage(new LiteralText("已设置可看自己效果").formatted(Formatting.GREEN), false);
                                            return Command.SINGLE_SUCCESS;
                                        }catch (Exception e){
                                            Start.LOGGER.error("命令执行错误", e);
                                        }
                                        throw new SimpleCommandExceptionType(new LiteralText(errTip).formatted(Formatting.RED)).create();
                                    }))
                                    .then(CommandManager.literal("on").executes(context -> {
                                        try {
                                            Start.modConfig.get(context.getSource().getPlayer()).onlyOtherEnable(true);
                                            context.getSource().getPlayer().sendMessage(new LiteralText("已设置仅看他人效果").formatted(Formatting.GREEN), false);
                                            return  Command.SINGLE_SUCCESS;
                                        }catch (Exception e){
                                            Start.LOGGER.error("命令执行错误", e);
                                        }
                                        throw new SimpleCommandExceptionType(new LiteralText(errTip).formatted(Formatting.RED)).create();
                                    }))
                                    .then(CommandManager.literal("state").executes(context -> {
                                        try {
                                            context.getSource().getPlayer().sendMessage(new LiteralText("仅看他人效果:" + Start.modConfig.get(context.getSource().getPlayer()).isOnlyOtherEnable())
                                                    .formatted(Formatting.GREEN), true);
                                            return  Command.SINGLE_SUCCESS;
                                        }catch (Exception e){
                                            Start.LOGGER.error("命令执行错误", e);
                                        }
                                        throw new SimpleCommandExceptionType(new LiteralText(errTip).formatted(Formatting.RED)).create();
                                    }))
                        )
                    .then(
                            CommandManager.literal("prefixEnable")
                                    .then(CommandManager.literal("off").executes(context -> {
                                        try {
                                            Start.modConfig.get(context.getSource().getPlayer()).prefixEnable(false);
                                            PrefixUtil.removePrefix(context.getSource().getWorld(), context.getSource().getPlayer());
                                            context.getSource().getPlayer().sendMessage(new LiteralText("已取消前缀").formatted(Formatting.GREEN), false);
                                            return Command.SINGLE_SUCCESS;
                                        }catch (Exception e){
                                            Start.LOGGER.error("命令执行错误", e);
                                        }
                                        throw new SimpleCommandExceptionType(new LiteralText(errTip).formatted(Formatting.RED)).create();
                                    }))
                                    .then(CommandManager.literal("on").executes(context -> {
                                        try {
                                            Start.modConfig.get(context.getSource().getPlayer()).prefixEnable(true);
                                            context.getSource().getPlayer().sendMessage(new LiteralText("已开启前缀").formatted(Formatting.GREEN), false);
                                            return  Command.SINGLE_SUCCESS;
                                        }catch (Exception e){
                                            Start.LOGGER.error("命令执行错误", e);
                                        }
                                        throw new SimpleCommandExceptionType(new LiteralText(errTip).formatted(Formatting.RED)).create();
                                    }))
                                    .then(CommandManager.literal("state").executes(context -> {
                                        try {
                                            context.getSource().getPlayer().sendMessage(new LiteralText("前缀:" + Start.modConfig.get(context.getSource().getPlayer()).isPrefixEnable())
                                                    .formatted(Formatting.GREEN), true);
                                            return  Command.SINGLE_SUCCESS;
                                        }catch (Exception e){
                                            Start.LOGGER.error("命令执行错误", e);
                                        }
                                        throw new SimpleCommandExceptionType(new LiteralText(errTip).formatted(Formatting.RED)).create();
                                    }))
                    )
                    );
        });
    }

    public static void init(){
        INSTANCE = new ModCommand();
    }

}
