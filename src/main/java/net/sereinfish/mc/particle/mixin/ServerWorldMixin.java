package net.sereinfish.mc.particle.mixin;

import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.level.ServerWorldProperties;
import net.sereinfish.mc.particle.event.ServerWorldForTickCallback;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.BooleanSupplier;

@Mixin(ServerWorld.class)
public abstract class ServerWorldMixin {
    @Shadow public abstract ServerWorld toServerWorld();
    @Shadow @Final private ServerWorldProperties worldProperties;

    @Inject(at = @At("RETURN"), method = "tick(Ljava/util/function/BooleanSupplier;)V")
    public void tick(BooleanSupplier shouldKeepTicking, CallbackInfo ci){
        ServerWorldForTickCallback.EVENT.invoker().interact(toServerWorld(), worldProperties.getTime());
    }
}
