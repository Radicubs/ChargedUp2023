package frc.robot.commands.P0;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.commands.common.SequentialCommand;
import frc.robot.subsystems.Swerve;

public abstract class P0AutoCommand extends SequentialCommand {

    // Robot should be 26.8 inches from the edge of the field, and 22.4 inches from the boundary of the community
    private final Pose2d startingPos = new Pose2d(new Translation2d(2.456, 1.055 * yMult), Rotation2d.fromDegrees(180));

    public P0AutoCommand(Swerve swerve, boolean isBlue) {
        super(isBlue);
        addCommands(new InstantCommand(() -> swerve.resetOdo(startingPos)));
    }
}
