package frc.robot.commands.P0;

import frc.robot.commands.common.AprilTagAlign;
import frc.robot.commands.common.SequentialCommand;
import frc.robot.subsystems.PhotonVision;
import frc.robot.subsystems.Swerve;

import java.util.function.DoubleSupplier;

public class MidRisk extends SequentialCommand {

    // Scores preloaded game piece, then goes for station
    public MidRisk(Swerve swerve, PhotonVision vision, DoubleSupplier roll, boolean isBlue) {
        super(isBlue);
        addCommands(
                new AprilTagAlign(swerve, vision, isBlue ? 8 : 1,
                        isBlue ? AprilTagAlign.TagAlignment.LEFT : AprilTagAlign.TagAlignment.RIGHT),
                // score command
                new LowRiskStation(swerve, roll, isBlue));
    }

}
