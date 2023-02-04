package frc.robot.commands.P0;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.commands.common.SequentialCommand;
import frc.robot.subsystems.Swerve;

public abstract class P0AutoCommand extends SequentialCommand {
    private static final Pose2d startingPos = new Pose2d();

    public P0AutoCommand(Swerve swerve, boolean isBlue) {
        super(isBlue);
        addCommands(new InstantCommand(() -> swerve.resetOdo(startingPos)));
    }
}
