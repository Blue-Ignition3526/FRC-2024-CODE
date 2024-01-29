package frc.robot.subsystems.SwerveDrive;

import org.littletonrobotics.junction.AutoLog;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveDriveOdometry;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;

public interface SwerveDriveIO {
    @AutoLog
    class SwerveDriveIOInputs {
        double heading;

        double xSpeed;
        double ySpeed;
        double rotSpeed;
    }

    public Rotation2d getHeading();
    public SwerveDriveOdometry getOdometry();

    public SwerveModuleState[] getModuleStates();
    public SwerveModuleState[] getRealModuleStates();
    public SwerveModulePosition[] getModulePositions();

    public void stop();

    public void drive(ChassisSpeeds speeds);

    public void driveFieldRelative(double xSpeed, double ySpeed, double rotSpeed);
    public void driveFieldRelative(ChassisSpeeds speeds);

    public void driveRobotRelative(double xSpeed, double ySpeed, double rotSpeed);
    public void driveRobotRelative(ChassisSpeeds speeds);

    public void configureAutoBuilder(SwerveDrive swerveDrive);

    public void xFormation();

    public void updateInputs(SwerveDriveIOInputs inputs);
    public default void periodic() {}
}
