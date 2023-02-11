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

public class AdjustToFinalPosition extends CommandBase {

    private final int tagnum;
    private final Swerve base;
    private final PhotonVision camera;
    private final double distance;
    private final double offset;
    private boolean doneX, doneY, doneRot;
    

    public AdjustToFinalPosition(Swerve base, PhotonVision vision, int tagnum, double distance, double offset) {
        this.tagnum = tagnum;
        this.base = base;
        this.camera = vision;
        this.distance = distance;
        this.offset = offset;
        addRequirements(vision, base);
    }

    @Override
    public void initialize() {
        base.driveFromChassisSpeeds(new ChassisSpeeds());
        SmartDashboard.putString("Apriltag Align Phase", "Adjusting to offset");
    }

    private double posYToSpeeds(double posY){
        return Math.min(0.75, 1.875*posY);
    }

    @Override
    public void execute() {
        doneX = false; doneY = false; doneRot = false;
        PhotonTrackedTarget target = camera.getTarget(tagnum);
        if (target == null) {
            base.driveFromChassisSpeeds(new ChassisSpeeds());
            return;
        }

        Transform3d pose = target.getBestCameraToTarget();;
        double posX = pose.getX();
        double posY = pose.getY();
        double rotZ = pose.getRotation().getZ();

        rotZ += (rotZ > 0) ? -Math.PI : Math.PI;
        ChassisSpeeds speeds = new ChassisSpeeds();
        
        if(Math.abs(rotZ) > 0.007){
            // Rotation correction
            speeds.omegaRadiansPerSecond = (rotZ > 0) ? -0.14 : 0.14;
        }
        else{
            doneRot = true;
        }
        
        //for x distance
        if(Math.abs(posX - distance) < 0.05){
            doneX = true;
        }
        else{
            speeds.vxMetersPerSecond = (posX > distance) ? 0.1 : -0.1;
        }

        //for y distance
        if(Math.abs(posY - offset) < 0.05){
            doneY = true;
        }
        speeds.vyMetersPerSecond = posYToSpeeds(posY-offset);

        base.driveFromChassisSpeeds(speeds);
    }

    @Override
    public void end(boolean interrupted) {
        base.driveFromChassisSpeeds(new ChassisSpeeds());
    }

    @Override
    public boolean isFinished() {
        return doneX && doneY && doneRot;
    }
}
