package frc.robot.commands.tests;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.RobotContainer;
import frc.robot.commands.common.AprilTagAlign;
import frc.robot.commands.common.AutoCommandGenerator;
import frc.robot.commands.teleop.MoveCommand;
import frc.robot.subsystems.PhotonVision;
import frc.robot.subsystems.Swerve;

import java.util.function.DoubleSupplier;

public class TestCommandGenerator implements AutoCommandGenerator {
    @Override
    public Command generate(Swerve swerve, PhotonVision vision, DoubleSupplier roll, boolean alliance, RobotContainer.AutoDifficulty difficulty) {
        if(difficulty == RobotContainer.AutoDifficulty.NoRisk)
            return new AprilTagAlign(swerve, vision, 1, AprilTagAlign.TagAlignment.LEFT);
        else{
            return new MoveCommand(swerve);
        }
    }
}
