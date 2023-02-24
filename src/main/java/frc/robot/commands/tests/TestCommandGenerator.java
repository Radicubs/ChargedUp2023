package frc.robot.commands.tests;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.RobotContainer;
import frc.robot.commands.common.AprilTagAlign;
import frc.robot.commands.common.AutoCommandGenerator;
import frc.robot.commands.common.PathWeave;
import frc.robot.commands.teleop.MoveCommand;
import frc.robot.subsystems.PhotonVision;
import frc.robot.subsystems.Swerve;

import java.util.function.DoubleSupplier;

public class TestCommandGenerator implements AutoCommandGenerator {
    @Override
    public Command generate(Swerve swerve, PhotonVision vision, DoubleSupplier roll, boolean alliance, RobotContainer.AutoDifficulty difficulty) {
        switch(difficulty) {
            case LowRiskStation:
                return new SequentialCommandGroup(new InstantCommand(() -> {swerve.resetOdo(new Pose2d(new Translation2d(1, 0), Rotation2d.fromDegrees(0)));}, swerve),
                        new PathWeave(swerve, new Pose2d(new Translation2d(0.5, 0), Rotation2d.fromDegrees(0))));
            default:
                return null;
        }
    }
}
