package frc.robot.commands.common.chargestation;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.lib.util.auto.ChargeStationPosition;
import frc.robot.commands.common.PathWeaveStraight;
import frc.robot.subsystems.Swerve;

import java.util.function.DoubleSupplier;

public class ChargeStation extends SequentialCommandGroup {

    public ChargeStation(Swerve swerve, DoubleSupplier roll, ChargeStationPosition position) {
        switch(position) {
            case LEFT:
                addCommands(new SequentialCommandGroup(
                        new PathWeaveStraight(swerve, true, 5.5,
                                new Pose2d(new Translation2d(1.5, 0), Rotation2d.fromRadians(0)),
                                new Pose2d(new Translation2d(1.5, 1.397), Rotation2d.fromDegrees(0))),
                        new ChargeStationBalance(swerve, roll, false)
                ));
                break;

            case CENTER:
                addCommands(new ChargeStationBalance(swerve, roll, true));
                break;

            case RIGHT:
                addCommands(new SequentialCommandGroup(
                        new PathWeaveStraight(swerve, true, 5.5,
                                new Pose2d(new Translation2d(1.5, 0), Rotation2d.fromRadians(0)),
                                new Pose2d(new Translation2d(1.5, -1.397), Rotation2d.fromDegrees(0))),
                        new ChargeStationBalance(swerve, roll, false)
                ));
        }
    }

}
