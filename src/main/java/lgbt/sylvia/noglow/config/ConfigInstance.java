package lgbt.sylvia.noglow.config;

public class ConfigInstance {
    private boolean glowing;

    public ConfigInstance(boolean glowing) {
        this.glowing = glowing;
    }

    public void setGlowing(boolean glowing) {
        this.glowing = glowing;
    }

    public boolean isGlowing() {
        return this.glowing;
    }
}
