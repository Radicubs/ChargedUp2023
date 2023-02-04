package frc.robot.commands.P1;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.RobotContainer;
import frc.robot.commands.P1.HighRisk;
import frc.robot.commands.P1.LowRiskPlace;
import frc.robot.commands.P1.LowRiskStation;
import frc.robot.commands.P1.MidRisk;
import frc.robot.commands.P1.NoRisk;
import frc.robot.commands.P1.*;
import frc.robot.commands.common.AutoCommandGenerator;
import frc.robot.subsystems.PhotonVision;
import frc.robot.subsystems.Swerve;

import java.util.function.DoubleSupplier;

public class P1CommandGenerator implements AutoCommandGenerator {
    @Override
    public Command generate(Swerve swerve, PhotonVision vision, DoubleSupplier roll, boolean isBlue, RobotContainer.AutoDifficulty difficulty) {
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
