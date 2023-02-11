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

public class SpinAroundTarget extends CommandBase {

    private final int tagnum;
    private final Swerve base;
    private final PhotonVision camera;
    private final double distance;
    private boolean done = false;

    public SpinAroundTarget(Swerve base, PhotonVision vision, int tagnum, double distance) {
        this.tagnum = tagnum;
        this.base = base;
        this.camera = vision;
        this.distance = distance;
        addRequirements(vision, base);
    }

    @Override
    public void initialize() {
        base.driveFromChassisSpeeds(new ChassisSpeeds());
        SmartDashboard.putString("Apriltag Align Phase", "Spinning around target");
    }

    private double rotZToSpeeds(double rotZ){
        return -MathUtil.clamp(rotZ*0.75, 0.1, 0.5);
    }

    @Override
    public void execute() {
        PhotonTrackedTarget target = camera.getTarget(tagnum);
        if (target == null) {
            base.driveFromChassisSpeeds(new ChassisSpeeds());
            return;
        }

        Transform3d pose = target.getBestCameraToTarget();
        double rotZ = pose.getRotation().getZ();

        if(rotZ < 0){
            rotZ += Math.PI;
        }
        else{
            rotZ -= Math.PI;
        }        

        
        if(Math.abs(rotZ) < 0.03){
            done = true;
            System.out.println(rotZ);
        }
        base.rotateAroundPoint(new Translation2d(-distance - (Constants.Swerve.wheelBase / 2), 0), rotZToSpeeds(rotZ));
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
