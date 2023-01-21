package frc.robot.commands;

import org.photonvision.targeting.PhotonTrackedTarget;

import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.PhotonVision;
import frc.robot.subsystems.Swerve;

public class AprilTagAlign extends CommandBase {

    private final int tagnum;
    //private final Swerve base;
    private final PhotonVision camera;

    public AprilTagAlign(PhotonVision camera, int tagnum) {
        this.tagnum = tagnum;
        //this.base = base;
        this.camera = camera;
        addRequirements(camera);
    }

    @Override
    public void initialize() {
        //base.driveFromChassisSpeeds(new ChassisSpeeds());
    }

    @Override
    public void execute() {
        PhotonTrackedTarget target = camera.getTarget(tagnum);
        if(target == null) {
            return;
        }
        
        Transform3d pose = target.getBestCameraToTarget();
        SmartDashboard.putNumber("x distance", pose.getX());
        SmartDashboard.putNumber("Y distance", pose.getY());
        SmartDashboard.putNumber("Yaw Rotation", target.getYaw());
    }
}
