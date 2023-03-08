package frc.robot.subsystems;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.lib.util.swerve.SwerveModule;

import java.util.function.DoubleSupplier;

public class Swerve extends SubsystemBase {
    public SwerveDriveOdometry swerveOdometry;
    public SwerveModule[] mSwerveMods;
    private boolean fieldOriented;
    private boolean slowmode;
    private final DoubleSupplier rotationSupplier;
    private double speed;

    public Swerve(DoubleSupplier rotationSupplier) {
        this.rotationSupplier = rotationSupplier;
        fieldOriented = false;
        slowmode = false;
        speed = Constants.Swerve.maxSpeed;

        mSwerveMods = new SwerveModule[] {
                new SwerveModule(0, Constants.Swerve.Mod0.constants),
                new SwerveModule(1, Constants.Swerve.Mod1.constants),
                new SwerveModule(2, Constants.Swerve.Mod2.constants),
                new SwerveModule(3, Constants.Swerve.Mod3.constants)
        };

        swerveOdometry = new SwerveDriveOdometry(Constants.Swerve.swerveKinematics, getYaw(), getModulePositions());
    }

    public void toggleSlowmode() {
        slowmode = !slowmode;
        speed = slowmode ? Constants.Swerve.maxSpeed / 3 : Constants.Swerve.maxSpeed;
    }

    public void toggleFieldOriented() {
        fieldOriented = !fieldOriented;
    }

    public void resetOdo() {
        resetOdo(new Pose2d());
    }

    public void resetOdo(Pose2d position) {
        swerveOdometry.resetPosition(getYaw(), getModulePositions(), position);
    }

    public void drive(Translation2d translation, double rotation, boolean isOpenLoop) {
        SwerveModuleState[] swerveModuleStates = Constants.Swerve.swerveKinematics.toSwerveModuleStates(
                fieldOriented ? ChassisSpeeds.fromFieldRelativeSpeeds(
                        translation.getX(),
                        translation.getY(),
                        rotation,
                        getYaw())
                        : new ChassisSpeeds(
                                translation.getX(),
                                translation.getY(),
                                rotation));
        SwerveDriveKinematics.desaturateWheelSpeeds(swerveModuleStates, Constants.Swerve.maxSpeed);

        for (SwerveModule mod : mSwerveMods) {
            mod.setDesiredState(swerveModuleStates[mod.moduleNumber], isOpenLoop);
        }
    }

    public void rotateAroundPoint(Translation2d point, double rotation) {
        SwerveModuleState[] swerveModuleStates = Constants.Swerve.swerveKinematics.toSwerveModuleStates(
                new ChassisSpeeds(
                        0, 0, rotation),
                point);
        
        for(SwerveModule mod : mSwerveMods){
            mod.setDesiredState(swerveModuleStates[mod.moduleNumber], false);
        }
    }

    public void driveFromChassisSpeeds(ChassisSpeeds speeds) {
        SwerveModuleState[] states = Constants.Swerve.swerveKinematics.toSwerveModuleStates(speeds);
        SwerveDriveKinematics.desaturateWheelSpeeds(states, Constants.Swerve.maxSpeed);
        for (SwerveModule mod : mSwerveMods) {
            mod.setDesiredState(states[mod.moduleNumber], false);
        }
    }

    public Pose2d getPose() {
        return swerveOdometry.getPoseMeters();
    }


    public SwerveModulePosition[] getModulePositions() {
        SwerveModulePosition[] positions = new SwerveModulePosition[4];
        for (SwerveModule mod : mSwerveMods) {
            positions[mod.moduleNumber] = mod.getPosition();
        }
        return positions;
    }

    public Rotation2d getYaw() {
        return (Constants.Swerve.invertGyro) ? Rotation2d.fromDegrees(360 - rotationSupplier.getAsDouble())
                : Rotation2d.fromDegrees(rotationSupplier.getAsDouble());
    }

    @Override
    public void periodic() {
        SmartDashboard.putBoolean("Field oriented", fieldOriented);
        SmartDashboard.putBoolean("slowmode", slowmode);
        SmartDashboard.putNumber("swerve x", swerveOdometry.getPoseMeters().getX());
        SmartDashboard.putNumber("swerve y", swerveOdometry.getPoseMeters().getY());
        SmartDashboard.putNumber("swerve rot", swerveOdometry.getPoseMeters().getRotation().getDegrees());

        swerveOdometry.update(getYaw(), getModulePositions());
    }
}