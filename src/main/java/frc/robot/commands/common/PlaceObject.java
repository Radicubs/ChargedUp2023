package frc.robot.commands.common;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.common.ArmSetCommands.*;
import frc.robot.subsystems.*;

public class PlaceObject extends SequentialCommandGroup{
    // 0 -> ground, 1 -> middle, 2 -> top
    private final int level;
    private final boolean isCube;

    double[][] angles = {
        //cube
        {10, 30, 50},
        //cone
        {20, 40, 60}
    };

    double[][] lengths = {
        //cube
        {40, 50, 60},
        //cone
        {40, 50, 60}
    };
    
    private final Arm arm;
    private final Gripper gripper;
    private final Shoulder shoulder;
    // Steps: Angle arm up, extend arm, release grip

    public PlaceObject(Arm arm, Gripper gripper, Shoulder shoulder, boolean isCube, int level){
        this.arm = arm;
        this.gripper = gripper;
        this.shoulder = shoulder;
        this.isCube = isCube;
        this.level = level;
        
        addCommands(
            new SetShoulderAngle(shoulder, angles[isCube ? 0 : 1][level]),
            new SetArmLength(arm, lengths[isCube ? 0 : 1][level]),
            new SetGripperPosition(gripper, 10)
        );
    }
}
