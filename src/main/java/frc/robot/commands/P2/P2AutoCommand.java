package frc.robot.commands.P2;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.commands.common.SequentialCommand;
import frc.robot.subsystems.Swerve;

public abstract class P2AutoCommand extends SequentialCommand {

    // Start 16.6 inches from the tape, and 43.75 inches from the left of the charging station
    private final Pose2d startingPos = new Pose2d(new Translation2d(0.860, 2.817 * yMult), new Rotation2d(180));

    public P2AutoCommand(Swerve swerve, boolean isBlue) {
        super(isBlue);
        addCommands(new InstantCommand(() -> swerve.resetOdo(startingPos)));
    }
}
