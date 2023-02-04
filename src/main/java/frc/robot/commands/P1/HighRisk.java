package frc.robot.commands.P1;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import frc.robot.commands.common.AprilTagAlign;
import frc.robot.commands.common.PathWeave;
import frc.robot.commands.common.SequentialCommand;
import frc.robot.subsystems.PhotonVision;
import frc.robot.subsystems.Swerve;

public class HighRisk extends SequentialCommand {
    public HighRisk(Swerve swerve, PhotonVision vision, boolean isBlue) {
        super(isBlue);

        addCommands(
                new AprilTagAlign(swerve, vision, isBlue ? 7 : 2,
                        isBlue ? AprilTagAlign.TagAlignment.LEFT : AprilTagAlign.TagAlignment.RIGHT),
                // score command
                PathWeave.fromFieldCoordinates(swerve, new Pose2d(new Translation2d(5.434032, 0.913231 * yMult),
                        Rotation2d.fromDegrees(0)), new Translation2d(2.004032, 0.760531 * yMult)),
                // gamepiece pick up
                PathWeave.fromFieldCoordinates(swerve,
                        new Pose2d(new Translation2d(2.004032, 0.760531 * yMult), Rotation2d.fromDegrees(180))),
                new AprilTagAlign(swerve, vision, isBlue ? 7 : 2, AprilTagAlign.TagAlignment.CENTER)
        );
    }
}
