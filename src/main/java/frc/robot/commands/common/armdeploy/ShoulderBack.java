package frc.robot.commands.common.armdeploy;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.LimitSwitches;
import frc.robot.subsystems.Shoulder;

public class ShoulderBack extends CommandBase {

    private final LimitSwitches switches;
    private final Shoulder shoulder;

    public ShoulderBack(LimitSwitches switches, Shoulder shoulder) {
        this.shoulder = shoulder;
        this.switches = switches;
        addRequirements(shoulder);
    }

    @Override
    public void initialize() {
        shoulder.set(-0.25);
    }

    @Override
    public boolean isFinished() {
        return switches.shoulderButton();
    }

    @Override
    public void end(boolean interrupted) {
        shoulder.set(0);
    }
}
