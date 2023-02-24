package frc.robot.commands.P2;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import frc.robot.commands.P0.LowRiskPlace;
import frc.robot.commands.P2.P2AutoCommand;
import frc.robot.commands.common.AprilTagAlign;
import frc.robot.commands.common.PathWeave;
import frc.robot.subsystems.PhotonVision;
import frc.robot.subsystems.Swerve;

public class HighRisk extends P2AutoCommand {

    // Scores game piece, goes after cube in field center, then scores it
    public HighRisk(Swerve swerve, PhotonVision vision, boolean isBlue) {
        super(swerve, isBlue);
        addCommands(new AprilTagAlign(swerve, vision, isBlue ? 7 : 2,
                isBlue ? AprilTagAlign.TagAlignment.LEFT : AprilTagAlign.TagAlignment.RIGHT),
                //add placement command
                PathWeave.fromRelativeCoordinates(swerve, new Pose2d(5.1667, 0, Rotation2d.fromDegrees(0))),
                //add intake object command
                PathWeave.fromRelativeCoordinates(swerve, new Pose2d(-5.1667, 0, Rotation2d.fromDegrees(0))), new AprilTagAlign(swerve, vision, isBlue ? 7 : 2,
                    isBlue ? AprilTagAlign.TagAlignment.LEFT : AprilTagAlign.TagAlignment.RIGHT));
                //add placement command
    }
}
