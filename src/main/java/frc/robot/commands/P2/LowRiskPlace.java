package frc.robot.commands.P2;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import frc.robot.commands.common.AprilTagAlign;
import frc.robot.commands.common.PathWeave;
import frc.robot.subsystems.PhotonVision;
import frc.robot.subsystems.Swerve;

public class LowRiskPlace extends P2AutoCommand {

    // Routine that scores preloaded game piece, then exits the community for mobility points
    public LowRiskPlace(Swerve swerve, PhotonVision vision, boolean isBlue) {
        super(swerve, isBlue);
        addCommands(new AprilTagAlign(swerve, vision, isBlue ? 7 : 2,
                isBlue ? AprilTagAlign.TagAlignment.LEFT : AprilTagAlign.TagAlignment.RIGHT),
                PathWeave.fromFieldCoordinates(swerve,
                        new Pose2d(new Translation2d(5.232, 3.321 * yMult), Rotation2d.fromDegrees(0)),
                        new Translation2d(0.870, 4.401 * yMult), new Translation2d(3.788, 4.490 * yMult)));
    }

}
