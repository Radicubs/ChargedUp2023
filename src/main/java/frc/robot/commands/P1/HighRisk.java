package frc.robot.commands.P1;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import frc.robot.commands.common.AprilTagAlign;
import frc.robot.commands.common.PathWeave;
import frc.robot.subsystems.PhotonVision;
import frc.robot.subsystems.Swerve;

public class HighRisk extends P1AutoCommand {

    // Scores game piece, goes after cube in field center, then scores it
    public HighRisk(Swerve swerve, PhotonVision vision, boolean isBlue) {
        super(swerve, isBlue);

        addCommands(
                new LowRiskPlace(swerve, vision, isBlue),
                // gamepiece pick up
                PathWeave.fromFieldCoordinates(swerve,
                        new Pose2d(new Translation2d(0.931, 0.998 * yMult), Rotation2d.fromDegrees(180))),
                new AprilTagAlign(swerve, vision, isBlue ? 8 : 1, AprilTagAlign.TagAlignment.CENTER)
                // score command
        );
    }
}
