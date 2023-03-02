package frc.lib.util.subchooser;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public abstract class SettableSubsystem extends SubsystemBase {
    public abstract void set(double setpoint);
    public abstract SubsystemChooserEnum getType();

}
