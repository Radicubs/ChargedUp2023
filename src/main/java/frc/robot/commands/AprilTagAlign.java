package frc.robot.commands;

import frc.robot.Constants;
import org.photonvision.targeting.PhotonTrackedTarget;

import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.PhotonVision;
import frc.robot.subsystems.Swerve;

public class AprilTagAlign extends CommandBase {

    private final int tagnum;
    private final Swerve base;
    private final PhotonVision camera;

    private boolean inXRange = false;
    private boolean inYRange = false;
    private boolean inRotRange = false;

    private final double targetDistance = 0.5;

    public AprilTagAlign(PhotonVision camera, Swerve base, int tagnum) {
        this.tagnum = tagnum;
        this.base = base;
        this.camera = camera;
        addRequirements(camera);
        addRequirements(base);
    }

    @Override
    public void initialize() {
        base.driveFromChassisSpeeds(new ChassisSpeeds());
    }

    @Override
    public void execute() {
        PhotonTrackedTarget target = camera.getTarget(tagnum);
        if(target == null) {
            return;
        }
        
        double rot;
        ChassisSpeeds speeds = new ChassisSpeeds();

        Transform3d pose = target.getBestCameraToTarget();
        double posX = pose.getX();
        double posY = pose.getY();
        double rotZ = pose.getRotation().getZ();

        //STEPS
        //Rotate Chassis until rot Z is within a bound close to 180
        //Move left/right until posY is within a bound close to 0
        //Move forward/back until posX is within a set bound
        //      Experiment until it ends up a certain distance from the tag
        //      The units are supposed to be in meters, but the calibration isn't great

        
        //LOOK AT LAST YEAR'S LIMELIGHT ALIGN FUNCTION TO FIGURE OUT HOW TO DO THIS lol...

        speeds.vxMetersPerSecond = PIDxspeed(posX);
        speeds.vyMetersPerSecond = PIDyspeed(posY);
        speeds.omegaRadiansPerSecond = PIDZRot(rotZ);


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
    public boolean isFinished(){
        return inXRange && inYRange && inRotRange;
    }

    public double PIDxspeed (double pos)
    {
        if (pos > 4)
            return 2;
        return  (.5 * (pos - .3));

    }

    public double PIDyspeed (double pos)
    {
        if (pos > 4)
            return -2;
        if (pos < -4)
            return 2;
        return  .5 * pos;
    }

    public double PIDZRot (double ZRot)
    {
        return ZRot/3;
    }
}
