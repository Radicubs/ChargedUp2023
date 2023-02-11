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

public class AimAtTarget extends CommandBase {

    private final int tagnum;
    private final Swerve base;
    private final PhotonVision camera;
    private boolean done = false;


    public AimAtTarget(Swerve base, PhotonVision vision, int tagnum) {
        this.tagnum = tagnum;
        this.base = base;
        this.camera = vision;
        addRequirements(vision, base);
    }

    @Override
    public void initialize() {
        base.driveFromChassisSpeeds(new ChassisSpeeds());
        SmartDashboard.putString("Apriltag Align Phase", "Aiming at target");
    }

    private double yawToSpeeds(double yaw){
        return MathUtil.clamp(0.03*yaw, 0.1, 1);
    }

    @Override
    public void execute() {
        PhotonTrackedTarget target = camera.getTarget(tagnum);
        if (target == null) {
            base.driveFromChassisSpeeds(new ChassisSpeeds());
            return;
        }

        double yaw = target.getYaw();

        ChassisSpeeds speeds = new ChassisSpeeds();
        
        // must be within acceptable yaw before it starts moving fw
        speeds.omegaRadiansPerSecond = yawToSpeeds(yaw);
        base.driveFromChassisSpeeds(speeds);

        if(Math.abs(yaw) < 10){
            done = true;
        }
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
