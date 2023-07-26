package thunder.hack.injection;

import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import thunder.hack.Thunderhack;
import thunder.hack.events.impl.EventEntityRemoved;
import thunder.hack.events.impl.EventEntitySpawn;

import static thunder.hack.utility.Util.mc;

@Mixin(ClientWorld.class)
public class MixinClientWorld {
    @Inject(method = "addEntityPrivate", at = @At("HEAD"), cancellable = true)
    public void onAddEntity(int id, Entity entity, CallbackInfo ci) {
        EventEntitySpawn ees = new EventEntitySpawn(entity);
        Thunderhack.EVENT_BUS.post(ees);
        if (ees.isCancelled()) {
            ci.cancel();
        }
    }


    @Inject(method = "removeEntity", at = @At("HEAD"))
    public void onRemoveEntity(int entityId, Entity.RemovalReason removalReason, CallbackInfo ci) {
        if(mc.world == null) return;
        EventEntityRemoved eer = new EventEntityRemoved(mc.world.getEntityById(entityId));
        Thunderhack.EVENT_BUS.post(eer);
    }
}
