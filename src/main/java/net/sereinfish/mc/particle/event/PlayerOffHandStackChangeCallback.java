package net.sereinfish.mc.particle.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;

public interface PlayerOffHandStackChangeCallback {
    Event<PlayerOffHandStackChangeCallback> EVENT = EventFactory.createArrayBacked(PlayerOffHandStackChangeCallback.class,
            listeners -> (player, serverWorld, itemStack) -> {
                for (PlayerOffHandStackChangeCallback listener : listeners) {
                    ActionResult result = listener.interact(player, serverWorld, itemStack);

                    if (result != ActionResult.PASS) {
                        return result;
                    }
                }

                return ActionResult.PASS;
            });

    ActionResult interact(ServerPlayerEntity player, ServerWorld serverWorld, ItemStack itemStack);
}
