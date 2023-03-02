package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.lib.util.subchooser.SettableSubsystem;
import frc.lib.util.subchooser.SubsystemChooserEnum;

public class Gripper extends SettableSubsystem {

    private final CANSparkMax gripper;
    private double setpoint;
    private double prevPos;

    public Gripper() {
        gripper = new CANSparkMax(16, CANSparkMaxLowLevel.MotorType.kBrushless);
        gripper.restoreFactoryDefaults();
        gripper.setIdleMode(CANSparkMax.IdleMode.kBrake);
        gripper.set(0);
        setpoint = 0;
        prevPos = gripper.getEncoder().getPosition();
    }

    public void set(double setpoint) {
        this.setpoint = setpoint;
    }

    @Override
    public SubsystemChooserEnum getType() {
        return SubsystemChooserEnum.GRIPPER;
    }

    @Override
    public void periodic() {
        gripper.set(setpoint / 5);
        SmartDashboard.putNumber("Gripper Temp", gripper.getMotorTemperature());
//        SmartDashboard.putNumber("gripper val", setpoint);
//        if(setpoint == 0) {
//            if(Math.abs(prevPos - gripper.getEncoder().getPosition()) > 10) {
//                prevPos = gripper.getEncoder().getPosition();
//                gripper.set(setpoint);
//            }
//        }
//
//        else gripper.set(0.1);
    }
}
