package frc.robot.subsystems;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import java.util.function.IntSupplier;

public class SubsystemChooser extends SubsystemBase {
    private SubsystemChooserEnum sub;
    private final IntSupplier pov;

    public SubsystemChooser(IntSupplier pov) {
        this.pov = pov;
        sub = SubsystemChooserEnum.SHOULDER;
    }

    public SubsystemChooserEnum getSub() {
        return sub;
    }

    @Override
    public void periodic() {
        switch (pov.getAsInt()) {
            case 270 -> {
                sub = SubsystemChooserEnum.SHOULDER;
                SmartDashboard.putString("Selected Subsystem", "Shoulder");
            }

            case 0 -> {
                sub = SubsystemChooserEnum.ARM;
                SmartDashboard.putString("Selected Subsystem", "Arm");
            }

            case 90 -> {
                sub = SubsystemChooserEnum.GRIPPER;
                SmartDashboard.putString("Selected Subsystem", "Gripper");
            }

            default -> {
            }
        }
    }

    public enum SubsystemChooserEnum {
        SHOULDER, ARM, GRIPPER
    }
}

