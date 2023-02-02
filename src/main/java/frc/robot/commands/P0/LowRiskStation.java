package frc.robot.commands.P0;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.common.ChargeStationAlign;
import frc.robot.commands.common.ChargeStationBalance;
import frc.robot.commands.common.PathWeave;
import frc.robot.commands.common.ForwardUntilTilt;
import frc.robot.subsystems.Swerve;

import java.util.List;
import java.util.function.DoubleSupplier;

public class LowRiskStation extends SequentialCommandGroup {

    public LowRiskStation(Swerve swerve, DoubleSupplier roll) {
        addCommands(
                PathWeave.fromFieldCoordinates(swerve, List.of(new Translation2d(5.907849797, 2.44473984)),
                        new Pose2d(new Translation2d(5.907849797, 2.44473984), Rotation2d.fromDegrees(180))),
                new ChargeStationAlign(swerve, roll)
        );
    }
}
