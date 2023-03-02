package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

import frc.lib.util.auto.AutoDifficulty;
import frc.lib.util.auto.StartingPosition;
import frc.robot.commands.common.AprilTagAlign;
import frc.robot.commands.teleop.SubsystemControlCommand;
import frc.robot.commands.teleop.TeleopSwerve;
import frc.robot.subsystems.*;

import java.util.function.DoubleSupplier;

public class RobotContainer {

    // Joysticks
    private final Joystick driver = new Joystick(0);
    private final Joystick buttonBoard = new Joystick(1);

    // Buttons
    private final JoystickButton leftAlign;
    private final JoystickButton centerAlign;
    private final JoystickButton rightAlign;
    private final JoystickButton slowmode;
    private final JoystickButton fieldOriented;

    // Subsystems
    private final Swerve swerve;
    private final PhotonVision camera;
    private final Navx navx;
    private final SubsystemChooser subchooser;

    private final Shoulder shoulder;
    private final Arm arm;
    private final Gripper gripper;

    // Sendables
    private final SendableChooser<Boolean> allianceColor;
    private final SendableChooser<StartingPosition> startingPos;
    private final SendableChooser<AutoDifficulty> difficulty;

    public RobotContainer() {
        subchooser = new SubsystemChooser(driver::getPOV);
        camera = new PhotonVision();
        navx = new Navx();
        swerve = new Swerve(navx::getYaw);
        swerve.setDefaultCommand(
            new TeleopSwerve(
                    swerve,
                () -> -driver.getRawAxis(XboxController.Axis.kLeftY.value),
                () -> -driver.getRawAxis(XboxController.Axis.kLeftX.value),
                () -> -driver.getRawAxis(XboxController.Axis.kRightX.value)));

        DoubleSupplier left = () -> driver.getRawAxis(XboxController.Axis.kLeftTrigger.value);
        DoubleSupplier right = () -> driver.getRawAxis(XboxController.Axis.kRightTrigger.value);

        shoulder = new Shoulder();
        shoulder.setDefaultCommand(new SubsystemControlCommand(shoulder, left, right, subchooser::getSub));

        arm = new Arm();
        arm.setDefaultCommand(new SubsystemControlCommand(arm, left, right, subchooser::getSub));

        gripper = new Gripper();
        gripper.setDefaultCommand(new SubsystemControlCommand(gripper, left, right, subchooser::getSub));

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

        leftAlign = new JoystickButton(buttonBoard, 1);
        centerAlign = new JoystickButton(buttonBoard, 2);
        rightAlign = new JoystickButton(buttonBoard, 3);
        slowmode = new JoystickButton(buttonBoard, 4);
        fieldOriented = new JoystickButton(driver, XboxController.Button.kLeftBumper.value);

        configureButtonBindings();
    }

    private void configureButtonBindings() {
        leftAlign.whileTrue(new AprilTagAlign(swerve, camera, camera.findNearestTag(), AprilTagAlign.TagAlignment.LEFT));
        centerAlign.whileTrue(new AprilTagAlign(swerve, camera, camera.findNearestTag(), AprilTagAlign.TagAlignment.CENTER));
        rightAlign.whileTrue(new AprilTagAlign(swerve, camera, camera.findNearestTag(), AprilTagAlign.TagAlignment.RIGHT));
        slowmode.onTrue(new InstantCommand(swerve::toggleSlowmode, swerve));
        fieldOriented.onTrue(new InstantCommand(swerve::toggleFieldOriented, swerve));
    }

    public Command getAutonomousCommand() {
        if(startingPos.getSelected() == null) return null;
        return startingPos.getSelected().generate(swerve, camera, navx::getRoll, allianceColor.getSelected(), difficulty.getSelected());
    }

}
