package frc.robot.commands.P0;

import frc.robot.commands.common.SequentialCommand;
import frc.robot.subsystems.PhotonVision;
import frc.robot.subsystems.Swerve;

import java.util.function.DoubleSupplier;

public class ImpossibleRisk extends SequentialCommand {

    public ImpossibleRisk(Swerve swerve, PhotonVision vision, DoubleSupplier roll, boolean isBlue) {
        super(isBlue);
        addCommands(
                new HighRisk(swerve, vision, isBlue),
                new LowRiskStation(swerve, roll, isBlue));
    }

}
