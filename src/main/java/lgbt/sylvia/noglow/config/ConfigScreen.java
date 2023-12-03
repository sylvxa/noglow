package lgbt.sylvia.noglow.config;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.util.Window;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;

import java.awt.*;

import static lgbt.sylvia.noglow.NoGlow.CONFIG;

public class ConfigScreen extends Screen {
    ButtonWidget glowingToggleButton;
    ButtonWidget doneButton;
    Screen previous;

    private Text getText(boolean glowing) {
        return Text.of("Glowing: §l" + (glowing ? "§aENABLED" : "§cDISABLED"));
    }

    public ConfigScreen(Screen screen) {
        super(Text.of("NoGlow configuration screen"));
        this.client = MinecraftClient.getInstance();
        if (this.client != null) {
            Window window = this.client.getWindow();
            this.width = window.getScaledWidth();
            this.height = window.getScaledHeight();
        }

        this.glowingToggleButton = ButtonWidget.builder(getText(CONFIG.isGlowing()), (button) -> {
            CONFIG.setGlowing(!CONFIG.isGlowing());
            button.setMessage(getText(CONFIG.isGlowing()));
            ConfigHelper.save(CONFIG);
        }).position(8, 24).build();

        this.doneButton = ButtonWidget.builder(ScreenTexts.DONE, (button) -> this.close()).size(width / 2, 16).build();

        this.previous = screen;
        this.updatePositions();
        this.addDrawableChild(doneButton);
        this.addDrawableChild(glowingToggleButton);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);

        context.drawCenteredTextWithShadow(this.textRenderer, Text.of("§lNoGlow"), this.width / 2, 8, Color.WHITE.getRGB());
    }

    private void updatePositions() {
        Window window = MinecraftClient.getInstance().getWindow();
        this.width = window.getScaledWidth();
        this.height = window.getScaledHeight();

        glowingToggleButton.setWidth(width - 16);
        doneButton.setWidth(width / 2);
        doneButton.setY(height - 24);
        doneButton.setX(width / 4);
    }

    @Override
    public void close() {
        if (this.client == null) return;
        this.client.setScreen(previous);
        ConfigHelper.save(CONFIG);
    }

    @Override
    public void resize(MinecraftClient client, int width, int height) {
        this.updatePositions();
    }
}
