package frc.robot.commands.teleop;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.lib.util.subchooser.SettableSubsystem;
import frc.lib.util.subchooser.SubsystemChooserSupplier;

import java.util.function.DoubleSupplier;

public class SubsystemControlCommand extends CommandBase {

    private final SettableSubsystem subsystem;
    private final SubsystemChooserSupplier sub;
    private final DoubleSupplier left;
    private final DoubleSupplier right;

    public SubsystemControlCommand(SettableSubsystem subsystem, DoubleSupplier left, DoubleSupplier right,
                                   SubsystemChooserSupplier sub) {
        this.left = left;
        this.right = right;
        this.sub = sub;
        this.subsystem = subsystem;
        addRequirements(subsystem);
    }

    @Override
    public void execute() {
        if(sub.getAsChooser() == subsystem.getType())
            subsystem.set(deadband(left.getAsDouble() - right.getAsDouble()));

        else subsystem.set(0);
    }

    private static double deadband(double value) {
        if(Math.abs(value) < 0.01) return 0;
        return value;
    }
}
