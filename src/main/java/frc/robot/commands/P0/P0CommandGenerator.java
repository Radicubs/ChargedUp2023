package frc.robot.commands.P0;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.RobotContainer;
import frc.robot.commands.common.AutoCommandGenerator;
import frc.robot.subsystems.PhotonVision;
import frc.robot.subsystems.Swerve;

import java.util.function.DoubleSupplier;

public class P0CommandGenerator implements AutoCommandGenerator {
    @Override
    public Command generate(Swerve swerve, PhotonVision vision, DoubleSupplier roll, boolean isBlue, RobotContainer.AutoDifficulty difficulty) {
        return switch(difficulty) {
            case NoRisk -> new NoRisk(swerve);
            case LowRiskPlace -> new LowRiskPlace(swerve, vision, isBlue);
            case LowRiskStation -> new LowRiskStation(swerve, roll, isBlue);
            case MidRisk -> new MidRisk(swerve, vision, roll, isBlue);
            case HighRisk -> new HighRisk(swerve, vision, isBlue);
            case Impossible -> new ImpossibleRisk(swerve, vision, roll, isBlue);
        };
    }
}
