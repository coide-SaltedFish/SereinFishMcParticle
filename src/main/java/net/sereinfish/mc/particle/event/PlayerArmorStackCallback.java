package net.sereinfish.mc.particle.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;

public interface PlayerArmorStackCallback {

    Event<PlayerArmorStackCallback> EVENT = EventFactory.createArrayBacked(PlayerArmorStackCallback.class,
            listeners -> (player, serverWorld, itemStack, equipmentSlotType) -> {
                for (PlayerArmorStackCallback listener : listeners) {
                    ActionResult result = listener.interact(player, serverWorld, itemStack, equipmentSlotType);

                    if (result != ActionResult.PASS) {
                        return result;
                    }
                }

                return ActionResult.PASS;
            });

    ActionResult interact(ServerPlayerEntity player, ServerWorld serverWorld, ItemStack itemStack, int equipmentSlotType);
}
