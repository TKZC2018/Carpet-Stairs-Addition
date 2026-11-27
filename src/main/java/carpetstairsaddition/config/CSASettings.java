package carpetstairsaddition.config;

import carpet.settings.Rule;
import carpetstairsaddition.recipe.CSARecipeManager;
import carpetstairsaddition.settings.CSARuleCategory;
import carpetstairsaddition.settings.RecipeRule;

public class CSASettings {
    
    @RecipeRule
    @SuppressWarnings({"deprecation", "removal"})
    @Rule(
        desc = "Allow crafting enchanted golden apples with 8 gold blocks and 1 golden apple",
        category = CSARuleCategory.CSA
    )
    public static boolean enchantedGoldenAppleCrafting = false;
    
    @RecipeRule
    @SuppressWarnings({"deprecation", "removal"})
    @Rule(
        desc = "Allow crafting sponge with 8 hay blocks and 1 slime block",
        category = CSARuleCategory.CSA
    )
    public static boolean spongeCrafting = false;
    
    @RecipeRule
    @SuppressWarnings({"deprecation", "removal"})
    @Rule(
        desc = "Allow crafting heart of the sea with 4 lapis lazuli and 1 nether star",
        category = CSARuleCategory.CSA
    )
    public static boolean heartOfTheSeaCrafting = false;
    
    @RecipeRule
    @SuppressWarnings({"deprecation", "removal"})
    @Rule(
        desc = "Allow crafting dispenser with 2 sticks, 2 strings and 1 dropper",
        category = CSARuleCategory.CSA
    )
    public static boolean betterDispenserCrafting = false;
    
    @RecipeRule
    @SuppressWarnings({"deprecation", "removal"})
    @Rule(
        desc = "Allow crafting 9 minecarts with 5 iron blocks",
        category = CSARuleCategory.CSA
    )
    public static boolean betterMinecartCrafting = false;
    
    @RecipeRule
    @SuppressWarnings({"deprecation", "removal"})
    @Rule(
        desc = "Allow duplicating elytra with 8 phantom membranes and 1 elytra",
        category = CSARuleCategory.CSA
    )
    public static boolean betterElytraCrafting = false;
    
    @SuppressWarnings({"deprecation", "removal"})
    @Rule(
        desc = "Enhanced enchanted golden apple effects: Regeneration V for 30s, Absorption I for 2min",
        category = CSARuleCategory.CSA
    )
    public static boolean enhancedEnchantedGoldenApple = false;

    @SuppressWarnings({"deprecation", "removal"})
    @Rule(
        desc = "Craft player head by combining written book with wither skeleton skull",
        category = CSARuleCategory.CSA
    )
    public static boolean playerHeadCrafting = false;
    
    public static void onReload() {
        if (carpetstairsaddition.CarpetStairsAdditionMod.getServer() != null) {
            CSARecipeManager.reloadRecipes();
        }
    }
}