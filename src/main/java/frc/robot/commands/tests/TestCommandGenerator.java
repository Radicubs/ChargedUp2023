package frc.robot.commands.tests;

import edu.wpi.first.wpilibj2.command.Command;
import frc.lib.util.auto.AutoDifficulty;
import frc.robot.commands.common.AprilTagAlign;
import frc.lib.util.auto.AutoCommandGenerator;
import frc.robot.commands.common.chargestation.ChargeStationBalance;
import frc.robot.subsystems.PhotonVision;
import frc.robot.subsystems.Swerve;

import java.util.function.DoubleSupplier;

public class TestCommandGenerator implements AutoCommandGenerator {
    @Override
    public Command generate(Swerve swerve, PhotonVision vision, DoubleSupplier roll, boolean alliance, AutoDifficulty difficulty) {
        switch(difficulty) {
            case LowRiskPlace:
                return new AprilTagAlign(swerve, vision, vision.findNearestTag(), AprilTagAlign.TagAlignment.CENTER);

            case LowRiskStation:
                return new ChargeStationBalance(swerve, roll);

            default:
                return null;
        }
    }
}
