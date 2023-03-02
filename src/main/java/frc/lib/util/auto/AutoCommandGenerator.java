package frc.lib.util.auto;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.PhotonVision;
import frc.robot.subsystems.Swerve;

import java.util.function.DoubleSupplier;

public interface AutoCommandGenerator {

    Command generate(Swerve swerve, PhotonVision vision, DoubleSupplier roll, boolean alliance, AutoDifficulty difficulty);
}
