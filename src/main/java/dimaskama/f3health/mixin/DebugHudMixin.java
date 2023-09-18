package dimaskama.f3health.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.DebugHud;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.Slice;

import java.util.List;

@Mixin(DebugHud.class)
public class DebugHudMixin {
    @Redirect(
            method = "getRightText",
            at = @At(
                    value = "INVOKE",
                    target = "Ljava/util/List;add(Ljava/lang/Object;)Z"
            ),
            slice = @Slice(
                    from = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;getType()Lnet/minecraft/entity/EntityType;")
            )
    )
    private <E> boolean redirect(List<Object> instance, E e) {
        instance.add(e);
        Entity entity = MinecraftClient.getInstance().targetedEntity;
        if (entity instanceof LivingEntity livingEntity)
            instance.add(String.format("Health: %.3f/%.1f", livingEntity.getHealth(), livingEntity.getMaxHealth()));
        return true;
    }
}
