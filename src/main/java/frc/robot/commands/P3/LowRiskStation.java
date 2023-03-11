package frc.robot.commands.P3;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import frc.robot.commands.common.chargestation.ChargeStationAlign;
import frc.robot.commands.common.PathWeave;
import frc.robot.subsystems.Swerve;

import java.util.function.DoubleSupplier;

public class LowRiskStation extends P3AutoCommand {

    public LowRiskStation(Swerve swerve, DoubleSupplier roll, boolean isBlue) {
        super(swerve, isBlue);
        //TODO
        addCommands(
                PathWeave.fromFieldCoordinates(swerve, new Pose2d(
                        new Translation2d(4.112, 3.321 * yMult), Rotation2d.fromDegrees(0)),
                        new Translation2d(3.788, 4.490 * yMult)),
                new ChargeStationAlign(swerve, roll, true)
        );
    }
}
