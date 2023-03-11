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
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Swerve;

import java.util.ArrayList;
import java.util.List;

public class PathWeave2 extends CommandBase {
    private static final Pose2d tolerance = new Pose2d(0.05, 0.05, Rotation2d.fromDegrees(180));

    private static final TrajectoryConfig config =
            new TrajectoryConfig(Constants.AutoConstants.kMaxSpeedMetersPerSecond,
                    Constants.AutoConstants.kMaxAccelerationMetersPerSecondSquared);

    private final Swerve swerve;
    private final Timer timer;
    private final boolean isRobotRelative;
    private Pose2d endpos;
    private List<Translation2d> points;
    private Trajectory trajectory;
    private HolonomicDriveController controller;

    public PathWeave2(Swerve swerve, boolean isRobotRelative, Pose2d endpos, Translation2d... points) {
        // Pass in the swerve subsystem, the ending position (ex 1, 0, 0 is 1 meter in front of the robot),
        // and any points to hit along the way
        this.swerve = swerve;
        this.isRobotRelative = isRobotRelative;
        this.endpos = endpos;
        this.points = new ArrayList<>(List.of(points));
        timer = new Timer();
        addRequirements(swerve);
    }

    @Override
    public void initialize() {
        if(isRobotRelative) {
            // Grab angle of the robot
            double angleOffset = swerve.getPose().getRotation().getRadians();

            // Iterate through extra points and rotate them the correct amount, then translate them over to the robot
            for(int i = 0; i < points.size(); i++) {
                Translation2d old = points.remove(i);
                Translation2d t = new Translation2d(
                        (old.getX() * Math.cos(angleOffset)) - (old.getY() * Math.sin(angleOffset)) + swerve.getPose().getX(),
                        (old.getX() * Math.sin(angleOffset)) + (old.getY() * Math.cos(angleOffset)) + swerve.getPose().getY());
                points.add(i, t);
            }

            // Do the same to the ending position
            endpos = new Pose2d(new Translation2d(
                    (endpos.getX() * Math.cos(angleOffset)) - (endpos.getY() * Math.sin(angleOffset)) + swerve.getPose().getX(),
                    (endpos.getX() * Math.sin(angleOffset)) + (endpos.getY() * Math.cos(angleOffset)) + swerve.getPose().getY()
            ), Rotation2d.fromRadians(endpos.getRotation().getRadians() + angleOffset));

        }

        // Put the calculated poses onto glass
        swerve.getField().getObject("endpos").setPose(endpos);
        for(int i = 0; i < points.size(); i++) {
            swerve.getField().getObject("pos" + i).setPose(new Pose2d(points.get(i), Rotation2d.fromDegrees(0)));
        }

        // Invoke the trajectory generator
        trajectory = TrajectoryGenerator.generateTrajectory(swerve.getPose(), points, endpos, config);
        controller = new HolonomicDriveController(
                new PIDController(0.5, 0, 0.001),
                new PIDController(0.5, 0, 0.001),
                new ProfiledPIDController(0, 0, 0,
                        new TrapezoidProfile.Constraints(Math.PI / 8, Math.PI / 16)));
//        controller.setTolerance(tolerance);
        // setup timer and add generated trajectory to glass
        timer.reset();
        timer.start();
        swerve.setTrajectory(trajectory);
    }

    @Override
    public void execute() {
        Trajectory.State state = trajectory.sample(timer.get());
        ChassisSpeeds speeds = controller.calculate(swerve.getPose(), state, new Rotation2d());
        swerve.driveFromChassisSpeeds(speeds);
    }

    @Override
    public boolean isFinished() {
        return controller.atReference() || timer.get() > 5;
    }

    @Override
    public void end(boolean terminated) {
        swerve.driveFromChassisSpeeds(new ChassisSpeeds());
    }

}
