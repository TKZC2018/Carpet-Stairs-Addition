package carpetstairsaddition.mixin;

import carpetstairsaddition.config.CSASettings;
import net.minecraft.core.RegistryAccess;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.ShapedRecipe;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ShapedRecipe.class)
public class ShapedRecipeMixin {

    @Inject(method = "matches", at = @At("RETURN"), cancellable = true)
    private void onMatches(CraftingContainer input, net.minecraft.world.level.Level world,
                           CallbackInfoReturnable<Boolean> cir) {
        if (!cir.getReturnValue()) {
            return;
        }

        ShapedRecipe recipe = (ShapedRecipe) (Object) this;
        ItemStack result;
        try {
            result = recipe.assemble(input, RegistryAccess.EMPTY);
        } catch (RuntimeException e) {
            return;
        }

        if (!isAllowed(result, input)) {
            cir.setReturnValue(false);
            cir.cancel();
        }
    }

    private static boolean contains(CraftingContainer input, net.minecraft.world.item.Item item) {
        for (int i = 0; i < input.getContainerSize(); i++) {
            if (input.getItem(i).getItem() == item) {
                return true;
            }
        }
        return false;
    }

    private static boolean isAllowed(ItemStack result, CraftingContainer input) {
        var item = result.getItem();

        if (item == Items.ENCHANTED_GOLDEN_APPLE) {
            return CSASettings.enchantedGoldenAppleCrafting;
        }
        if (item == Items.SPONGE) {
            return CSASettings.spongeCrafting;
        }
        if (item == Items.HEART_OF_THE_SEA) {
            return CSASettings.heartOfTheSeaCrafting;
        }
        // Vanilla has no dropper-based dispenser recipe, no iron-block minecart recipe,
        // and no craftable elytra, so these signatures only match the mod's recipes.
        if (item == Items.DISPENSER) {
            return !contains(input, Items.DROPPER) || CSASettings.betterDispenserCrafting;
        }
        if (item == Items.MINECART) {
            return !contains(input, Items.IRON_BLOCK) || CSASettings.betterMinecartCrafting;
        }
        if (item == Items.ELYTRA) {
            return !contains(input, Items.PHANTOM_MEMBRANE) || CSASettings.betterElytraCrafting;
        }
        return true;
    }
}