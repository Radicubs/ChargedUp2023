package frc.robot.commands.common;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.PhotonVision;
import frc.robot.subsystems.Swerve;

import java.util.function.DoubleSupplier;

public abstract class SequentialCommand extends SequentialCommandGroup {

    protected final int yMult;

    public SequentialCommand(boolean isBlue) {
        yMult = isBlue ? 1 : -1;
    }

}
