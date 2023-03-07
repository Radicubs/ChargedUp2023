package frc.lib.util.auto;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.commands.P0.P0CommandGenerator;
import frc.robot.commands.P1.P1CommandGenerator;
import frc.robot.commands.P2.P2CommandGenerator;
import frc.robot.commands.P3.P3CommandGenerator;
import frc.robot.commands.tests.TestCommandGenerator;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.PhotonVision;
import frc.robot.subsystems.Shoulder;
import frc.robot.subsystems.Swerve;

import java.util.function.DoubleSupplier;

public enum StartingPosition {
    P0(new P0CommandGenerator()),
    P1(new P1CommandGenerator()),
    P2(new P2CommandGenerator()),
    P3(new P3CommandGenerator()),
    TESTS(new TestCommandGenerator());

    private final AutoCommandGenerator generator;

    public Command generate(Swerve swerve, Arm arm, Shoulder shoulder, PhotonVision vision, DoubleSupplier roll, boolean alliance, AutoDifficulty difficulty) {
        return generator.generate(swerve, arm, shoulder, vision, roll, alliance, difficulty);
    }

    StartingPosition(AutoCommandGenerator generator) {
        this.generator = generator;
    }
}
