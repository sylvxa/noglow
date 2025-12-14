package lol.sylvie.noglow.mixin;

import lol.sylvie.noglow.NoGlow;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = { LivingEntity.class, Entity.class })
public class EntityMixin {
    @Inject(method = "isCurrentlyGlowing", at = @At("HEAD"), cancellable = true)
    public void isGlowing(CallbackInfoReturnable<Boolean> cir) {
        if (!NoGlow.CONFIG.isGlowing()) {
            cir.setReturnValue(false);
        }
    }
}
