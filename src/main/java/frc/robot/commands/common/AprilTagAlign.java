package frc.robot.commands.common;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.PhotonVision;
import frc.robot.subsystems.Swerve;
import frc.robot.Constants;
import javax.swing.GroupLayout.Alignment;

import org.photonvision.targeting.PhotonTrackedTarget;

public class AprilTagAlign extends CommandBase {

    private final int tagnum;
    private final Swerve base;
    private final PhotonVision camera;
    private boolean done = false;
    private final double offset;

    public AprilTagAlign(Swerve base, PhotonVision vision, int tagnum, TagAlignment alignment) {
        this(base, vision, tagnum, alignment.offset);
    }

    public AprilTagAlign(Swerve base, PhotonVision vision, int tagnum, double offset) {
        this.tagnum = tagnum;
        this.base = base;
        this.camera = vision;
        this.offset = offset;
        addRequirements(vision, base);
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

        Transform3d pose = target.getBestCameraToTarget();;
        double posX = pose.getX();
        double posY = pose.getY() + offset;
        double rotZ = pose.getRotation().getZ();
        double yaw = target.getYaw();

        if(rotZ < 0){
            rotZ += Math.PI;
        }
        else{
            rotZ -= Math.PI;
        }

        ChassisSpeeds speeds = new ChassisSpeeds();
        
        // must be within acceptable yaw before it starts moving fw
        if(Math.abs(yaw) > 8){
            speeds.omegaRadiansPerSecond = (yaw > 0) ? 0.25 : -0.25;
            base.driveFromChassisSpeeds(speeds);
            return;
        }


        if(Math.abs(yaw) > 4){
            // yaw correction
            speeds.omegaRadiansPerSecond = (yaw > 0) ? 0.25 : -0.25;
        }
        if(Math.abs(posX - 0.5) < 0.05){
            if(Math.abs(rotZ) < 0.05){
                done = true;
                System.out.println(rotZ);
                return;
            }
            else{
                System.out.println("Rotating around outside point");
                base.rotateAroundPoint(new Translation2d(-0.5 - (Constants.Swerve.wheelBase / 2), 0), (yaw > 0) ? -0.3 : 0.3);
            }
            return;
        }
        else if(Math.abs(posX - 0.5) < 0.4){
            // move slow to target if far
            speeds.vxMetersPerSecond = (posX > 0.5) ? 0.2 : -0.2;
        }
        else{
            speeds.vxMetersPerSecond = (posX > 0.5) ? 0.5 : -0.5;
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
        return done;
    }

    public enum TagAlignment {
        // TODO: change x offset in accordance to camera position on robot
        LEFT(0.541),
        RIGHT(-0.541),
        CENTER(0);

        public final double offset;

        TagAlignment(double offset) {
            this.offset = offset;
        }
    }
}
