package frc.robot.commands.P2;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.commands.common.SequentialCommand;
import frc.robot.subsystems.Swerve;

public abstract class P2AutoCommand extends SequentialCommand {

    // Start 26.3 inches from the tape, and 5.85 inches from the left of the charging station
    private final Pose2d startingPos = new Pose2d(new Translation2d(1.042703, 3.420531 * yMult), new Rotation2d(180));

    public P2AutoCommand(Swerve swerve, boolean isBlue) {
        super(isBlue);
        addCommands(new InstantCommand(() -> swerve.resetOdo(startingPos)));
    }
}
