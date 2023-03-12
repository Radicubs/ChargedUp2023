package frc.robot.commands.common.placemid;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Arm;

public class ArmUp extends CommandBase {

    private final Arm arm;

    public ArmUp(Arm arm) {
        this.arm = arm;
        addRequirements(arm);
    }

    @Override
    public void initialize() {
        arm.set(0.2);
    }

    @Override
    public boolean isFinished() {
        return arm.getPosition() > 15000;
    }

    @Override
    public void end(boolean terminated) {
        arm.set(0);
    }

}
