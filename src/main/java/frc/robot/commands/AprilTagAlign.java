package frc.robot.commands;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.geometry.Translation3d;
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
    private boolean done = false;
    private final Transform3d offset;

    //TODO: Replace with constants
    /*private final PIDController xspeed = new PIDController(-0.22, 0, 0.01);
    private final PIDController yspeed = new PIDController(-0.22, 0, 0.01);
    private final PIDController zrot = new PIDController(-0.07, 0, 0);*/
    public AprilTagAlign(PhotonVision camera, Swerve base, int tagnum, TagAlignment alignment) {
        this(camera, base, tagnum, alignment.offset);
    }

    public AprilTagAlign(PhotonVision camera, Swerve base, int tagnum, Transform3d offset) {
        this.tagnum = tagnum;
        this.base = base;
        this.camera = camera;
        this.offset = offset.inverse();
        addRequirements(camera, base);
        /*xspeed.setSetpoint(0.25);
        xspeed.setTolerance(0.25);
        yspeed.setSetpoint(0);
        yspeed.setTolerance(0.15);
        zrot.setSetpoint(Math.PI);
        zrot.setTolerance(Units.degreesToRadians(10));*/
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

        Transform3d pose = target.getBestCameraToTarget().plus(offset);
        double posX = pose.getX();
        double posY = pose.getY();
        double rotZ = pose.getRotation().getZ();
        double yaw = target.getYaw();

        


        if(rotZ < 0){
            rotZ += Math.PI;
        }
        else{
            rotZ -= Math.PI;
        }

        ChassisSpeeds speeds = new ChassisSpeeds();

        if(Math.abs(yaw) > 6.5){
            if(yaw > 0){
                speeds.omegaRadiansPerSecond = 0.25;
            }
            else{
                speeds.omegaRadiansPerSecond = -0.25;
            }
        }
        else{
            if(Math.abs(posX - 0.5) < 0.05){
                done = true;
                return;
            }
            if(posX < 0.5){
                speeds.vxMetersPerSecond = -0.2;
            }
            else if(posX > 0.85){
                speeds.vxMetersPerSecond = 0.5;
            }
            else{
                speeds.vxMetersPerSecond = 0.2;
            }
        }

        base.driveFromChassisSpeeds(speeds);

        SmartDashboard.putNumber("Z Rotation", rotZ);
        SmartDashboard.putNumber("Apriltag Yaw", yaw);
        SmartDashboard.putNumber("X Pos", posX);
        SmartDashboard.putNumber("Y Pos", posY);
    }

    @Override
    public void end(boolean interrupted) {
        base.driveFromChassisSpeeds(new ChassisSpeeds());
    }

    @Override
    public boolean isFinished() {
        //return xspeed.atSetpoint() && yspeed.atSetpoint() && zrot.atSetpoint();
        return done;
    }

    private static double speedClamp(double val) { // TODO: Replace with constants
        return MathUtil.clamp(MathUtil.applyDeadband(val, 0.1), -4.5, 4.5);
    }

    private static double rotClamp(double val) { // TODO: Replace with constants
        return MathUtil.clamp(MathUtil.applyDeadband(val, 0.1), -Math.PI, Math.PI);
    }

    public enum TagAlignment {
        // TODO: change x offset in accordance to camera position on robot
        LEFT(new Transform3d(new Translation3d(0.40639, 0.541, 0), new Rotation3d())),
        RIGHT(new Transform3d(new Translation3d(0.40639, -0.541, 0), new Rotation3d())),
        CENTER(new Transform3d(new Translation3d(0.40639, 0, 0), new Rotation3d()));

        public final Transform3d offset;

        TagAlignment(Transform3d offset) {
            this.offset = offset;
        }
    }
}
