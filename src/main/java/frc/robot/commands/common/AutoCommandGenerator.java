package frc.robot.commands.common;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.RobotContainer;
import frc.robot.subsystems.PhotonVision;
import frc.robot.subsystems.Swerve;

public interface AutoCommandGenerator {

    Command generate(Swerve swerve, PhotonVision vision, boolean alliance, RobotContainer.AutoDifficulty difficulty);
}
