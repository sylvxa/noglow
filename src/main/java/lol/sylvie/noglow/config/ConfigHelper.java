package lol.sylvie.noglow.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import lol.sylvie.noglow.NoGlow;
import net.fabricmc.loader.api.FabricLoader;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class ConfigHelper {
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private static final String CONFIG_NAME = "noglow.json";


    public static void save(ConfigInstance config) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(config);

        Path configDir = FabricLoader.getInstance().getConfigDir();
        Path file = Path.of(configDir.toString(), CONFIG_NAME);
        try (FileWriter writer = new FileWriter(file.toFile())) {
            writer.write(json);
        } catch (IOException ignored) {}

    }

    public static ConfigInstance load() {
        Gson gson = new Gson();
        Path configDir = FabricLoader.getInstance().getConfigDir();
        Path file = Path.of(configDir.toString(), CONFIG_NAME);
        if (Files.exists(file)) {
            try {
                return gson.fromJson(Files.readString(file), ConfigInstance.class);
            } catch (IOException ignored) {}
            catch (IllegalStateException | JsonSyntaxException exception) {
                NoGlow.LOGGER.error("Could not read NoGlow configuration file, continuing without it.", exception);
            }
        }
        return new ConfigInstance(false);
    }
}
