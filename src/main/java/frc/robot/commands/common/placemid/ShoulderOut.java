package frc.robot.commands.common.placemid;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Shoulder;

public class ShoulderOut extends CommandBase {

    private final Shoulder shoulder;

    public ShoulderOut(Shoulder shoulder) {
        this.shoulder = shoulder;
        addRequirements(shoulder);
    }

    @Override
    public void initialize() {
        shoulder.set(-0.4);
    }

    @Override
    public boolean isFinished() {
        return shoulder.getPosition() < -28.8;
    }

    @Override
    public void end(boolean terminated) {
        shoulder.set(0);
    }
}
