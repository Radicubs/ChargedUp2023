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
            System.out.println("zeroed");
//            if(Math.abs(prevPos - ((shoulder.getEncoder().getPosition() + shoulderRight.getEncoder().getPosition()) / 2)) > .15) {
                System.out.println("moving");

                prevPos = (shoulder.getEncoder().getPosition() + shoulderRight.getEncoder().getPosition()) / 2;
//                shoulder.set(-0.05);
//                shoulderRight.set(-0.05);
//            }

//            else {
//                shoulder.set(0);
//                shoulderRight.set(0);
//            }
        }

        else {
            shoulder.set(setpoint / 4);
            shoulderRight.set(setpoint / 4);
        }
//        if(setpoint == 0) {
//            if(Math.abs(prevPos - ((shoulder.getEncoder().getPosition() - shoulderRight.getEncoder().getPosition()) / 2)) > 10) {
//                prevPos = (shoulder.getEncoder().getPosition() - shoulderRight.getEncoder().getPosition()) / 2;
//                shoulder.set(0.1);
//                shoulderRight.set(0.1);
//            }
//        }
//
//        else {
//            shoulder.set(setpoint);
//            shoulderRight.set(setpoint);
//        }
    }

    public void set(double setpoint) {
        this.setpoint = setpoint;
    }

    public SubsystemChooserEnum getType() {
        return SubsystemChooserEnum.SHOULDER;
    }


}
