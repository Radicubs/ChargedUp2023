package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

import frc.robot.commands.P0.P0CommandGenerator;
import frc.robot.commands.P1.P1CommandGenerator;
import frc.robot.commands.P2.P2CommandGenerator;
import frc.robot.commands.P3.P3CommandGenerator;
import frc.robot.commands.common.AutoCommandGenerator;
import frc.robot.commands.teleop.TeleopSwerve;
import frc.robot.commands.tests.TestCommandGenerator;
import frc.robot.subsystems.*;

import java.util.function.DoubleSupplier;

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
    private final Gripper gripper;

    private final SendableChooser<Boolean> allianceColor;
    private final SendableChooser<StartingPosition> startingPos;
    private final SendableChooser<AutoDifficulty> difficulty;



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
        gripper = new Gripper(() -> driver.getRawAxis(XboxController.Axis.kLeftTrigger.value),
                () -> driver.getRawAxis(XboxController.Axis.kRightTrigger.value));

        // Configure the button bindings
        configureButtonBindings();

        allianceColor = new SendableChooser<>();
        allianceColor.setDefaultOption("Blue", true);
        allianceColor.addOption("Red", false);
        SmartDashboard.putData("Alliance Color", allianceColor);

        startingPos = new SendableChooser<>();
        for(StartingPosition pos : StartingPosition.values()) startingPos.addOption(pos.toString(), pos);
        SmartDashboard.putData("Starting Position", startingPos);

        difficulty = new SendableChooser<>();
        for(AutoDifficulty diff : AutoDifficulty.values()) difficulty.addOption(diff.toString(), diff);
        SmartDashboard.putData("Auto Difficulty", difficulty);

        SmartDashboard.updateValues();
    }

    private void configureButtonBindings() {
        robotCentric.toggleOnTrue(new InstantCommand(swerve::toggleFieldOriented));

    }



    public Command getAutonomousCommand() {
        if(startingPos.getSelected() == null) return null;
        return startingPos.getSelected().generate(swerve, camera, navx::getRoll, allianceColor.getSelected(), difficulty.getSelected());
    }

    public enum AutoDifficulty {
        NoRisk,
        LowRiskPlace,
        LowRiskStation,
        MidRisk,
        HighRisk,
        Impossible
    }

    private enum StartingPosition {
        P0(new P0CommandGenerator()),
        P1(new P1CommandGenerator()),
        P2(new P2CommandGenerator()),
        P3(new P3CommandGenerator()),
        TESTS(new TestCommandGenerator());

        private final AutoCommandGenerator generator;

        public Command generate(Swerve swerve, PhotonVision vision, DoubleSupplier roll, boolean alliance, RobotContainer.AutoDifficulty difficulty) {
            return generator.generate(swerve, vision, roll, alliance, difficulty);
        }

        StartingPosition(AutoCommandGenerator generator) {
            this.generator = generator;
        }
    }
}
