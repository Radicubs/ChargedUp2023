package frc.robot.commands.P1;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import frc.robot.commands.common.ChargeStationAlign;
import frc.robot.commands.common.PathWeave;
import frc.robot.commands.common.SequentialCommand;
import frc.robot.subsystems.Swerve;

import java.util.function.DoubleSupplier;

public class LowRiskStation extends P1AutoCommand {

    public LowRiskStation(Swerve swerve, DoubleSupplier roll, boolean isBlue) {
        super(swerve, isBlue);
        addCommands(
                PathWeave.fromFieldCoordinates(swerve, new Pose2d(new Translation2d(4.349, 2.252 * yMult),
                        Rotation2d.fromDegrees(180)), new Translation2d(4.349, 1.102 * yMult)),
                new ChargeStationAlign(swerve, roll));
    }
}
