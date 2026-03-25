package lol.sylvie.noglow;

import com.mojang.blaze3d.platform.InputConstants;
import lol.sylvie.noglow.config.ConfigHelper;
import lol.sylvie.noglow.config.ConfigInstance;
import lol.sylvie.noglow.config.ConfigScreen;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
import net.minecraft.client.KeyMapping;
import net.minecraft.resources.Identifier;
import org.lwjgl.glfw.GLFW;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NoGlow implements ClientModInitializer {
    public static Logger LOGGER = LoggerFactory.getLogger("noglow");
    public static ConfigInstance CONFIG = ConfigHelper.load();

    private static KeyMapping keyBinding;
    private static final KeyMapping.Category CATEGORY = KeyMapping.Category.register(
            Identifier.fromNamespaceAndPath("noglow", "keybinds")
    );

    @Override
    public void onInitializeClient() {
        keyBinding = KeyMappingHelper.registerKeyMapping(new KeyMapping(
                "key.noglow.toggle",
                InputConstants.Type.KEYSYM,
                -1,
                CATEGORY
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (keyBinding.consumeClick()) {
                CONFIG.toggle();
                ConfigHelper.save(CONFIG);
                if (client.player != null)
                    client.player.sendOverlayMessage(ConfigScreen.getText(CONFIG.isGlowing()));
            }
        });

        Runtime.getRuntime().addShutdownHook(new Thread(() -> ConfigHelper.save(CONFIG)));
    }
}
