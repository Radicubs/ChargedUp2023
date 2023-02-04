package frc.robot.commands.P2;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.common.PathWeave;
import frc.robot.subsystems.Swerve;

public class NoRisk extends P2AutoCommand {

    // Routine that simply leaves the community and maintains heading
    public NoRisk(Swerve swerve, boolean isBlue) {
        //TODO
        super(swerve, isBlue);
        addCommands(PathWeave.fromRelativeCoordinates(swerve, new Pose2d(-1, 0, new Rotation2d(180))));
    }

}