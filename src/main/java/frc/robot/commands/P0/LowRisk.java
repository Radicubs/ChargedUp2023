package frc.robot.commands.P0;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.AprilTagAlign;
import frc.robot.commands.PathWeave;
import frc.robot.subsystems.PhotonVision;
import frc.robot.subsystems.Swerve;

import java.util.List;

public class LowRisk extends SequentialCommandGroup {

    // Routine that scores preloaded game piece, then exits the community for mobility points
    public LowRisk(Swerve swerve, PhotonVision vision, boolean isBlue, AprilTagAlign.TagAlignment alignment) {
        addCommands(new AprilTagAlign(vision, swerve, isBlue ? 8 : 1, alignment));
        // addCommands(placement command);
        addCommands(new PathWeave(swerve, new Pose2d(new Translation2d(5.6896, 0.92073984), new Rotation2d()),
                List.of(new Translation2d(0.25, 0.92073984))));
    }

}
