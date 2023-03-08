package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.lib.util.subchooser.SettableSubsystem;
import frc.lib.util.subchooser.SubsystemChooserEnum;

public class Shoulder extends SettableSubsystem {
    private final CANSparkMax shoulder;
    private final CANSparkMax shoulderRight;
    private double setpoint;
    private double prevPos;
    private double offset = 0;

    public Shoulder() {
        shoulder = new CANSparkMax(14, CANSparkMaxLowLevel.MotorType.kBrushless);
        shoulderRight = new CANSparkMax(15, CANSparkMaxLowLevel.MotorType.kBrushless);
        shoulder.restoreFactoryDefaults();
        shoulderRight.restoreFactoryDefaults();
        shoulder.setIdleMode(CANSparkMax.IdleMode.kBrake);
        shoulderRight.setIdleMode(CANSparkMax.IdleMode.kBrake);
        shoulder.set(0);
        shoulder.setInverted(false);
        shoulderRight.set(0);
        shoulderRight.setInverted(true);
        setpoint = 0;
        prevPos = shoulder.getEncoder().getPosition();
    }

    @Override
    public void periodic() {
        SmartDashboard.putNumber("ShoulderL Temp", shoulder.getMotorTemperature());
        SmartDashboard.putNumber("ShoulderR Temp", shoulderRight.getMotorTemperature());

        if(setpoint == 0) {
            shoulder.set(0);
        }

        else {
            shoulder.set(setpoint / 4);
            shoulderRight.set(setpoint / 4);
        }
    }

    public double getAngle(){
        return shoulderRight.getEncoder().getPosition() - offset;
    }

    public void set(double setpoint) {
        this.setpoint = setpoint;
    }

    public SubsystemChooserEnum getType() {
        return SubsystemChooserEnum.SHOULDER;
    }


}
