package frc.robot.commands.common;

import edu.wpi.first.math.controller.HolonomicDriveController;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrajectoryConfig;
import edu.wpi.first.math.trajectory.TrajectoryGenerator;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Swerve;

import java.util.ArrayList;
import java.util.List;

public class PathWeaveStraight extends CommandBase {

    private static final TrajectoryConfig config =
            new TrajectoryConfig(Constants.AutoConstants.kMaxSpeedMetersPerSecond,
                    Constants.AutoConstants.kMaxAccelerationMetersPerSecondSquared);

    private final Swerve swerve;
    private final Timer timer;
    private final double ttk;
    private final boolean isRobotRelative;
    private List<Pose2d> points;
    private Trajectory trajectory;
    private HolonomicDriveController controller;

    public PathWeaveStraight(Swerve swerve, boolean isRobotRelative, double ttk, Pose2d... points) {
        this.swerve = swerve;
        this.ttk = ttk;
        this.timer = new Timer();
        this.isRobotRelative = isRobotRelative;
        this.points = new ArrayList<>(List.of(points));
        addRequirements(swerve);
    }

    @Override
    public void initialize() {
        points.add(0, swerve.getPose());

        if(isRobotRelative) {
            double angleOffset = swerve.getPose().getRotation().getRadians();
            for(int i = 1; i < points.size(); i++) {
                Pose2d old = points.remove(i);
                Pose2d newPos = new Pose2d(new Translation2d(
                        (old.getX() * Math.cos(angleOffset)) - (old.getY() * Math.sin(angleOffset)) + swerve.getPose().getX(),
                        (old.getX() * Math.sin(angleOffset)) + (old.getY() * Math.cos(angleOffset)) + swerve.getPose().getY()),
                        Rotation2d.fromRadians(angleOffset + old.getRotation().getRadians()));
                points.add(i, newPos);
            }
        }

        trajectory = TrajectoryGenerator.generateTrajectory(points, config);
        controller = new HolonomicDriveController(
                new PIDController(0.5, 0, 0.001),
                new PIDController(0.5, 0, 0.001),
                new ProfiledPIDController(0, 0, 0,
                        new TrapezoidProfile.Constraints(Math.PI / 8, Math.PI / 16)));

        timer.reset();
        timer.start();
    }

    @Override
    public void execute() {
        Trajectory.State state = trajectory.sample(timer.get());
        ChassisSpeeds speeds = controller.calculate(swerve.getPose(), state, Rotation2d.fromDegrees(0));
        swerve.driveFromChassisSpeeds(speeds);
    }

    @Override
    public boolean isFinished() {
        return controller.atReference() || timer.get() > ttk;
    }

    @Override
    public void end(boolean terminated) {
        swerve.driveFromChassisSpeeds(new ChassisSpeeds());
    }


}
