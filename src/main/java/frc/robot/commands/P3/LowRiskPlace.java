package frc.robot.commands.P3;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import frc.robot.commands.P3.P3AutoCommand;
import frc.robot.commands.common.AprilTagAlign;
import frc.robot.commands.common.PathWeave;
import frc.robot.subsystems.PhotonVision;
import frc.robot.subsystems.Swerve;

public class LowRiskPlace extends P3AutoCommand {

    // Routine that scores preloaded game piece, then exits the community for mobility points
    public LowRiskPlace(Swerve swerve, PhotonVision vision, boolean isBlue) {
        super(swerve, isBlue);
        //TODO
        addCommands(new AprilTagAlign(swerve, vision, isBlue ? 6 : 3,
                isBlue ? AprilTagAlign.TagAlignment.LEFT : AprilTagAlign.TagAlignment.RIGHT));
        // addCommands(placement command);
        addCommands(PathWeave.fromFieldCoordinates(swerve,
                new Pose2d(new Translation2d(5.6896, 2.766 * yMult), new Rotation2d()), new Translation2d(5.6896, 2.766 * yMult)));
    }

}
