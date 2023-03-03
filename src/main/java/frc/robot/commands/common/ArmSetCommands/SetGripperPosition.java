package frc.robot.commands.common.ArmSetCommands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Gripper;

public class SetGripperPosition extends CommandBase{
    private final Gripper gripper;
    private final double setPosition;
    private final PIDController pid = new PIDController(1, 0, 0);

    public SetGripperPosition(Gripper gripper, double setPosition){
        addRequirements(gripper);
        this.gripper = gripper;
        this.setPosition = setPosition;
    }

    @Override
    public void initialize(){

    }

    @Override
    public void execute(){
        gripper.set(pid.calculate(gripper.getPosition(), setPosition));
    }

    @Override
    public boolean isFinished(){
        return Math.abs(gripper.getPosition() - setPosition) < 0.1;
    }

    @Override
    public void end(boolean interrupted){
        
    }
}
