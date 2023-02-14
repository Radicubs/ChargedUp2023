package frc.robot.commands.P2;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import frc.robot.commands.common.ChargeStationAlign;
import frc.robot.commands.common.PathWeave;
import frc.robot.commands.common.SequentialCommand;
import frc.robot.subsystems.Swerve;

import java.util.function.DoubleSupplier;

public class LowRiskStation extends P2AutoCommand {

    public LowRiskStation(Swerve swerve, DoubleSupplier roll, boolean isBlue) {
        super(swerve, isBlue);
        addCommands(
                PathWeave.fromRelativeCoordinates(swerve, new Pose2d(new Translation2d(3.444, 0),
                        Rotation2d.fromDegrees(0)), new Translation2d(0, 1.887 * yMult),
                        new Translation2d(3.444, 1.887 * yMult)),
                new ChargeStationAlign(swerve, roll)
        );
    }
}
