package frc.robot.commands.P0;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import frc.robot.commands.common.AprilTagAlign;
import frc.robot.commands.common.PathWeave;
import frc.robot.commands.common.SequentialCommand;
import frc.robot.subsystems.PhotonVision;
import frc.robot.subsystems.Swerve;

import java.util.List;

public class LowRiskPlace extends SequentialCommand {

    // Routine that scores preloaded game piece, then exits the community for mobility points
    public LowRiskPlace(Swerve swerve, PhotonVision vision, boolean isBlue) {
        super(isBlue);
        addCommands(new AprilTagAlign(swerve, vision, isBlue ? 8 : 1,
                isBlue ? AprilTagAlign.TagAlignment.LEFT : AprilTagAlign.TagAlignment.RIGHT));
        // addCommands(placement command);
        addCommands(PathWeave.fromFieldCoordinates(swerve,
                new Pose2d(new Translation2d(5.6896, 0.849 * yMult), new Rotation2d()), new Translation2d(5.6896, 0.849 * yMult)));
    }

}