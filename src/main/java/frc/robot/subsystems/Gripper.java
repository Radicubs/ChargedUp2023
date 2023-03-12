package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.lib.util.subchooser.SettableSubsystem;
import frc.lib.util.subchooser.SubsystemChooserEnum;

public class Gripper extends SettableSubsystem {

    private final CANSparkMax gripper;
    private double setpoint;
    private double offset;

    public Gripper() {
        gripper = new CANSparkMax(16, CANSparkMaxLowLevel.MotorType.kBrushless);
        gripper.restoreFactoryDefaults();
        gripper.setIdleMode(CANSparkMax.IdleMode.kBrake);
        gripper.set(0);
        setpoint = 0;
        resetEnc();
    }

    public void set(double setpoint) {
        this.setpoint = setpoint;
    }

    public double getPosition(){
        return gripper.getEncoder().getPosition() - offset;
    }

    public void resetEnc() {
        offset = gripper.getEncoder().getPosition();
    }

    @Override
    public SubsystemChooserEnum getType() {
        return SubsystemChooserEnum.GRIPPER;
    }

    @Override
    public void periodic() {
        gripper.set(setpoint / 5);
        SmartDashboard.putNumber("Gripper Temp", gripper.getMotorTemperature());
    }
}
