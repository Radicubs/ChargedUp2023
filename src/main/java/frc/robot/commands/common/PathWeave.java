package frc.robot.commands.common;

import edu.wpi.first.math.controller.HolonomicDriveController;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.geometry.*;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrajectoryConfig;
import edu.wpi.first.math.trajectory.TrajectoryGenerator;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Swerve;

import java.util.ArrayList;
import java.util.List;

public class PathWeave extends CommandBase {
    private static int instance = 0;
    private static final Pose2d TOLERANCE = new Pose2d(new Translation2d(0.03, 0.03), Rotation2d.fromDegrees(5));

    private HolonomicDriveController controller;
    private int exe = 0;
    private final TrajectoryConfig config;
    private Trajectory trajectory;
    private final Swerve swerve;
    private final Timer timer;
    private Pose2d finalPos;
    private final boolean isAbsolute;
    private Translation2d[] points;
    public static PathWeave fromFieldCoordinates(Swerve s, Pose2d pose, Translation2d... t) {
        return new PathWeave(s, true, pose, t);
    }

    public static PathWeave fromRelativeCoordinates(Swerve s, Pose2d pose, Translation2d... t) {
        return new PathWeave(s, false, pose, t);
    }


    private PathWeave(Swerve swerve, boolean isAbsolute, Pose2d finalPos, Translation2d... points) {
        instance++;
        config = new TrajectoryConfig(Constants.Swerve.maxSpeed, 1);
        this.finalPos = finalPos;
        this.swerve = swerve;
        addRequirements(swerve);
        this.timer = new Timer();
        this.isAbsolute = isAbsolute;
        this.points = points;
    }

    @Override
    public void initialize() {
        SmartDashboard.putBoolean("wedr4t5r", false);
        SmartDashboard.putNumber("scheduled", instance);
        List<Translation2d> finalPoints = new ArrayList<>(List.of(points));
        if(!isAbsolute) {
            Pose2d pose = swerve.getPose();
            double degrees = pose.getRotation().getDegrees();
            finalPos = rotatePose(degrees, finalPos);
            finalPos = new Pose2d(new Translation2d(finalPos.getX() + pose.getX(), finalPos.getY() + pose.getY()),
                    finalPos.getRotation());

            for(int i = 0; i < finalPoints.size(); i++) {
                Translation2d t = finalPoints.remove(i);
                t = rotateTranslation(degrees, t);
                finalPoints.add(i, t);
            }
        }

        trajectory = TrajectoryGenerator.generateTrajectory(swerve.getPose(), finalPoints, finalPos, config);
        swerve.setTrajectory(trajectory);
        controller = new HolonomicDriveController(
                new PIDController(0.5, 0, 0.001),
                new PIDController(0.5, 0, 0.001),
                new ProfiledPIDController(0.6, 0, 0,
                        new TrapezoidProfile.Constraints(6.18, 3.14)));
//        controller.setTolerance(TOLERANCE);
        timer.reset();
        timer.start();
    }

    @Override
    public void execute() {
        Trajectory.State state = trajectory.sample(timer.get());
        ChassisSpeeds speeds = controller.calculate(swerve.getPose(), state, new Rotation2d());
        speeds.omegaRadiansPerSecond = Units.degreesToRadians(speeds.omegaRadiansPerSecond);
        swerve.driveFromChassisSpeeds(speeds);
        SmartDashboard.putNumber("exe", exe++);

    }

    @Override
    public boolean isFinished() {
        return controller.atReference();
    }

    @Override
    public void end(boolean term) {
        SmartDashboard.putBoolean("wedr4t5r", true);
        swerve.driveFromChassisSpeeds(new ChassisSpeeds());
    }

    private static Translation2d rotateTranslation(double degrees, Translation2d translation) {
        return new Translation2d(
                (Math.cos(degrees) * translation.getX()) - (Math.sin(degrees) * translation.getY()),
                (Math.sin(degrees) * translation.getX()) + (Math.cos(degrees) * translation.getY()));
    }

    private static Pose2d rotatePose(double degrees, Pose2d pose) {
        return new Pose2d(rotateTranslation(degrees, pose.getTranslation()),
                Rotation2d.fromDegrees(pose.getRotation().getDegrees() + degrees));
    }

}
