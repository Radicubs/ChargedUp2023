package frc.robot.commands.common;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.Swerve;

import java.util.function.DoubleSupplier;

public class ChargeStationAlign extends SequentialCommandGroup {

    public ChargeStationAlign(Swerve swerve, DoubleSupplier roll) {
        addCommands(
                new ForwardUntilTilt(swerve, roll),
                new ChargeStationBalance(swerve, roll)
        );
    }

}
