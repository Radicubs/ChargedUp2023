package frc.robot.commands.P3;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.commands.common.SequentialCommand;
import frc.robot.subsystems.Swerve;

public abstract class P3AutoCommand extends SequentialCommand {
    private final Pose2d startingPos = new Pose2d(new Translation2d(0.860, 4.404 * yMult), Rotation2d.fromDegrees(180));

    // Start 16.6 inches from the tape and 18.9 inches from the BASE of the divider
    public P3AutoCommand(Swerve swerve, boolean isBlue) {
        super(isBlue);
        addCommands(new InstantCommand(() -> swerve.resetOdo(startingPos)));
    }
}
