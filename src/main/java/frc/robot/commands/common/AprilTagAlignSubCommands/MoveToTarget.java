package frc.robot.commands.common.AprilTagAlignSubCommands;

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

public class MoveToTarget extends CommandBase {

    private final int tagnum;
    private final Swerve base;
    private final PhotonVision camera;
    private final double distance;
    private boolean done = false;
    

    public MoveToTarget(Swerve base, PhotonVision vision, int tagnum, double distance) {
        this.tagnum = tagnum;
        this.base = base;
        this.camera = vision;
        this.distance = distance;
        addRequirements(vision, base);
    }

    @Override
    public void initialize() {
        base.driveFromChassisSpeeds(new ChassisSpeeds());
    }

    private double posXToSpeeds(double posX){
        return MathUtil.clamp(1.875*posX, 0.1, 0.75);
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
        double yaw = target.getYaw();

        ChassisSpeeds speeds = new ChassisSpeeds();
        
        if(Math.abs(yaw) > 4){
            // yaw correction
            speeds.omegaRadiansPerSecond = (yaw > 0) ? 0.1 : -0.1;
        }


        if(Math.abs(posX - distance) < 0.05){
            done = true;
            return;
        }
        speeds.vxMetersPerSecond = posXToSpeeds(posX-distance);

        base.driveFromChassisSpeeds(speeds);
    }

    @Override
    public void end(boolean interrupted) {
        base.driveFromChassisSpeeds(new ChassisSpeeds());
    }

    @Override
    public boolean isFinished() {
        return done;
    }
}
