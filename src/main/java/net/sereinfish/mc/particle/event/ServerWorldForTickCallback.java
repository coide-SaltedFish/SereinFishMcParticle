package net.sereinfish.mc.particle.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;

public interface ServerWorldForTickCallback {
    Event<ServerWorldForTickCallback> EVENT = EventFactory.createArrayBacked(ServerWorldForTickCallback.class,
            listeners -> (serverWorld, tick) -> {
                for (ServerWorldForTickCallback listener : listeners) {
                    ActionResult result = listener.interact(serverWorld, tick);

                    if (result != ActionResult.PASS) {
                        return result;
                    }
                }

                return ActionResult.PASS;
            });

    ActionResult interact(ServerWorld serverWorld, long tick);
}
