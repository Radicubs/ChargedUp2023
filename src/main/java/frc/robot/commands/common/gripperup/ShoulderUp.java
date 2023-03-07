package frc.robot.commands.common.gripperup;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Shoulder;

public class ShoulderUp extends CommandBase {

    private final Timer timer;
    private final Shoulder shoulder;

    public ShoulderUp(Shoulder shoulder) {
        this.shoulder = shoulder;
        timer = new Timer();
        addRequirements(shoulder);
    }

    @Override
    public void initialize() {
        timer.start();
        shoulder.set(-0.4);
    }

    @Override
    public boolean isFinished() {
        return timer.get() > 1;
    }

    @Override
    public void end(boolean terminated) {
        shoulder.set(0);
    }
}
