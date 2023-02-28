package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.SparkMaxAbsoluteEncoder;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.lib.util.SubsystemChooserProvider;

import java.util.function.DoubleSupplier;

public class Shoulder extends SubsystemBase {
    private final CANSparkMax shoulder;
    private final CANSparkMax shoulderRight;
    private final DoubleSupplier left;
    private final DoubleSupplier right;
    private final SubsystemChooserProvider chooser;

    public Shoulder(DoubleSupplier left, DoubleSupplier right, SubsystemChooserProvider chooser) {
        this.chooser = chooser;
        shoulder = new CANSparkMax(14, CANSparkMaxLowLevel.MotorType.kBrushless);
        shoulderRight = new CANSparkMax(15, CANSparkMaxLowLevel.MotorType.kBrushless);
        shoulder.restoreFactoryDefaults();
        shoulderRight.restoreFactoryDefaults();
        shoulder.setIdleMode(CANSparkMax.IdleMode.kBrake);
        shoulderRight.setIdleMode(CANSparkMax.IdleMode.kBrake);
        shoulder.set(0);
        shoulderRight.set(0);
        shoulderRight.setInverted(true);
        this.left = left;
        this.right = right;
    }

    @Override
    public void periodic() {
        if(chooser.getAsChooser() == SubsystemChooser.SubsystemChooserEnum.SHOULDER) {
            double val = left.getAsDouble() - right.getAsDouble();
            shoulder.set(val);
            shoulderRight.set(val / 2);
            SmartDashboard.putNumber("shoulder", val);
            SmartDashboard.putNumber("shoulderTemp", shoulder.getMotorTemperature());
            SmartDashboard.putNumber("shoulderTemp2", shoulderRight.getMotorTemperature());
        }

        else {
            shoulder.set(0);
            shoulderRight.set(0);
            SmartDashboard.putNumber("shoulder", 0);
        }

        SmartDashboard.putNumber("motor slow", shoulder.getEncoder().getVelocity());
        SmartDashboard.putNumber("motor right", shoulderRight.getEncoder().getVelocity());
    }


}
