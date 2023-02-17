package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import edu.wpi.first.wpilibj.simulation.DoubleSolenoidSim;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import java.util.function.DoubleSupplier;

public class Gripper extends SubsystemBase {

    private final WPI_TalonFX claw;
    private boolean clamped;
    private final DoubleSupplier leftTrigger;
    private final DoubleSupplier rightTrigger;

    public Gripper(DoubleSupplier leftTrigger, DoubleSupplier rightTrigger) {
        claw = new WPI_TalonFX(13);
        claw.configFactoryDefault();
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
        SmartDashboard.putBoolean("Clamped", clamped);
        double value = leftTrigger.getAsDouble() - rightTrigger.getAsDouble();
        claw.set(ControlMode.PercentOutput, value);
    }

}
