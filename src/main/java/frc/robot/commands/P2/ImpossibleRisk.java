package frc.robot.commands.P2;

import frc.robot.commands.common.chargestation.ChargeStationBalance;
import frc.robot.subsystems.PhotonVision;
import frc.robot.subsystems.Swerve;

import java.util.function.DoubleSupplier;

public class ImpossibleRisk extends P2AutoCommand {
    public ImpossibleRisk(Swerve swerve, PhotonVision vision, DoubleSupplier roll, boolean isBlue) {
        super(swerve, isBlue);
        addCommands(
                new HighRisk(swerve, vision, isBlue),
                new ChargeStationBalance(swerve, roll, true));
    }
}
