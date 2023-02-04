package frc.robot.commands.P3;

import frc.robot.commands.P3.LowRiskStation;
import frc.robot.commands.P3.P3AutoCommand;
import frc.robot.commands.common.AprilTagAlign;
import frc.robot.subsystems.PhotonVision;
import frc.robot.subsystems.Swerve;

import java.util.function.DoubleSupplier;

public class MidRisk extends P3AutoCommand {

    // Scores preloaded game piece, then goes for station
    public MidRisk(Swerve swerve, PhotonVision vision, DoubleSupplier roll, boolean isBlue) {
        super(swerve, isBlue);
        //TODO
        addCommands(
                new AprilTagAlign(swerve, vision, isBlue ? 6 : 3,
                        isBlue ? AprilTagAlign.TagAlignment.LEFT : AprilTagAlign.TagAlignment.RIGHT),
                // score command
                new LowRiskStation(swerve, roll, isBlue));
    }

}
