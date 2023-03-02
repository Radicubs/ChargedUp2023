package frc.robot.commands.P0;

import edu.wpi.first.wpilibj2.command.Command;
import frc.lib.util.auto.AutoDifficulty;
import frc.lib.util.auto.AutoCommandGenerator;
import frc.robot.subsystems.PhotonVision;
import frc.robot.subsystems.Swerve;

import java.util.function.DoubleSupplier;

public class P0CommandGenerator implements AutoCommandGenerator {
    @Override
    public Command generate(Swerve swerve, PhotonVision vision, DoubleSupplier roll, boolean isBlue, AutoDifficulty difficulty) {
        switch(difficulty) {
            case NoRisk:
                return new NoRisk(swerve, isBlue);

            case LowRiskPlace:
                return new LowRiskPlace(swerve, vision, isBlue);

            case LowRiskStation:
                return new LowRiskStation(swerve, roll, isBlue);

            case MidRisk:
                return new MidRisk(swerve, vision, roll, isBlue);

            case HighRisk:
                return new HighRisk(swerve, vision, isBlue);

            case Impossible:
                return new ImpossibleRisk(swerve, vision, roll, isBlue);

            default:
                throw new IllegalArgumentException();
        }
    }
}
