package frc.robot.commands.P0;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import frc.robot.commands.common.*;
import frc.robot.subsystems.Swerve;

import java.util.List;
import java.util.function.DoubleSupplier;

public class LowRiskStation extends P0AutoCommand {

    public LowRiskStation(Swerve swerve, DoubleSupplier roll, boolean isBlue) {
        super(swerve, isBlue);
        addCommands(
                PathWeave.fromFieldCoordinates(swerve, new Pose2d(new Translation2d(4.489, 2.079 * yMult),
                        Rotation2d.fromDegrees(180)), new Translation2d(4.489, 0.909 * yMult)),
                new ChargeStationAlign(swerve, roll)
        );
    }
}
