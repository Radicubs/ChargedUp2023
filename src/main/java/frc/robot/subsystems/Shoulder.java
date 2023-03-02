package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.lib.util.SettableSubsystem;
import frc.lib.util.SubsystemChooserEnum;
import frc.lib.util.SubsystemChooserSupplier;

import java.util.function.DoubleSupplier;

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
        shoulderRight.setInverted(false);
        setpoint = 0;
        prevPos = shoulder.getEncoder().getPosition();
    }

    @Override
    public void periodic() {
        if(setpoint == 0) {
            if(Math.abs(prevPos - ((shoulder.getEncoder().getPosition() - shoulderRight.getEncoder().getPosition()) / 2)) > 10) {
                prevPos = (shoulder.getEncoder().getPosition() - shoulderRight.getEncoder().getPosition()) / 2;
                shoulder.set(0.1);
                shoulderRight.set(0.1);
            }
        }

        else {
            shoulder.set(setpoint);
            shoulderRight.set(setpoint);
        }
    }

    public void setOffset(double offset){
        this.offset = offset;
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
