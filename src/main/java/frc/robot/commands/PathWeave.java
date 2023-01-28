package frc.robot.commands;

import edu.wpi.first.math.controller.HolonomicDriveController;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.controller.RamseteController;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrajectoryConfig;
import edu.wpi.first.math.trajectory.TrajectoryGenerator;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.RamseteCommand;
import frc.robot.Constants;
import frc.robot.subsystems.Swerve;

import java.util.List;

public class PathWeave extends CommandBase {

    private final Swerve swerve;
    private final HolonomicDriveController controller;
    private final Trajectory trajectory;
    private final Timer timer;

    public PathWeave(Swerve swerve) {
        this.swerve = swerve;
        addRequirements(swerve);
        timer = new Timer();
        controller = new HolonomicDriveController(
                new PIDController(0.5, 0, 0),
                new PIDController(0.5, 0, 0),
                new ProfiledPIDController(0.5, 0, 0, new TrapezoidProfile.Constraints(6.28, 3.14)));
        TrajectoryConfig config = new TrajectoryConfig(Constants.Swerve.maxSpeed, 1)
                .setKinematics(Constants.Swerve.swerveKinematics);
        trajectory = TrajectoryGenerator.generateTrajectory(
                new Pose2d(),
                List.of(),
                new Pose2d(2, 0, Rotation2d.fromDegrees(0)),
                config);
    }

    @Override
    public void initialize() {
        timer.reset();
        timer.start();
        swerve.driveFromChassisSpeeds(new ChassisSpeeds());
        swerve.resetOdo();
    }

    @Override
    public void execute() {
        Trajectory.State state = trajectory.sample(timer.get());
        ChassisSpeeds speeds = controller.calculate(swerve.getPose(), state, Rotation2d.fromDegrees(0));
        System.out.println("Chassis Speeds: " + speeds);
        System.out.println("Chassis Pose: " + swerve.getPose());
        speeds.omegaRadiansPerSecond = Units.degreesToRadians(speeds.omegaRadiansPerSecond);
        swerve.driveFromChassisSpeeds(speeds);
    }

    @Override
    public boolean isFinished() {
        return controller.atReference();
    }

    @Override
    public void end(boolean interrupted) {
        timer.stop();
        swerve.driveFromChassisSpeeds(new ChassisSpeeds());
    }
}
