package frc.robot.commands.common;

import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Swerve;

import java.util.function.DoubleSupplier;

public class ForwardUntilTilt extends CommandBase {

    private final Swerve swerve;
    private final DoubleSupplier roll;

    public ForwardUntilTilt(Swerve swerve, DoubleSupplier roll) {
        this.swerve = swerve;
        this.roll = roll;
        addRequirements(swerve);
    }

    @Override
    public void initialize() {
        swerve.driveFromChassisSpeeds(new ChassisSpeeds(0.5, 0, 0));
    }

    @Override
    public boolean isFinished() {
        return Math.abs(roll.getAsDouble()) > 7.5;
    }

    @Override
    public void end(boolean interrupted) {
        swerve.driveFromChassisSpeeds(new ChassisSpeeds());
    }
}
