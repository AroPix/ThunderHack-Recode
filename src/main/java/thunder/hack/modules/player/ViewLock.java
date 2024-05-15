package thunder.hack.modules.player;

import net.minecraft.client.util.math.MatrixStack;
import thunder.hack.modules.Module;
import thunder.hack.setting.Setting;

public class ViewLock extends Module {
    public ViewLock() {
        super("ViewLock", Category.PLAYER);
    }

    private final Setting<Boolean> lockCurrent = new Setting<>("LockCurrent", true);
    private final Setting<Boolean> pitch = new Setting<>("Pitch", true);
    private final Setting<Float> pitchValue = new Setting<>("PitchValue", 0f, -90f, 90f, v -> pitch.getValue());
    private final Setting<Boolean> yaw = new Setting<>("Yaw", true);
    private final Setting<Float> yawValue = new Setting<>("YawValue", 0f, -180f, 180f, v -> pitch.getValue());

    @Override
    public void onEnable() {
        if(lockCurrent.getValue()) {
            yawValue.setValue(mc.player.getYaw());
            pitchValue.setValue(mc.player.getPitch());
        }
    }

    public void onRender3D(MatrixStack m) {
        if (pitch.getValue()) mc.player.setPitch(pitchValue.getValue());
        if (yaw.getValue()) mc.player.setYaw(yawValue.getValue());
    }
}
