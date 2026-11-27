package carpetstairsaddition.mixin;

import carpetstairsaddition.config.CSASettings;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemStack.class)
public class EnchantedGoldenAppleMixin {

    @Inject(method = "finishUsingItem", at = @At("RETURN"))
    private void onFinishUsingItem(Level level, LivingEntity user, CallbackInfoReturnable<ItemStack> cir) {
        ItemStack stack = (ItemStack) (Object) this;
        if (CSASettings.enhancedEnchantedGoldenApple && stack.getItem() == Items.ENCHANTED_GOLDEN_APPLE && user instanceof Player) {
            // 清除原版效果
            user.removeEffect(MobEffects.REGENERATION);
            user.removeEffect(MobEffects.ABSORPTION);

            // 应用增强效果
            // 生命恢复 V 级，持续 30 秒（600 ticks）
            user.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 600, 4));

            // 伤害吸收 I 级，持续 2 分钟（2400 ticks）
            user.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 2400, 0));
        }
    }
}