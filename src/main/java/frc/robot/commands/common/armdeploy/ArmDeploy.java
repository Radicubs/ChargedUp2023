package frc.robot.commands.common.armdeploy;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.LimitSwitches;
import frc.robot.subsystems.Shoulder;

public class ArmDeploy extends SequentialCommandGroup {

    public ArmDeploy(LimitSwitches switches, Arm arm, Shoulder shoulder) {
        addCommands(
                new ArmUp(switches, arm),
                new ShoulderBack(switches, shoulder)
        );
    }

}
