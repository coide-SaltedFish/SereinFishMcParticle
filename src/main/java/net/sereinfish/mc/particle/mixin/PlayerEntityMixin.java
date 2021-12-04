package net.sereinfish.mc.particle.mixin;

import com.mojang.authlib.GameProfile;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.sereinfish.mc.particle.Start;
import net.sereinfish.mc.particle.event.PlayerArmorStackCallback;
import net.sereinfish.mc.particle.event.PlayerMainHandStackChangeCallback;
import net.sereinfish.mc.particle.event.PlayerOffHandStackChangeCallback;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.UUID;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin{
    private ItemStack afterMainItemStack;
    private ItemStack afterOffItemStack;
    private DefaultedList<ItemStack> afterArmorStacks = DefaultedList.ofSize(4, ItemStack.EMPTY);;

    @Shadow @Final private PlayerInventory inventory;

    @Shadow @Final private GameProfile gameProfile;

    @Inject(at = @At("HEAD"), method = "tick()V")
    public void tick(CallbackInfo info){
        ServerPlayerEntity player = null;
        ServerWorld serverWorld = (ServerWorld) inventory.player.world;
        UUID uuid = PlayerEntity.getUuidFromProfile(gameProfile);

        for (ServerPlayerEntity serverPlayerEntity:serverWorld.getPlayers()){
            if(uuid.equals(serverPlayerEntity.getUuid())){
                player = serverPlayerEntity;
                break;
            }
        }
        if(player == null){
            //Start.LOGGER.error("玩家不存在：" + uuid);
            return;
        }

        ItemStack mainHandStack = player.getMainHandStack();
        ItemStack offHandStack = player.getOffHandStack();

        DefaultedList<ItemStack> armorStacks = inventory.armor;

        //右手检测
        if(afterMainItemStack == null){
            afterMainItemStack = mainHandStack;
            PlayerMainHandStackChangeCallback.EVENT.invoker().interact(player, serverWorld, mainHandStack);
        }else {
            if(!afterMainItemStack.equals(mainHandStack)){
                afterMainItemStack = mainHandStack;
                PlayerMainHandStackChangeCallback.EVENT.invoker().interact(player, serverWorld, mainHandStack);
            }
        }

        //副手检测
        if(afterOffItemStack == null){
            afterOffItemStack = offHandStack;
            PlayerOffHandStackChangeCallback.EVENT.invoker().interact(player, serverWorld, offHandStack);
        }else {
            if(!afterOffItemStack.equals(offHandStack)){
                afterOffItemStack = offHandStack;
                PlayerOffHandStackChangeCallback.EVENT.invoker().interact(player, serverWorld, offHandStack);
            }
        }

        //盔甲检测
        for(int i = 0; i < afterArmorStacks.size(); i++){
            //System.out.println("[" + afterArmorStacks.get(i) + "  " + armorStacks.get(i) + "]");
            if(!afterArmorStacks.get(i).equals(armorStacks.get(i))){
                afterArmorStacks.set(i, armorStacks.get(i));
                PlayerArmorStackCallback.EVENT.invoker().interact(player, serverWorld, afterArmorStacks.get(i), i);
            }
        }
    }
}
