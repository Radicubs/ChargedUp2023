package frc.robot.commands.P1;

import frc.robot.subsystems.PhotonVision;
import frc.robot.subsystems.Swerve;

import java.util.function.DoubleSupplier;

public class ImpossibleRisk extends P1AutoCommand {
    public ImpossibleRisk(Swerve swerve, PhotonVision vision, DoubleSupplier roll, boolean isBlue) {
        super(swerve, isBlue);
        addCommands(new HighRisk(swerve, vision, isBlue), new LowRiskStation(swerve, roll, isBlue));
    }
}
