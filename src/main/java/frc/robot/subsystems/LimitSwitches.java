package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class LimitSwitches extends SubsystemBase {

    private final DigitalInput armExtensionButton;
    private final DigitalInput shoulderButton;

    public LimitSwitches() {
        armExtensionButton = new DigitalInput(1);
        shoulderButton = new DigitalInput(2);
    }

    public boolean armExtensionButton() {
        return armExtensionButton.get();
    }

    public boolean shoulderButton() {return shoulderButton.get();}

}
