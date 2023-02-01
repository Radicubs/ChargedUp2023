package frc.robot.commands.P0;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.PathWeave;
import frc.robot.subsystems.Swerve;

import java.nio.file.Path;
import java.util.List;

public class NoRisk extends SequentialCommandGroup {

    public NoRisk(Swerve swerve) {
        addCommands(new PathWeave(swerve, List.of(), new Pose2d(-1, 0, new Rotation2d())));
    }

}
