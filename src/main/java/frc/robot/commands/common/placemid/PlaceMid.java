package frc.robot.commands.common.placemid;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Gripper;
import frc.robot.subsystems.Shoulder;

public class PlaceMid extends SequentialCommandGroup {

    public PlaceMid(Shoulder shoulder, Arm arm) {
        addCommands(new ArmUp(arm), new ShoulderOut(shoulder), new ExtendAndPlace(shoulder, arm));
    }

}
