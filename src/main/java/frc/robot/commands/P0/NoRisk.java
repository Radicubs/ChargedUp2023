package frc.robot.commands.P0;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.common.PathWeave;
import frc.robot.subsystems.Swerve;

import java.util.List;

public class NoRisk extends SequentialCommandGroup {

    // Routine that simply leaves the community and maintains heading
    public NoRisk(Swerve swerve) {
        addCommands(PathWeave.fromRelativeCoordinates(swerve, new Pose2d(-1, 0, new Rotation2d(180))));
    }

}
