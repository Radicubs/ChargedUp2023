package frc.robot.commands.tests;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.lib.util.auto.AutoCommandGenerator;
import frc.lib.util.auto.AutoDifficulty;
import frc.robot.commands.common.AprilTagAlignSubCommands.AdjustToFinalPosition;
import frc.robot.commands.common.PathWeave;
import frc.robot.commands.common.PathWeave2;
import frc.robot.commands.common.PathWeave3;
import frc.robot.commands.common.chargestation.ChargeStationBalance;
import frc.robot.commands.common.gripperup.GripperUp;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.PhotonVision;
import frc.robot.subsystems.Shoulder;
import frc.robot.subsystems.Swerve;

import java.util.function.DoubleSupplier;

public class TestCommandGenerator implements AutoCommandGenerator {
    @Override
    public Command generate(Swerve swerve, Arm arm, Shoulder shoulder, PhotonVision vision, DoubleSupplier roll, boolean alliance, AutoDifficulty difficulty) {
        switch(difficulty) {
            case NoRisk:
                return PathWeave.fromRelativeCoordinates(swerve, new Pose2d(new Translation2d(3, 0), Rotation2d.fromDegrees(0)));

            case LowRiskStation:
                return new ChargeStationBalance(swerve, roll, true);

            case MidRisk:
                return new GripperUp(swerve, arm, shoulder);

            case HighRisk:
                return new SequentialCommandGroup(
                        new PathWeave3(swerve, true, 5.5,
                                new Pose2d(new Translation2d(1.5, 0), Rotation2d.fromRadians(0)),
                                new Pose2d(new Translation2d(1.5, 1.397), Rotation2d.fromDegrees(0))),
                        new ChargeStationBalance(swerve, roll, false)
                );

            case Impossible:
                return new SequentialCommandGroup(PathWeave.fromRelativeCoordinates(swerve,
                        new Pose2d(new Translation2d(-5, 0), Rotation2d.fromDegrees(0))),
                        new ChargeStationBalance(swerve, roll, false));

            default:
                return null;
        }
    }
}
