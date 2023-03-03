package frc.robot.commands.common.ArmSetCommands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Arm;

public class SetArmLength extends CommandBase{
    private final Arm arm;
    private final double setLength;
    private final PIDController pid = new PIDController(1, 0, 0);

    public SetArmLength(Arm arm, double setLength){
        addRequirements(arm);
        this.arm = arm;
        this.setLength = setLength;
    }

    @Override
    public void initialize(){

    }

    @Override
    public void execute(){
        arm.set(pid.calculate(arm.getLength(), setLength));
    }

    @Override
    public boolean isFinished(){
        return Math.abs(arm.getLength() - setLength) < 0.1;
    }

    @Override
    public void end(boolean interrupted){
        
    }
}
