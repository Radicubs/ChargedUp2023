package frc.robot.commands.common.gripperup;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Shoulder;
import frc.robot.subsystems.Swerve;

public class GripperUp extends SequentialCommandGroup {

    public GripperUp(Swerve swerve, Arm arm, Shoulder shoulder) {
        addCommands(new ArmUp(arm), new ShoulderUp(shoulder));
    }

}
