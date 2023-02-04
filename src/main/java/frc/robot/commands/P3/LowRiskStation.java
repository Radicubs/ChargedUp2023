package frc.robot.commands.P3;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import frc.robot.commands.P3.P3AutoCommand;
import frc.robot.commands.common.ChargeStationAlign;
import frc.robot.commands.common.PathWeave;
import frc.robot.subsystems.Swerve;

import java.util.function.DoubleSupplier;

public class LowRiskStation extends P3AutoCommand {

    public LowRiskStation(Swerve swerve, DoubleSupplier roll, boolean isBlue) {
        super(swerve, isBlue);
        //TODO
        addCommands(
                PathWeave.fromRelativeCoordinates(swerve, new Pose2d(new Translation2d(0, 0 * yMult),
                        Rotation2d.fromDegrees(180))),
                new ChargeStationAlign(swerve, roll)
        );
    }
}
