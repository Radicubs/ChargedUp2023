package frc.robot.commands.P3;

import frc.robot.commands.P3.HighRisk;
import frc.robot.commands.P3.LowRiskStation;
import frc.robot.commands.P3.P3AutoCommand;
import frc.robot.subsystems.PhotonVision;
import frc.robot.subsystems.Swerve;

import java.util.function.DoubleSupplier;

public class ImpossibleRisk extends P3AutoCommand {
    public ImpossibleRisk(Swerve swerve, PhotonVision vision, DoubleSupplier roll, boolean isBlue) {
        super(swerve, isBlue);
        addCommands(new HighRisk(swerve, vision, isBlue), new LowRiskStation(swerve, roll, isBlue));
    }
}
