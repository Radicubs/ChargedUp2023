package frc.robot.commands.tests;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.Command;
import frc.lib.util.auto.AutoDifficulty;
import frc.robot.commands.common.AprilTagAlign;
import frc.lib.util.auto.AutoCommandGenerator;
import frc.robot.commands.common.PathWeave;
import frc.robot.commands.common.chargestation.ChargeStationBalance;
import frc.robot.subsystems.PhotonVision;
import frc.robot.subsystems.Swerve;

import java.util.function.DoubleSupplier;

public class TestCommandGenerator implements AutoCommandGenerator {
    @Override
    public Command generate(Swerve swerve, PhotonVision vision, DoubleSupplier roll, boolean alliance, AutoDifficulty difficulty) {
        switch(difficulty) {
            case LowRiskPlace:
                return new AprilTagAlign(swerve, vision, vision.findNearestTag(), AprilTagAlign.TagAlignment.CENTER);

            case LowRiskStation:
                return new ChargeStationBalance(swerve, roll);

            case MidRisk:
                return PathWeave.fromRelativeCoordinates(swerve, new Pose2d(new Translation2d(-1, 0), Rotation2d.fromDegrees(20)));

            case HighRisk:
                return new AprilTagAlign(swerve, vision, 1, AprilTagAlign.TagAlignment.CENTER);

            default:
                return null;
        }
    }
}
