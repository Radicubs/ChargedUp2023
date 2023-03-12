package frc.robot.commands.common.placemid;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Shoulder;

public class ExtendAndPlace extends ParallelDeadlineGroup {

    public ExtendAndPlace(Shoulder shoulder, Arm arm) {
        super(
                new CommandBase() {
                    @Override
                    public void initialize() {
                        arm.set(-0.2);
                    }

                    @Override
                    public void end(boolean interrupted) {
                        arm.set(0);
                    }

                    @Override
                    public boolean isFinished() {
                        return arm.getPosition() < -65000;
                    }
                },

                new CommandBase() {
                    @Override
                    public void initialize() {
                        shoulder.set(-0.07);
                    }

                    @Override
                    public void end(boolean interrupted) {
                        shoulder.set(0);
                    }
                }
        );
    }

}
