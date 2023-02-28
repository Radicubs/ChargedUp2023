package frc.lib.util;

import frc.robot.subsystems.SubsystemChooser;

@FunctionalInterface
public interface SubsystemChooserProvider {

    SubsystemChooser.SubsystemChooserEnum getAsChooser();

}
