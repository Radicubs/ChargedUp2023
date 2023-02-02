package frc.robot.commands.common;

import edu.wpi.first.math.controller.HolonomicDriveController;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Transform2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrajectoryConfig;
import edu.wpi.first.math.trajectory.TrajectoryGenerator;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Swerve;

import java.util.List;

public class PathWeave extends CommandBase {

    public static PathWeave fromFieldCoordinates(Swerve swerve, List<Translation2d> points, Pose2d position) {
        return new PathWeave(swerve, position, points);
    }

    public static PathWeave fromRelativeCoordinates(Swerve swerve, List<Translation2d> points, Pose2d position) {
        return new PathWeave(swerve, points, position);
    }
    private static final Pose2d PATH_WEAVE_TOLERANCE = new Pose2d(0.15, 0.15, Rotation2d.fromDegrees(10));
    private static final TrajectoryConfig config =
            new TrajectoryConfig(Constants.Swerve.maxSpeed, 1)
                    .setKinematics(Constants.Swerve.swerveKinematics);

    private final Swerve swerve;
    private final HolonomicDriveController controller;
    private final Trajectory trajectory;
    private final Timer timer;

    private PathWeave(Swerve swerve, Trajectory trajectory) {
        this.swerve = swerve;
        addRequirements(swerve);
        timer = new Timer();
        controller = new HolonomicDriveController(
                new PIDController(0.5, 0, 0),
                new PIDController(0.5, 0, 0),
                new ProfiledPIDController(0.5, 0, 0, new TrapezoidProfile.Constraints(6.28, 3.14)));
        this.trajectory = trajectory;
    }

    // Constructor to move robot a magnitude in relation to its current position, ex move 1 meter backwards
    private PathWeave(Swerve swerve, List<Translation2d> points, Pose2d delta) {
        this(swerve, TrajectoryGenerator.generateTrajectory(new Pose2d(), points, delta, config).transformBy(
                new Transform2d(delta.getTranslation(), delta.getRotation())));
    }

    // Constructor to move robot to a specific field position, ex move to 1, 0
    private PathWeave(Swerve swerve, Pose2d toPos, List<Translation2d> points) {
        this(swerve, TrajectoryGenerator.generateTrajectory(swerve.getPose(), points, toPos, config));
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
