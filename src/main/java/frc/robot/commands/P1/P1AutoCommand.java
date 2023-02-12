package frc.robot.commands.P1;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.commands.common.SequentialCommand;
import frc.robot.subsystems.Swerve;

public abstract class P1AutoCommand extends SequentialCommand {
    private final Pose2d startingPos = new Pose2d(new Translation2d(0.860, 1.055 * yMult), Rotation2d.fromDegrees(180));

    // Start 26.8 inches from the edge of the field and 16.6 inches from the tape
    public P1AutoCommand(Swerve swerve, boolean isBlue) {
        super(isBlue);
        addCommands(new InstantCommand(() -> swerve.resetOdo(startingPos)));
    }
}

