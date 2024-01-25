package frc.robot;

import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.units.Angle;
import edu.wpi.first.units.Distance;
import edu.wpi.first.units.Measure;
import edu.wpi.first.units.Velocity;
import lib.team3526.PIDFConstants;
import lib.team3526.SwerveChassis;
import lib.team3526.SwerveModuleOptions;
import static edu.wpi.first.units.Units.*;

public final class Constants {
    public static final class SwerveDrive {
        //! Physical model of the robot
        public static final class PhysicalModel {
            //! MAX DISPLACEMENT SPEED (and acceleration)
            public static final Measure<Velocity<Distance>> kMaxSpeed = MetersPerSecond.of(20);
            public static final Measure<Velocity<Velocity<Distance>>> kMaxAcceleration = MetersPerSecond.per(Second).of(kMaxSpeed.in(MetersPerSecond) / 2);

            //! MAX ROTATIONAL SPEED (and acceleration)
            public static final Measure<Velocity<Angle>> kMaxAngularSpeed = RadiansPerSecond.of(2 * Math.PI);
            public static final Measure<Velocity<Velocity<Angle>>> kMaxAngularAcceleration = RadiansPerSecond.per(Second).of(kMaxAngularSpeed.in(RadiansPerSecond) / 2);

            // Drive wheel diameter
            public static final Measure<Distance> kWheelDiameter = Inches.of(4);

            // Gear ratios
            public static final double kDriveMotorGearRatio = 1.0 / 6.12; // 6.12:1 Drive
            public static final double kTurningMotorGearRatio = 1.0 / 12.8; // 12.8:1 Steering

            // Conversion factors (Drive Motor)
            public static final double kDriveEncoder_RotationToMeter = kDriveMotorGearRatio * kWheelDiameter.in(Meters) * 2 * Math.PI;
            public static final double kDriveEncoder_RPMToMeterPerSecond = kDriveEncoder_RotationToMeter / 60.0;

            // Conversion factors (Turning Motor)
            public static final double kTurningEncoder_RotationToRadian = kTurningMotorGearRatio * 2.0 * Math.PI;
            public static final double kTurningEncoder_RPMToRadianPerSecond = kTurningEncoder_RotationToRadian / 60.0;

            // Robot Without bumpers measures
            public static final Measure<Distance> kTrackWidth = Inches.of(30);
            public static final Measure<Distance> kWheelBase = Inches.of(30);
    
            // Create a kinematics instance with the positions of the swerve modules
            public static final SwerveDriveKinematics kDriveKinematics = new SwerveDriveKinematics(SwerveChassis.sizeToModulePositions(kTrackWidth.in(Meters), kWheelBase.in(Meters)));
        }

        //! Swerve modules configuration
        public static final class SwerveModules {
            //! PIDs (turn and drive motors)
            public static final PIDFConstants kDrivePIDConstants = new PIDFConstants(0.175, 0.0000000005, 0, 0.0000000005);
            public static final PIDFConstants kTurningPIDConstants = new PIDFConstants(0.5, 0, 0.0000000005, 0.0000000005);

            public static final SwerveModuleOptions kFrontLeftOptions = new SwerveModuleOptions()
                .setOffsetDeg(72.686)
                .setAbsoluteEncoderInverted(true)
                .setAbsoluteEncoderID(11)
                .setDriveMotorID(22)
                .setTurningMotorID(21)
                .setDriveMotorInverted(true)
                .setTurningMotorInverted(true)
                .setName("Front Left");

            public static final SwerveModuleOptions kFrontRightOptions = new SwerveModuleOptions()
                .setOffsetDeg(-38.583984375)
                .setAbsoluteEncoderInverted(true)
                .setAbsoluteEncoderID(12)
                .setDriveMotorID(24)
                .setTurningMotorID(23)
                .setDriveMotorInverted(false)
                .setTurningMotorInverted(true)
                .setName("Front Right");

            public static final SwerveModuleOptions kBackLeftOptions = new SwerveModuleOptions()
                .setOffsetDeg(-56.953)
                .setAbsoluteEncoderInverted(true)
                .setAbsoluteEncoderID(13)
                .setDriveMotorID(26)
                .setTurningMotorID(25)
                .setDriveMotorInverted(true)
                .setTurningMotorInverted(true)
                .setName("Back Left");

            public static final SwerveModuleOptions kBackRightOptions = new SwerveModuleOptions()
                .setOffsetDeg(-105.46875)
                .setAbsoluteEncoderInverted(true)
                .setAbsoluteEncoderID(14)
                .setDriveMotorID(28)
                .setTurningMotorID(27)
                .setDriveMotorInverted(false)
                .setTurningMotorInverted(true)
                .setName("Back Right");
        }
    }
}
