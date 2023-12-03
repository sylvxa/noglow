package lgbt.sylvia.noglow;

import lgbt.sylvia.noglow.config.ConfigHelper;
import lgbt.sylvia.noglow.config.ConfigInstance;
import net.fabricmc.api.ClientModInitializer;

public class NoGlow implements ClientModInitializer {
    public static ConfigInstance CONFIG = ConfigHelper.load();

    @Override
    public void onInitializeClient() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> ConfigHelper.save(CONFIG)));
    }
}
