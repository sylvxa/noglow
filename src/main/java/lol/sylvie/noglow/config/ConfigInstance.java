package lol.sylvie.noglow.config;

public class ConfigInstance {
    private boolean glowing;

    public ConfigInstance(boolean glowing) {
        this.glowing = glowing;
    }

    public boolean isGlowing() {
        return this.glowing;
    }

    public void toggle() {
        this.glowing = !this.glowing;
    }
}
