package frc.robot.commands.P2;

import frc.robot.commands.common.chargestation.ChargeStationBalance;
import frc.robot.subsystems.Swerve;

import java.util.function.DoubleSupplier;

public class LowRiskStation extends P2AutoCommand { // low risk leaves the community and then docks on the charging station

    public LowRiskStation(Swerve swerve, DoubleSupplier roll, boolean isBlue) {
        super(swerve, isBlue);
        addCommands(
                new NoRisk(swerve, isBlue),
                new ChargeStationBalance(swerve, roll)
        );
    }
}
