package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.lib.util.SubsystemChooserProvider;

import java.util.function.DoubleSupplier;

public class Arm extends SubsystemBase {

    private final WPI_TalonFX claw;
    private boolean clamped;
    private final DoubleSupplier leftTrigger;
    private final DoubleSupplier rightTrigger;
    private final SubsystemChooserProvider chooser;

    public Arm(DoubleSupplier leftTrigger, DoubleSupplier rightTrigger, SubsystemChooserProvider chooser) {
        this.chooser = chooser;
        claw = new WPI_TalonFX(13);
        claw.configFactoryDefault();
        claw.setNeutralMode(NeutralMode.Brake);
        this.leftTrigger = leftTrigger;
        this.rightTrigger = rightTrigger;
        unclamp();
    }

    public void clamp() {
        clamped = true;
    }

    public void unclamp() {
        clamped = false;
    }

    @Override
    public void periodic() {
        if(chooser.getAsChooser() == SubsystemChooser.SubsystemChooserEnum.ARM) {
            SmartDashboard.putBoolean("Clamped", clamped);
            double value = leftTrigger.getAsDouble() - rightTrigger.getAsDouble();
            claw.set(ControlMode.PercentOutput, value);
        }

        else {
            claw.set(ControlMode.PercentOutput, 0);
        }
    }

}
