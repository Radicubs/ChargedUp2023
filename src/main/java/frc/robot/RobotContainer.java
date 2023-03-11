package frc.robot;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.lib.util.auto.AutoDifficulty;
import frc.lib.util.auto.StartingPosition;
import frc.robot.commands.common.AprilTagAlign;
import frc.robot.commands.common.PathWeave;
import frc.robot.commands.common.PathWeave2;
import frc.robot.commands.teleop.SubsystemControlCommand;
import frc.robot.commands.teleop.TeleopSwerve;
import frc.robot.subsystems.*;

import java.util.function.DoubleSupplier;

public class RobotContainer {

    // Joysticks
    private final Joystick driver;
    private final Joystick buttonBoard;

    // Buttons
    private final JoystickButton leftAlign;
    private final JoystickButton centerAlign;
    private final JoystickButton rightAlign;
    private final JoystickButton gripp;
    private final JoystickButton slowmode;
    private final JoystickButton fieldOriented;
    private final JoystickButton resetGyro;
    private final JoystickButton forward;
    private final JoystickButton backward;

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

    private AprilTagAlign currentApril;
    private PathWeave2 currentPath;

    public RobotContainer() {
        driver = new Joystick(0);
        buttonBoard = new Joystick(1);
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
        swerve.resetOdo(new Pose2d(new Translation2d(2, 2), Rotation2d.fromDegrees(90)));
//swerve.resetOdo();
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
        resetGyro = new JoystickButton(driver, XboxController.Button.kRightBumper.value);
        gripp = new JoystickButton(buttonBoard, 5);
        forward = new JoystickButton(buttonBoard, 6);
        backward = new JoystickButton(buttonBoard, 7);

        currentApril = null;
        currentPath = null;

        configureButtonBindings();
    }

    private void configureButtonBindings() {
        leftAlign.onTrue(new InstantCommand(() -> {
            CommandScheduler.getInstance().cancel(currentApril);
            currentApril = new AprilTagAlign(swerve, camera, camera.findNearestTag(), AprilTagAlign.TagAlignment.LEFT);
            CommandScheduler.getInstance().schedule(currentApril);
        }));

        centerAlign.onTrue(new InstantCommand(() -> {
            CommandScheduler.getInstance().cancel(currentApril);
            currentApril = new AprilTagAlign(swerve, camera, 1, AprilTagAlign.TagAlignment.CENTER);
            CommandScheduler.getInstance().schedule(currentApril);
        }));

        rightAlign.whileTrue(new InstantCommand(() -> {
            CommandScheduler.getInstance().cancel(currentApril);
            currentApril = new AprilTagAlign(swerve, camera, camera.findNearestTag(), AprilTagAlign.TagAlignment.RIGHT);
            CommandScheduler.getInstance().schedule(currentApril);
        }));

        leftAlign.onFalse(new InstantCommand(() -> CommandScheduler.getInstance().cancel(currentApril)));
        centerAlign.onFalse(new InstantCommand(() -> CommandScheduler.getInstance().cancel(currentApril)));
        rightAlign.onFalse(new InstantCommand(() -> CommandScheduler.getInstance().cancel(currentApril)));

        forward.onTrue(new InstantCommand(() -> {
            CommandScheduler.getInstance().cancel(currentPath);
            currentPath = new PathWeave2(swerve, false, new Pose2d(new Translation2d(4, 4), Rotation2d.fromDegrees(45)), new Translation2d(0, 3));
            CommandScheduler.getInstance().schedule(currentPath);
        }, swerve));

        backward.onTrue(new InstantCommand(() -> {
            CommandScheduler.getInstance().cancel(currentPath);
            currentPath = new PathWeave2(swerve, false, new Pose2d(new Translation2d(-1, 0), Rotation2d.fromDegrees(0)));
            CommandScheduler.getInstance().schedule(currentPath);
        }, swerve));

        forward.onFalse(new InstantCommand(() -> CommandScheduler.getInstance().cancel(currentPath), swerve));
        backward.onFalse(new InstantCommand(() -> CommandScheduler.getInstance().cancel(currentPath), swerve));

        gripp.whileTrue(new CommandBase() {
            @Override
            public void execute() {
                gripper.set(-0.4);
            }
        });

        slowmode.onTrue(new InstantCommand(swerve::toggleSlowmode, swerve));
        fieldOriented.onTrue(new InstantCommand(swerve::toggleFieldOriented, swerve));
        resetGyro.onTrue(new InstantCommand(navx::reset, navx));
    }

    public Command getAutonomousCommand() {
        if(startingPos.getSelected() == null) return null;
        return startingPos.getSelected().generate(swerve, arm, shoulder, camera, navx::getPitch, allianceColor.getSelected(), difficulty.getSelected());
    }

}
