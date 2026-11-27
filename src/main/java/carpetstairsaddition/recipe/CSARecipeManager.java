package carpetstairsaddition.recipe;

import carpetstairsaddition.config.CSASettings;
import net.minecraft.server.MinecraftServer;

public class CSARecipeManager {
    
    public static void reloadRecipes() {
        MinecraftServer server = getServer();
        if (server == null) return;
        
        // Note: Recipe control is handled by ShapedRecipeMixin at runtime
        // This method can be called when needed to refresh recipe state
        // The mixin checks CSASettings flags dynamically during recipe matching
    }
    
    private static MinecraftServer getServer() {
        return carpetstairsaddition.CarpetStairsAdditionMod.getServer();
    }
}
