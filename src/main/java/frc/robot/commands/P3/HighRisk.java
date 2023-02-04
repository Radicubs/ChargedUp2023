package frc.robot.commands.P3;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import frc.robot.commands.P3.P3AutoCommand;
import frc.robot.commands.common.AprilTagAlign;
import frc.robot.commands.common.PathWeave;
import frc.robot.subsystems.PhotonVision;
import frc.robot.subsystems.Swerve;

public class HighRisk extends P3AutoCommand {

    // Scores game piece, goes after cube in field center, then scores it
    public HighRisk(Swerve swerve, PhotonVision vision, boolean isBlue) {
        super(swerve, isBlue);
        //DONE!! - (i tink)
        addCommands(
                new AprilTagAlign(swerve, vision, isBlue ? 6 : 3,
                        isBlue ? AprilTagAlign.TagAlignment.LEFT : AprilTagAlign.TagAlignment.RIGHT),
                // score command
                PathWeave.fromFieldCoordinates(swerve, new Pose2d(new Translation2d(5.434032, 0.913231 * yMult),
                        Rotation2d.fromDegrees(0)),  new Translation2d(.842, 1.008 * yMult), new Translation2d(2.004032, 0.760531 * yMult)),
                // gamepiece pick up
                PathWeave.fromFieldCoordinates(swerve,
                        new Pose2d(new Translation2d(2.004032, 0.760531 * yMult), Rotation2d.fromDegrees(180))),
                new AprilTagAlign(swerve, vision, isBlue ? 6 : 3, AprilTagAlign.TagAlignment.CENTER)
        );
    }
}