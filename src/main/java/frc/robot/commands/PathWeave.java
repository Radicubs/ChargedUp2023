package frc.robot.commands;

import edu.wpi.first.math.controller.RamseteController;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrajectoryConfig;
import edu.wpi.first.math.trajectory.TrajectoryGenerator;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.RamseteCommand;
import frc.robot.Constants;
import frc.robot.subsystems.Swerve;

import java.util.List;

public class PathWeave extends CommandBase {

    private final Swerve swerve;
    private final RamseteController controller;
    private final Trajectory trajectory;
    private final Timer timer;

    public PathWeave(Swerve swerve) {
        this.swerve = swerve;
        addRequirements(swerve);
        timer = new Timer();
        controller = new RamseteController();
        TrajectoryConfig config = new TrajectoryConfig(Constants.Swerve.maxSpeed, Constants.Swerve.maxSpeed)
                .setKinematics(Constants.Swerve.swerveKinematics);
        trajectory = TrajectoryGenerator.generateTrajectory(
                new Pose2d(),
                List.of(new Translation2d(1, 1), new Translation2d(2, -1)),
                new Pose2d(3, 0, new Rotation2d()),
                config);
    }

    @Override
    public void initialize() {
        swerve.driveFromChassisSpeeds(new ChassisSpeeds());
    }

    @Override
    public void execute() {
        swerve.driveFromChassisSpeeds(controller.calculate(swerve.getPose(), trajectory.sample(timer.get())));
    }

    @Override
    public void end(boolean interrupted) {
        swerve.driveFromChassisSpeeds(new ChassisSpeeds());
    }
}
