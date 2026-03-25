package lol.sylvie.noglow.config;

import java.awt.*;

import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;

import static lol.sylvie.noglow.NoGlow.CONFIG;

public class ConfigScreen extends Screen {
    protected Screen parent;

    public static Component getText(boolean glowing) {
        return Component.translatable("button.noglow.toggle", (glowing ? Component.translatable("button.noglow.toggle.on") : Component.translatable("button.noglow.toggle.off")));
    }

    public ConfigScreen(Screen parent) {
        super(Component.translatable("title.noglow"));
        this.parent = parent;
    }

    @Override
    protected void init() {
        int halfWidth = width / 2;
        int quarterWidth = width / 4;

        Button glowingToggleButton = Button.builder(getText(CONFIG.isGlowing()), (button) -> {
            CONFIG.toggle();
            button.setMessage(getText(CONFIG.isGlowing()));
            ConfigHelper.save(CONFIG);
        })
                .tooltip(Tooltip.create(Component.translatable("button.noglow.toggle.tooltip")))
                .width(halfWidth)
                .pos(quarterWidth, 24)
                .build();

        Button doneButton = Button.builder(CommonComponents.GUI_DONE, (button) -> this.onClose())
                .size(halfWidth, 20)
                .pos(quarterWidth, height - 24)
                .build();

        this.addRenderableWidget(doneButton);
        this.addRenderableWidget(glowingToggleButton);
    }

    @Override
    public void extractBackground(GuiGraphicsExtractor context, int mouseX, int mouseY, float a) {
        super.extractBackground(context, mouseX, mouseY, a);
        context.centeredText(this.font, this.title.copy().setStyle(Style.EMPTY.withBold(true)), this.width / 2, 8, Color.WHITE.getRGB());
    }

    @Override
    public void onClose() {
        this.minecraft.setScreen(parent);
        ConfigHelper.save(CONFIG);
    }
}
