package frc.robot.subsystems.IntakeLifter;

import org.littletonrobotics.junction.AutoLog;

import edu.wpi.first.units.Angle;
import edu.wpi.first.units.Measure;

public interface IntakeLifterIO {
    @AutoLog
    class IntakeLifterIOInputs {
        double lifterAngle;
        Measure<Angle> desiredAngle;
    }

    public void setLifterAngle(Measure<Angle> angleDeg);
    public double getLifterAngleRadians();
    public void stopLifter();

    public void updateInputs(IntakeLifterIOInputs inputs);
    public default void periodic() {};
}
