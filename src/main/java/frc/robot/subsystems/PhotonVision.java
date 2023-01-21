package frc.robot.subsystems;

import org.photonvision.PhotonCamera;
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

    public boolean seeTarget(int targetID){
        PhotonPipelineResult result = camera.getLatestResult();
        if(!result.hasTargets())
            return false;
        
        for(PhotonTrackedTarget target : result.getTargets()){
            if(target.getFiducialId() == targetID)
                return true;
        }
        return false;
    }

    
}
