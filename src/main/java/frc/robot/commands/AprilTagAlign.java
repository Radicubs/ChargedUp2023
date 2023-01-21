package frc.robot.commands;

import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.networktables.*;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Swerve;

public class AprilTagAlign extends CommandBase {

    private final int tagnum;
    private final Swerve base;
    private final IntegerSubscriber num;
    private final StringArraySubscriber vals;

    public AprilTagAlign(Swerve base, int tagnum) {
        num = NetworkTableInstance.getDefault().getTable("atags").getIntegerTopic("numtags").subscribe(0);
        vals = NetworkTableInstance.getDefault().getTable("atags").getStringArrayTopic("tagvals").subscribe(new String[] {});
        this.tagnum = tagnum;
        this.base = base;
    }

    @Override
    public void initialize() {
        base.driveFromChassisSpeeds(new ChassisSpeeds());
    }

    @Override
    public void execute() {

    }

}
