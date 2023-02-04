package frc.robot.commands.P2;

import frc.robot.commands.common.AprilTagAlign;
import frc.robot.subsystems.PhotonVision;
import frc.robot.subsystems.Swerve;

import java.util.function.DoubleSupplier;

public class MidRisk extends P2AutoCommand {

    // Scores preloaded game piece, then goes for station
    public MidRisk(Swerve swerve, PhotonVision vision, DoubleSupplier roll, boolean isBlue) {
        super(swerve, isBlue);
        addCommands(
                new AprilTagAlign(swerve, vision, isBlue ? 7 : 2,
                        isBlue ? AprilTagAlign.TagAlignment.LEFT : AprilTagAlign.TagAlignment.RIGHT),
                // score command
                new LowRiskStation(swerve, roll, isBlue));
    }

}