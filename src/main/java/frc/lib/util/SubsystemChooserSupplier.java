package frc.lib.util;

@FunctionalInterface
public interface SubsystemChooserSupplier {

    SubsystemChooserEnum getAsChooser();

}
