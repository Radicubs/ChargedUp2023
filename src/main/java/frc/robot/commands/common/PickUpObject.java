package frc.robot.commands.common;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.common.ArmSetCommands.*;
import frc.robot.subsystems.*;

public class PickUpObject extends SequentialCommandGroup{
    // 0 -> ground, 1 -> middle, 2 -> top

    
    private final Arm arm;
    private final Gripper gripper;
    private final Shoulder shoulder;
    // Steps: Angle arm up, extend arm, release grip

    public PickUpObject(Arm arm, Gripper gripper, Shoulder shoulder, boolean isCube, int level){
        this.arm = arm;
        this.gripper = gripper;
        this.shoulder = shoulder;

        
        addCommands(
            new SetGripperPosition(gripper, 10),
            new SetShoulderAngle(shoulder, 10),
            new SetArmLength(arm, 40),
            new SetGripperPosition(gripper, 0)
        );
    }
}
