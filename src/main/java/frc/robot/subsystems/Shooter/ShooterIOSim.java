package frc.robot.subsystems.Shooter;

public class ShooterIOSim implements ShooterIO {
    public void setLeftMotor(double speed) {}
    public void setRightMotor(double speed) {}
    public void set(double leftSpeed, double rightSpeed) {}
    public void set(double speed) {}

    public void shootSpeaker() {}

    public void stop() {}

    public double getLeftMotorRpm() {return 0;}
    public double getRightMotorRpm() {return 0;}

    public double getLeftMotorPercentage() {return 0;}
    public double getRightMotorPercentage() {return 0;}

    public void updateInputs(ShooterIOInputs inputs) {}
    public void periodic() {}
}
