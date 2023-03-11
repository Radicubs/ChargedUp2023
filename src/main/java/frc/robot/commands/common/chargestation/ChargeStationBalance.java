package frc.robot.commands.common.chargestation;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.common.PathWeaveStraight;
import frc.robot.subsystems.Swerve;

import java.util.function.DoubleSupplier;

public class ChargeStationBalance extends SequentialCommandGroup {
    private static final SwerveModuleState lockedState =
            new SwerveModuleState(0, Rotation2d.fromDegrees(45));

    public ChargeStationBalance(Swerve swerve, DoubleSupplier roll, boolean forward) {
        addCommands(
                new ForwardUntilTilt(swerve, roll, forward),
                new ChargeStationAlign(swerve, roll, forward),
                new PathWeaveStraight(swerve, true, 3, new Pose2d(new Translation2d(0.2 * (forward ? -1 : 1), 0), Rotation2d.fromDegrees(45)))
        );
    }

}
