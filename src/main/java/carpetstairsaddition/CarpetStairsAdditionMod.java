package carpetstairsaddition;

import carpet.CarpetExtension;
import carpet.CarpetServer;
import carpetstairsaddition.config.CSASettings;
import carpetstairsaddition.recipe.CSARecipeManager;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import net.fabricmc.api.ModInitializer;
import net.minecraft.server.MinecraftServer;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class CarpetStairsAdditionMod implements ModInitializer, CarpetExtension {
    
    public static final String MOD_ID = "carpetstairsaddition";
    public static final String MOD_NAME = "Carpet Stairs Addition";
    
    private static MinecraftServer server;
    
    @Override
    public void onInitialize() {
        CarpetServer.manageExtension(this);
    }
    
    @Override
    public void onGameStarted() {
        CarpetServer.settingsManager.parseSettingsClass(CSASettings.class);
    }
    
    @Override
    public void onServerLoaded(MinecraftServer server) {
        CarpetStairsAdditionMod.server = server;
        // Initialize recipes when server loads
        CSARecipeManager.reloadRecipes();
    }
    
    @Override
    public void onServerClosed(MinecraftServer server) {
        CarpetStairsAdditionMod.server = null;
    }
    
    @Override
    public void onTick(MinecraftServer server) {
    }
    
    @Override
    public Map<String, String> canHasTranslations(String lang) {
        Map<String, String> translations = new HashMap<>();
        
        // Load translation file based on language
        String langFile = "assets/carpetstairsaddition/lang/" + lang + ".json";
        
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(langFile)) {
            if (inputStream != null) {
                // Use Gson to parse JSON file
                InputStreamReader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
                Gson gson = new Gson();
                JsonObject jsonObject = gson.fromJson(reader, JsonObject.class);
                
                // Extract all key-value pairs from the JSON object
                for (Map.Entry<String, com.google.gson.JsonElement> entry : jsonObject.entrySet()) {
                    String key = entry.getKey();
                    String value = entry.getValue().getAsString();
                    translations.put(key, value);
                }
            }
        } catch (Exception e) {
            // If translation file not found or error occurs, return empty map
            System.err.println("Error loading translation file: " + langFile);
            e.printStackTrace();
        }
        
        return translations;
    }
    
    @Override
    public String version() {
        return MOD_VERSION;
    }
    
    public static String getModId() {
        return MOD_ID;
    }
    
    public static String getModName() {
        return MOD_NAME;
    }
    
    public static MinecraftServer getServer() {
        return server;
    }
    
    public static final String MOD_VERSION = "1.0.0";
}