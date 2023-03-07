package frc.robot.commands.common.chargestation;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Swerve;

import java.util.function.DoubleSupplier;

public class ChargeStationAlign extends CommandBase {
    private static final SwerveModuleState lockedState =
            new SwerveModuleState(0, Rotation2d.fromDegrees(90));

    private final Swerve swerve;
    private final DoubleSupplier roll;
    private double prev;
    private double change;

    public ChargeStationAlign(Swerve swerve, DoubleSupplier roll) {
        this.swerve = swerve;
        this.roll = roll;
        prev = roll.getAsDouble();
        addRequirements(swerve);
    }

    @Override
    public void execute() {
        change = Math.abs(roll.getAsDouble() - prev);
        if(change < 5){
            double speed = roll.getAsDouble();
            //speed = Math.copySign(((Math.pow(speed, 2) / 500.0) + 0.25), speed);
            swerve.driveFromChassisSpeeds(new ChassisSpeeds(Math.copySign(0.15, speed), 0, 0));
        }
        System.out.println(change);
        
        prev = roll.getAsDouble();
    }

    @Override
    public boolean isFinished() {
        return Math.abs(roll.getAsDouble()) < 3;
    }

    @Override
    public void end(boolean interrupted) {

    }

}
