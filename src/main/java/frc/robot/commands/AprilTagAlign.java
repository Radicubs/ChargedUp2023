package frc.robot.commands;

import edu.wpi.first.math.geometry.Translation2d;
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
        Translation2d relativePos = camera.getTarget(tagnum);
        if(relativePos == null) {
            System.out.println("heloooo");

            return;
        }
        SmartDashboard.putNumber("x distance", relativePos.getX());
        SmartDashboard.putNumber("Y distance", relativePos.getY());
    }

}
