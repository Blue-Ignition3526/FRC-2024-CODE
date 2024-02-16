package frc.robot.subsystems.Intake;

import static edu.wpi.first.units.Units.Degrees;
import static edu.wpi.first.units.Units.RPM;

import org.littletonrobotics.junction.Logger;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkPIDController;
import com.revrobotics.CANSparkBase.ControlType;
import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.units.Angle;
import edu.wpi.first.units.Measure;
import edu.wpi.first.units.Velocity;
import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants;
import lib.team3526.constants.PIDFConstants;
import lib.team3526.control.LazyCANSparkMax;

public class IntakeIOReal implements IntakeIO {
    private final LazyCANSparkMax lifterMotor;
    private final PIDController lifterMotorPID;
    private final DutyCycleEncoder lifterEncoder;

    private final LazyCANSparkMax intakeMotor;
    private final SparkPIDController intakeMotorPID;
    private final RelativeEncoder intakeMotorEncoder;

    private boolean hasPiece = false;
    private boolean isIntaking = false;
    private double feedForwad = 0.5;
    
    private Measure<Angle> desiredAngle = Degrees.of(0.0);

    public IntakeIOReal() {
        this.lifterMotor = new LazyCANSparkMax(Constants.Intake.kLifterMotorID, MotorType.kBrushless);
        this.lifterMotor.setInverted(true);
        this.lifterMotorPID = Constants.Intake.kLifterPIDController;
        this.lifterEncoder = new DutyCycleEncoder(Constants.Intake.kLifterEncoderPort);

        this.intakeMotor = new LazyCANSparkMax(Constants.Intake.kintakeMotorID, MotorType.kBrushless);
        this.intakeMotorPID = this.intakeMotor.getPIDController();
        PIDFConstants.applyToSparkPIDController(intakeMotorPID, Constants.Intake.kIntakePIDConstants);
        this.intakeMotorEncoder = this.intakeMotor.getEncoder();
    }

///////////////////////////// ROLLERS /////////////////////////////

    public void setIntakeOut() {
        this.setIntakeSpeed(Constants.Intake.kIntakeOutSpeed);
    }

    public void setIntakeIn() {
        this.setIntakeSpeed(Constants.Intake.kIntakeInSpeed);
    }

    public void giveToShooter() {
        this.setIntakeSpeed(Constants.Intake.kGiveToShooterSpeed);
    }

    public void setIntakeHold() {
        this.setIntakeSpeed(Constants.Intake.kIntakeHoldSpeed);
    }

    public void stopIntake() {
        this.setIntakeSpeed(0);
    }

    public void setIntakeSpeed(double speed) {
        this.isIntaking = speed > 0;
        if (speed < 0) this.hasPiece = false;
        this.intakeMotor.set(speed);
    }

    public void setIntakeSpeedRpm(Measure<Velocity<Angle>> rpm) {
        this.isIntaking = rpm.in(RPM) > 0;
        if (rpm.in(RPM) < 0) this.hasPiece = false;
        this.intakeMotorPID.setReference(rpm.in(RPM), ControlType.kVelocity);
    }

    public void setIntakeCoast() {
        Logger.recordOutput("Intake/Brake", false);
        this.intakeMotor.setIdleMode(IdleMode.kCoast);
    }

    public void setIntakeBrake() {
        Logger.recordOutput("Intake/Brake", true);
        this.intakeMotor.setIdleMode(IdleMode.kBrake);
    }

///////////////////////////// LIFTER /////////////////////////////

    /**
     * @param angleDeg The angle to set the lifter to
     * @return true if the lifter is within 5 degrees of the target angle
     */
    public void setLifterAngle(Measure<Angle> angleDeg) {
        this.desiredAngle = angleDeg;
    }

    public void setHasPiece(boolean hasPiece) {
        this.hasPiece = hasPiece;
    }

    public double getLifterAngle() {
        double angleDegrees = (this.lifterEncoder.getAbsolutePosition() - Constants.Intake.kLifterEncoderOffset) * 360;
        return ((Math.floor(angleDegrees * 1000)) / 1000);
    }

    public double getIntakeSpeed() {
        return this.intakeMotorEncoder.getVelocity();
    }

    public boolean hasPiece() {
        return this.hasPiece;
    }



    public void periodic() {
        if (Math.abs(this.getLifterAngle() - this.desiredAngle.in(Degrees)) > 0.5) {
            this.lifterMotor.set(this.lifterMotorPID.calculate(this.getLifterAngle(), this.desiredAngle.in(Degrees)) * 2 + 0.5);
            Logger.recordOutput("Intake/PIDCalculatedSpeed", this.lifterMotorPID.calculate(this.getLifterAngle(), this.desiredAngle.in(Degrees)) * 2 + 0.5);
        } else {
            this.lifterMotor.set(0.0);
            // Logger.recordOutput("Intake/PIDCalculatedSpeed", 0);
        }
        
        Logger.recordOutput("Intake/Current", this.intakeMotor.getOutputCurrent());
        Logger.recordOutput("Intake/Speed", this.getIntakeSpeed());
        Logger.recordOutput("Intake/LifterAngle", this.getLifterAngle());
        Logger.recordOutput("Intake/Lifter", this.lifterMotor.get());
        Logger.recordOutput("Intake/SetAngle", this.desiredAngle.in(Degrees));

        SmartDashboard.putData(this.lifterMotorPID);
    }

    public void updateInputs(IntakeIOInputs inputs) {
        inputs.hasPiece = this.hasPiece;
        inputs.intakeCurrent = this.intakeMotor.getOutputCurrent();
        inputs.intakeSpeed = this.intakeMotor.getAppliedOutput();
        inputs.lifterAngle = this.getLifterAngle();
    }
}
