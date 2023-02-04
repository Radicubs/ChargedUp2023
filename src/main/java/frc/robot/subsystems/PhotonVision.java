package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import org.photonvision.PhotonCamera;
import org.photonvision.targeting.PhotonPipelineResult;
import org.photonvision.targeting.PhotonTrackedTarget;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class PhotonVision extends SubsystemBase {
    //the string is the name of the NetworkTable that PhotonVision is broadcasting info over
    //it is the same as the camera's nickname from PhotonVision UI
    private final PhotonCamera camera;

    public PhotonVision() {
        camera = new PhotonCamera("raspberry");
    }

    public PhotonTrackedTarget getTarget(int targetID) {
        PhotonPipelineResult result = camera.getLatestResult();
        if (!result.hasTargets())
            return null;

        for (PhotonTrackedTarget target : result.getTargets()) {
            if (target.getFiducialId() == targetID) {
                //Translation2d translation = PhotonUtils.estimateCameraToTargetTranslation(PhotonUtils.calculateDistanceToTargetMeters(Constants.PhotonVision.cameraHeightMeters, Constants.PhotonVision.targetHeightMeters, Constants.PhotonVision.cameraPitchRadians, Units.degreesToRadians(target.getPitch())), Rotation2d.fromDegrees(-target.getYaw()));
                return target;
            }
        }
        return null;
    }
}
