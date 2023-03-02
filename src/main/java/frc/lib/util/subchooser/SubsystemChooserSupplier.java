package frc.lib.util.subchooser;

@FunctionalInterface
public interface SubsystemChooserSupplier {

    SubsystemChooserEnum getAsChooser();

}
