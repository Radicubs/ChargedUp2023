package frc.robot.commands.teleop;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.math.geometry.Translation2d;
import frc.robot.subsystems.Swerve;
import frc.robot.Constants;

public class MoveCommand extends CommandBase {

    private final Swerve base;

    public MoveCommand(Swerve base) {
        this.base = base;
        addRequirements(base);
    }

    @Override
    public void initialize() {
        base.driveFromChassisSpeeds(new ChassisSpeeds(0, 0.3, Units.degreesToRadians(0)));
    }

    @Override
    public void execute() {
//        if((Math.abs(base.swerveOdometry.getPoseMeters().getX() - 0.3) < 0.01)) {
//            base.driveFromChassisSpeeds(new ChassisSpeeds(0, 0.5, 0));
//        }
//
//        if((Math.abs(base.swerveOdometry.getPoseMeters().getY() - 0.3) < 0.01)) {
//            base.driveFromChassisSpeeds(new ChassisSpeeds(0.5, 0, 0));
//        }
        base.rotateAroundPoint(new Translation2d(-0.5 -(Constants.Swerve.wheelBase / 2), 0), -0.4);
    }

    @Override
    public boolean isFinished() {
        return false;
        //return Math.abs(base.swerveOdometry.getPoseMeters().getX() - 0.3) < 0.01 && Math.abs(base.swerveOdometry.getPoseMeters().getY() - 0.3) < 0.01;
    }

    @Override
    public void end(boolean interrupted) {
        base.driveFromChassisSpeeds(new ChassisSpeeds());
    }
}
