package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Gripper2 extends SubsystemBase {

    private final CANSparkMax gripper;

    public Gripper2() {
        this.gripper = new CANSparkMax(16, CANSparkMaxLowLevel.MotorType.kBrushless);
        gripper.restoreFactoryDefaults();
        gripper.setIdleMode(CANSparkMax.IdleMode.kBrake);
        gripper.set(0);
    }

    public void set() {
        gripper.set(0.5);
    }
    public void unset() {
        gripper.set(0);
    }
}
