package frc.robot.subsystems;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.lib.util.subchooser.SubsystemChooserEnum;

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
        sub = switch(pov.getAsInt()) {
            case 0 -> SubsystemChooserEnum.ARM;
            case 90 -> SubsystemChooserEnum.GRIPPER;
            default -> SubsystemChooserEnum.SHOULDER;
        };

        SmartDashboard.putString("Selected Subsystem", sub.toString());
    }

}

