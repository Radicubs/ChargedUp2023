package frc.robot.commands.P0;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.ChargeStationAlign;
import frc.robot.commands.PathWeave;
import frc.robot.subsystems.Swerve;

import java.util.List;
import java.util.function.DoubleSupplier;

public class ChargeStation extends SequentialCommandGroup {

    public ChargeStation(Swerve swerve, DoubleSupplier roll) {
        addCommands(
                PathWeave.fromFieldCoordinates(swerve, List.of(new Translation2d(5.907849797, 2.44473984)),
                        new Pose2d(new Translation2d(5.907849797, 2.44473984), Rotation2d.fromDegrees(180))),
                new ForwardUntilTilt(swerve, roll),
                new ChargeStationAlign(swerve, roll)
        );
    }
}
