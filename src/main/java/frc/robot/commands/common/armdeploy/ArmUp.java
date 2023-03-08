package frc.robot.commands.common.armdeploy;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.LimitSwitches;

public class ArmUp extends CommandBase {

    private final Arm arm;
    private final LimitSwitches switches;

    public ArmUp(LimitSwitches switches, Arm arm) {
        this.arm = arm;
        this.switches = switches;
        addRequirements(arm);
    }

    @Override
    public void initialize() {
        arm.set(-0.25);
    }

    @Override
    public boolean isFinished() {
        return switches.armExtensionButton();
    }

    @Override
    public void end(boolean interrupted) {
        arm.set(0);
    }



}