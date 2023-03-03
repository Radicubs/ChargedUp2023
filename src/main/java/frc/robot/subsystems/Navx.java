package frc.robot.subsystems;

import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Navx extends SubsystemBase {

    private final AHRS gyro;

    public Navx() {
        gyro = new AHRS(I2C.Port.kOnboard);
        gyro.calibrate();
        gyro.reset();
    }

    public double getYaw() {
        return gyro.getYaw();
    }

    public double getRoll() {
        return gyro.getRoll();
    }

    public double getPitch() {
        return gyro.getPitch();
    }

    public Translation2d getPosition() {
        return new Translation2d(gyro.getDisplacementX(), gyro.getDisplacementY());
    }

    public void reset() {
        gyro.reset();
    }

}
