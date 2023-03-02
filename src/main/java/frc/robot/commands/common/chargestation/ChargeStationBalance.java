package frc.robot.commands.common.chargestation;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.Swerve;

import java.util.function.DoubleSupplier;

public class ChargeStationBalance extends SequentialCommandGroup {

    public ChargeStationBalance(Swerve swerve, DoubleSupplier roll) {
        addCommands(
                new ForwardUntilTilt(swerve, roll),
                new ChargeStationAlign(swerve, roll)
        );
    }

}
