package frc.robot.commands.common;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Swerve;

import java.util.function.DoubleSupplier;

public class ChargeStationBalance extends CommandBase {
    private static final SwerveModuleState lockedState =
            new SwerveModuleState(0, Rotation2d.fromDegrees(90));

    private final Swerve swerve;
    private final DoubleSupplier roll;

    public ChargeStationBalance(Swerve swerve, DoubleSupplier roll) {
        this.swerve = swerve;
        this.roll = roll;
        addRequirements(swerve);
    }

    @Override
    public void execute() {
        double speed = roll.getAsDouble();
        speed = Math.copySign(((Math.pow(speed, 2) / 500.0) + 0.25), speed);
        swerve.driveFromChassisSpeeds(new ChassisSpeeds(speed, 0, 0));
    }

    @Override
    public boolean isFinished() {
        return Math.abs(roll.getAsDouble()) < 3;
    }

    @Override
    public void end(boolean interrupted) {
        swerve.setModuleStates(new SwerveModuleState[] {lockedState, lockedState, lockedState, lockedState});
    }

}