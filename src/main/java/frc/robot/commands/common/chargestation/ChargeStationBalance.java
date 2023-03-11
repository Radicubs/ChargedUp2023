package frc.robot.commands.common.chargestation;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.common.PathWeave;
import frc.robot.commands.common.PathWeave2;
import frc.robot.subsystems.Swerve;

import java.util.function.DoubleSupplier;

public class ChargeStationBalance extends SequentialCommandGroup {
    private static final SwerveModuleState lockedState =
            new SwerveModuleState(0, Rotation2d.fromDegrees(45));

    public ChargeStationBalance(Swerve swerve, DoubleSupplier roll, boolean forward) {
        addCommands(
                new ForwardUntilTilt(swerve, roll, forward),
                new ChargeStationAlign(swerve, roll, forward),
                new PathWeave2(swerve, true, new Pose2d(new Translation2d(forward ? -0.45 : 0.45, 0), Rotation2d.fromDegrees(45)))
        );
    }

}
