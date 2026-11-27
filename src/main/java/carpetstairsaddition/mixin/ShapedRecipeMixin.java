package carpetstairsaddition.mixin;

import carpetstairsaddition.config.CSASettings;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.world.item.crafting.CraftingInput;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ShapedRecipe.class)
public class ShapedRecipeMixin {

    @Inject(method = "matches", at = @At("HEAD"), cancellable = true)
    private void onMatches(CraftingInput input, net.minecraft.world.level.Level world, CallbackInfoReturnable<Boolean> cir) {
        ShapedRecipe recipe = (ShapedRecipe) (Object) this;
        ItemStack result = recipe.assemble(input);
        
        // Check if this is the enchanted golden apple recipe
        if (result.getItem() == Items.ENCHANTED_GOLDEN_APPLE) {
            if (!CSASettings.enchantedGoldenAppleCrafting) {
                cir.setReturnValue(false);
                cir.cancel();
            }
        }
        
        // Check if this is the sponge recipe
        if (result.getItem() == Items.SPONGE) {
            if (!CSASettings.spongeCrafting) {
                cir.setReturnValue(false);
                cir.cancel();
            }
        }
        
        // Check if this is the heart of the sea recipe
        if (result.getItem() == Items.HEART_OF_THE_SEA) {
            if (!CSASettings.heartOfTheSeaCrafting) {
                cir.setReturnValue(false);
                cir.cancel();
            }
        }
        
        // Check if this is the better dispenser recipe (uses dropper)
        // Only block if rule is disabled AND the recipe uses a dropper
        if (result.getItem() == Items.DISPENSER) {
            if (!CSASettings.betterDispenserCrafting) {
                // Check if this recipe uses a dropper (custom recipe characteristic)
                boolean usesDropper = false;
                for (int i = 0; i < input.size(); i++) {
                    if (input.getItem(i).getItem() == Items.DROPPER) {
                        usesDropper = true;
                        break;
                    }
                }
                if (usesDropper) {
                    cir.setReturnValue(false);
                    cir.cancel();
                }
            }
        }
        
        // Check if this is the better minecart recipe (uses iron blocks)
        // Only block if rule is disabled AND the recipe uses iron blocks
        if (result.getItem() == Items.MINECART) {
            if (!CSASettings.betterMinecartCrafting) {
                // Check if this recipe uses iron blocks (custom recipe characteristic)
                boolean usesIronBlocks = false;
                for (int i = 0; i < input.size(); i++) {
                    if (input.getItem(i).getItem() == Items.IRON_BLOCK) {
                        usesIronBlocks = true;
                        break;
                    }
                }
                if (usesIronBlocks) {
                    cir.setReturnValue(false);
                    cir.cancel();
                }
            }
        }
        
        // Check if this is the better elytra recipe (uses phantom membrane and elytra)
        // Only block if rule is disabled AND the recipe uses phantom membrane
        if (result.getItem() == Items.ELYTRA) {
            if (!CSASettings.betterElytraCrafting) {
                // Check if this recipe uses phantom membrane (custom recipe characteristic)
                boolean usesPhantomMembrane = false;
                for (int i = 0; i < input.size(); i++) {
                    if (input.getItem(i).getItem() == Items.PHANTOM_MEMBRANE) {
                        usesPhantomMembrane = true;
                        break;
                    }
                }
                if (usesPhantomMembrane) {
                    cir.setReturnValue(false);
                    cir.cancel();
                }
            }
        }
    }
}