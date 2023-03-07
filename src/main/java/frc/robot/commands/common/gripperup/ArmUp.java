package frc.robot.commands.common.gripperup;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Arm;

public class ArmUp extends CommandBase {

    private final Timer timer;
    private final Arm arm;

    public ArmUp(Arm arm) {
        this.arm = arm;
        this.timer = new Timer();
        addRequirements(arm);
    }

    @Override
    public void initialize() {
        timer.start();
        arm.set(-0.2);
    }

    @Override
    public boolean isFinished() {
        return timer.get() > 1;
    }

    @Override
    public void end(boolean terminated) {
        arm.set(0);
    }
}
