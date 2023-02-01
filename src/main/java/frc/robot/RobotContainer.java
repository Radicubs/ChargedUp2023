package frc.robot;

import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

import frc.robot.commands.*;
import frc.robot.subsystems.*;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
    private final Joystick driver = new Joystick(0);

    private final JoystickButton zeroGyro = new JoystickButton(driver, XboxController.Button.kY.value);
    private final JoystickButton robotCentric = new JoystickButton(driver, XboxController.Button.kLeftBumper.value);
    private final JoystickButton goForward = new JoystickButton(driver, XboxController.Button.kRightBumper.value);
    private final JoystickButton move = new JoystickButton(driver, XboxController.Button.kA.value);

    private final Swerve swerve;
    private final PhotonVision camera;
    private final Navx navx;


    public RobotContainer() {
        camera = new PhotonVision();
        navx = new Navx();
        swerve = new Swerve(navx::getYaw);
        swerve.setDefaultCommand(
            new TeleopSwerve(
                    swerve,
                () -> -driver.getRawAxis(XboxController.Axis.kLeftY.value),
                () -> -driver.getRawAxis(XboxController.Axis.kLeftX.value),
                () -> -driver.getRawAxis(XboxController.Axis.kRightX.value)));

        // Configure the button bindings
        configureButtonBindings();
    }

    private void configureButtonBindings() {
        robotCentric.toggleOnTrue(new InstantCommand(swerve::toggleFieldOriented));

    }

    public Command getAutonomousCommand() {
        return new AprilTagAlign(camera, swerve, 1, new Transform3d());
    }
}
