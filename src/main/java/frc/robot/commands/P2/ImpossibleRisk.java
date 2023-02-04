package frc.robot.commands.P2;

import frc.robot.commands.P1.HighRisk;
import frc.robot.commands.P1.LowRiskStation;
import frc.robot.commands.P1.P1AutoCommand;
import frc.robot.subsystems.PhotonVision;
import frc.robot.subsystems.Swerve;

import java.util.function.DoubleSupplier;

public class ImpossibleRisk extends P2AutoCommand {
    public ImpossibleRisk(Swerve swerve, PhotonVision vision, DoubleSupplier roll, boolean isBlue) {
        super(swerve, isBlue);
        addCommands(new HighRisk(swerve, vision, isBlue), new LowRiskStation(swerve, roll, isBlue));
    }
}
