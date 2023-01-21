package frc.robot.subsystems;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Transform2d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.util.Units;
import org.photonvision.PhotonCamera;
import org.photonvision.PhotonUtils;
import org.photonvision.targeting.PhotonPipelineResult;
import org.photonvision.targeting.PhotonTrackedTarget;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class PhotonVision extends SubsystemBase{
    //the string is the name of the NetworkTable that PhotonVision is broadcasting info over
    //it is the same as the camera's nickname from PhotonVision UI
    PhotonCamera camera;

    public PhotonVision(){
        camera = new PhotonCamera("photonvision");
    }

    public Translation2d getTarget(int targetID){
        PhotonPipelineResult result = camera.getLatestResult();
        if(!result.hasTargets())
            return null;
        
        for(PhotonTrackedTarget target : result.getTargets()){
            if(target.getFiducialId() == targetID) {
                Translation2d translation = PhotonUtils.estimateCameraToTargetTranslation(PhotonUtils.calculateDistanceToTargetMeters(0.1625, 66.5, 0, Units.degreesToRadians(result.getBestTarget().getPitch())), Rotation2d.fromDegrees(-target.getYaw()));
                return translation;
            }
        }
        return null;
    }

    
}
