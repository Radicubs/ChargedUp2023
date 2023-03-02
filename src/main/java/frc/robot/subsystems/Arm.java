package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.lib.util.SettableSubsystem;
import frc.lib.util.SubsystemChooserEnum;

public class Arm extends SettableSubsystem {

    private final WPI_TalonFX arm;
    private boolean clamped;
    private double offset = 0;
    private double prevPos;
    private double setpoint;

    public Arm() {
        arm = new WPI_TalonFX(13);
        arm.configFactoryDefault();
        arm.setNeutralMode(NeutralMode.Brake);
        prevPos = arm.getSelectedSensorPosition();
    }

    public void set(double setpoint) {
        this.setpoint = setpoint;
    }

    public void setOffset(double offset){
        this.offset = offset;
    }

    public double getLength(){
        return arm.getSelectedSensorPosition() - offset;
    }

    @Override
    public SubsystemChooserEnum getType() {
        return SubsystemChooserEnum.ARM;
    }

    @Override
    public void periodic() {
        if(setpoint == 0) {
            if(Math.abs(prevPos - arm.getSelectedSensorPosition()) > 10) {
                prevPos = arm.getSelectedSensorPosition();
                arm.set(ControlMode.PercentOutput, 0.1);
            }
        }

        else arm.set(ControlMode.PercentOutput, setpoint);
    }

}
