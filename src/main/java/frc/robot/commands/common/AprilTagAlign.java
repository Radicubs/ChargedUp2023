package frc.robot.commands.common;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.PhotonVision;
import frc.robot.subsystems.Swerve;
import frc.robot.commands.common.*;
import frc.robot.commands.common.AprilTagAlignSubCommands.*;

public class AprilTagAlign extends SequentialCommandGroup {

    private final int tagnum;
    private static int instance = 0;
    private final Swerve base;
    private final PhotonVision camera;
    private boolean done = false;
    private final double offset;

    public AprilTagAlign(Swerve base, PhotonVision vision, int tagnum, TagAlignment alignment) {
        this(base, vision, tagnum, alignment.offset);
    }

    public AprilTagAlign(Swerve base, PhotonVision vision, int tagnum, double offset) {
        instance++;
        SmartDashboard.putNumber("instance", instance);
        this.tagnum = tagnum;
        this.base = base;
        this.camera = vision;
        this.offset = offset;

        //Don't distance value less than 0.508 (Distance to camera from robot bumper)
        addCommands(
            new AimAtTarget(base, vision, tagnum),
            new MoveToTarget(base, vision, tagnum, 1.08),
            new SpinAroundTarget(base, vision, tagnum, 1.08),
            new AdjustToFinalPosition(base, vision, tagnum, 1.08, 0),
            PathWeave.fromRelativeCoordinates(base, new Pose2d(0, offset, new Rotation2d()))
        );
    }

    public enum TagAlignment {
        // TODO: change x offset in accordance to camera position on robot
        LEFT(0.541),
        RIGHT(-0.541),
        CENTER(0);

        public final double offset;

        TagAlignment(double offset) {
            this.offset = offset;
        }
    }
}
