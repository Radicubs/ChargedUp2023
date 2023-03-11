package frc.robot.commands.teleop;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Gripper2;

import java.util.function.BooleanSupplier;

public class Gripper2Control extends CommandBase {

    private final Gripper2 gripper;
    private final BooleanSupplier bool;

    public Gripper2Control(Gripper2 gripper, BooleanSupplier bool) {
        this.gripper = gripper;
        this.bool = bool;
        addRequirements(gripper);
    }

    @Override
    public void initialize() {
        gripper.unset();
    }

    @Override
    public void execute() {
        if(bool.getAsBoolean()) gripper.set();
        else gripper.unset();
    }

    @Override
    public void end(boolean terminated) {
        gripper.unset();
    }

}
