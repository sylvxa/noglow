package lol.sylvie.noglow.config;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.option.OptionsScreen;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.util.Window;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Style;
import net.minecraft.text.Text;

import java.awt.*;

import static lol.sylvie.noglow.NoGlow.CONFIG;

public class ConfigScreen extends Screen {
    ButtonWidget glowingToggleButton;
    ButtonWidget doneButton;
    Screen previous;

    private Text getText(boolean glowing) {
        return Text.translatable("button.noglow.toggle", (glowing ? Text.translatable("button.noglow.toggle.on") : Text.translatable("button.noglow.toggle.off")));
    }

    public ConfigScreen(Screen screen) {
        super(Text.translatable("title.noglow"));
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
        }).tooltip(Tooltip.of(Text.translatable("button.noglow.toggle.tooltip"))).position(8, 24).build();
        this.doneButton = ButtonWidget.builder(ScreenTexts.DONE, (button) -> this.close()).size(0, 20).build();

        this.previous = screen;
        this.updatePositions();
        this.addDrawableChild(doneButton);
        this.addDrawableChild(glowingToggleButton);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);

        context.drawCenteredTextWithShadow(this.textRenderer, this.title.copy().setStyle(Style.EMPTY.withBold(true)), this.width / 2, 8, Color.WHITE.getRGB());
    }

    private void updatePositions() {
        Window window = MinecraftClient.getInstance().getWindow();
        this.width = window.getScaledWidth();
        this.height = window.getScaledHeight();

        int halfWidth = width / 2;
        int quarterWidth = width / 4;

        glowingToggleButton.setWidth(halfWidth);
        glowingToggleButton.setX(quarterWidth);

        doneButton.setWidth(halfWidth);
        doneButton.setX(quarterWidth);
        doneButton.setY(height - 24);
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
