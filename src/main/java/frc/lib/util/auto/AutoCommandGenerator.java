package frc.lib.util.auto;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.PhotonVision;
import frc.robot.subsystems.Shoulder;
import frc.robot.subsystems.Swerve;

import java.util.function.DoubleSupplier;

public interface AutoCommandGenerator {

    Command generate(Swerve swerve, Arm arm, Shoulder shoulder, PhotonVision vision, DoubleSupplier roll, boolean alliance, AutoDifficulty difficulty);
}
