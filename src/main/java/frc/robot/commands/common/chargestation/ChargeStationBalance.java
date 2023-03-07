package frc.robot.commands.common.chargestation;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.common.PathWeave;
import frc.robot.subsystems.Swerve;

import java.util.function.DoubleSupplier;

public class ChargeStationBalance extends SequentialCommandGroup {
    private static final SwerveModuleState lockedState =
            new SwerveModuleState(0, Rotation2d.fromDegrees(45));

    public ChargeStationBalance(Swerve swerve, DoubleSupplier roll) {
        addCommands(
                new ForwardUntilTilt(swerve, roll),
                new ChargeStationAlign(swerve, roll),
                PathWeave.fromRelativeCoordinates(swerve, new Pose2d(new Translation2d(-0.45, 0), Rotation2d.fromDegrees(45)))
        );
    }

}
