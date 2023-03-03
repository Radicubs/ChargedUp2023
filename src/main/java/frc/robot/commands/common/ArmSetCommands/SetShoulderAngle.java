package frc.robot.commands.common.ArmSetCommands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Shoulder;

public class SetShoulderAngle extends CommandBase {
    private final Shoulder shoulder;
    private final double setAngle;
    private final PIDController pid = new PIDController(1, 0, 0);

    public SetShoulderAngle(Shoulder shoulder, double setAngle){
        addRequirements(shoulder);
        this.shoulder = shoulder;
        this.setAngle = setAngle;
    }

    @Override
    public void initialize(){

    }

    @Override
    public void execute(){
        shoulder.set(pid.calculate(shoulder.getAngle(), setAngle));
    }

    @Override
    public boolean isFinished(){
        return Math.abs(shoulder.getAngle() - setAngle) < 0.1;
    }

    @Override
    public void end(boolean interrupted){

    }
}
