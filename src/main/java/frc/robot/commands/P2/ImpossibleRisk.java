package frc.robot.commands.P2;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import frc.robot.commands.common.ChargeStationAlign;
import frc.robot.commands.common.ChargeStationBalance;
import frc.robot.commands.common.PathWeave;
import frc.robot.subsystems.PhotonVision;
import frc.robot.subsystems.Swerve;

import java.util.function.DoubleSupplier;

public class ImpossibleRisk extends P2AutoCommand {
    public ImpossibleRisk(Swerve swerve, PhotonVision vision, DoubleSupplier roll, boolean isBlue) {
        super(swerve, isBlue);
        addCommands(
                new HighRisk(swerve, vision, isBlue),
                new ChargeStationAlign(swerve, roll));
    }
}
