package frc.robot.commands.P2;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import frc.robot.commands.P1.P1AutoCommand;
import frc.robot.commands.common.ChargeStationAlign;
import frc.robot.commands.common.PathWeave;
import frc.robot.commands.common.SequentialCommand;
import frc.robot.subsystems.Swerve;

import java.util.function.DoubleSupplier;

public class LowRiskStation extends P2AutoCommand { // low risk leaves the community and then docks on the charging station

    public LowRiskStation(Swerve swerve, DoubleSupplier roll, boolean isBlue) {
        super(swerve, isBlue);
        addCommands(
                new NoRisk(swerve, isBlue),
                new ChargeStationAlign(swerve, roll));
    }
}
