package lgbt.sylvia.noglow;

import lgbt.sylvia.noglow.config.ConfigHelper;
import lgbt.sylvia.noglow.config.ConfigInstance;
import net.fabricmc.api.ClientModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NoGlow implements ClientModInitializer {
    public static Logger LOGGER = LoggerFactory.getLogger("NoGlow");
    public static ConfigInstance CONFIG = ConfigHelper.load();

    @Override
    public void onInitializeClient() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> ConfigHelper.save(CONFIG)));
    }
}
