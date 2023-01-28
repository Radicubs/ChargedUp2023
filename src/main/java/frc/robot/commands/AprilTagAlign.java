package frc.robot.commands;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.PhotonVision;
import frc.robot.subsystems.Swerve;
import org.photonvision.targeting.PhotonTrackedTarget;

public class AprilTagAlign extends CommandBase {

    private final int tagnum;
    private final Swerve base;
    private final PhotonVision camera;

    //TODO: Replace with constants
    private final PIDController xspeed = new PIDController(-0.22, 0, 0.01);
    private final PIDController yspeed = new PIDController(-0.22, 0, 0.01);
    private final PIDController zrot = new PIDController(-0.07, 0, 0);

    public AprilTagAlign(PhotonVision camera, Swerve base, int tagnum) {
        this.tagnum = tagnum;
        this.base = base;
        this.camera = camera;
        addRequirements(camera, base);
        xspeed.setSetpoint(0.25);
        xspeed.setTolerance(0.25);
        yspeed.setSetpoint(0);
        yspeed.setTolerance(0.15);
        zrot.setSetpoint(Math.PI);
        zrot.setTolerance(Units.degreesToRadians(10));
    }

    @Override
    public void initialize() {
        base.driveFromChassisSpeeds(new ChassisSpeeds());
    }

    @Override
    public void execute() {
        PhotonTrackedTarget target = camera.getTarget(tagnum);
        if (target == null) {
            base.driveFromChassisSpeeds(new ChassisSpeeds());
            return;
        }

        Transform3d pose = target.getBestCameraToTarget();
        double posX = pose.getX();
        double posY = pose.getY();
        double rotZ = pose.getRotation().getZ();

        ChassisSpeeds speeds = new ChassisSpeeds(
                speedClamp(xspeed.calculate(posX)), speedClamp(yspeed.calculate(posY)), rotClamp(zrot.calculate(rotZ)));

        base.driveFromChassisSpeeds(speeds);

        SmartDashboard.putNumber("Z Rotation", rotZ);
        SmartDashboard.putNumber("X Pos", posX);
        SmartDashboard.putNumber("Y Pos", posY);
    }

    @Override
    public void end(boolean interrupted) {
        base.driveFromChassisSpeeds(new ChassisSpeeds());
    }

    @Override
    public boolean isFinished() {
        return xspeed.atSetpoint() && yspeed.atSetpoint() && zrot.atSetpoint();
    }

    private static double speedClamp(double val) { // TODO: Replace with constants
        return MathUtil.clamp(MathUtil.applyDeadband(val, 0.1), -4.5, 4.5);
    }

    private static double rotClamp(double val) { // TODO: Replace with constants
        return MathUtil.clamp(MathUtil.applyDeadband(val, 0.1), -Math.PI, Math.PI);
    }
}
