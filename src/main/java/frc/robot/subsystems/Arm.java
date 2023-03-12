package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.lib.util.subchooser.SettableSubsystem;
import frc.lib.util.subchooser.SubsystemChooserEnum;

public class Arm extends SettableSubsystem {

    private final WPI_TalonFX arm;
    private double offset = 0;
    private double setpoint;

    public Arm() {
        arm = new WPI_TalonFX(13);
        arm.configFactoryDefault();
        arm.setNeutralMode(NeutralMode.Brake);
        resetEnc();
    }

    public void set(double setpoint) {
        this.setpoint = setpoint;
    }

    public void resetEnc() {
        offset = arm.getSelectedSensorPosition();
    }

    public double getPosition() {
        return arm.getSelectedSensorPosition() - offset;
    }

    @Override
    public SubsystemChooserEnum getType() {
        return SubsystemChooserEnum.ARM;
    }

    @Override
    public void periodic() {
        arm.set(ControlMode.PercentOutput, setpoint);
        SmartDashboard.putNumber("Arm Temp", arm.getTemperature());
        SmartDashboard.putNumber("armval", getPosition());
    }

}
