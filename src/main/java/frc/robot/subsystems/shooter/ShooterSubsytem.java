package frc.robot.subsystems.shooter;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.helpers.BBTalonFX;
import frc.robot.helpers.BBTalonSRX;
import io.github.oblarg.oblog.Loggable;
import io.github.oblarg.oblog.annotations.Log;

public class ShooterSubsytem extends SubsystemBase implements Loggable {

    @Log.SpeedController(name = "Shooter", columnIndex = 1, rowIndex = 0)
    private BBTalonFX shooter;

    @Log.SpeedController(name = "Shooter Follower", columnIndex = 3, rowIndex = 0)
    private BBTalonFX shooterFollower;

    @Log.SpeedController(name = "Feeder", columnIndex = 1, rowIndex = 1)
    private BBTalonSRX feeder;

    private final ShooterDashboardConfig shooterConfig;

    public ShooterSubsytem() {
        this.shooterConfig = new ShooterDashboardConfig();
    }

    public void init() {
        shooter = new BBTalonFX(15);
        shooterFollower = new BBTalonFX(16);
        shooter.setInverted(true);
        shooter.config_kP(0, 1023.0);
        shooter.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor);
        shooter.configMotionAcceleration(1350);
        shooter.configMotionCruiseVelocity(1350);

        shooter.configOpenloopRamp(0);
        shooter.configClosedloopRamp(0);
        shooter.setNeutralMode(NeutralMode.Coast);
        shooter.enableVoltageCompensation(true);
        shooter.configVoltageCompSaturation(12);
        shooter.configNominalOutputReverse(0);
        shooter.configVelocityMeasurementWindow(1);

        // setup the follower
        shooterFollower.follow(shooter);
        shooterFollower.setInverted(true);
        shooterFollower.configClosedloopRamp(0);
        shooterFollower.enableVoltageCompensation(true);
        shooterFollower.configVoltageCompSaturation(12);
        shooterFollower.configNominalOutputReverse(0);
        shooterFollower.setNeutralMode(NeutralMode.Coast);

        feeder = new BBTalonSRX(9);
        feeder.setNeutralMode(NeutralMode.Brake);

    }

    public void manualFire(double percentOutput) {
        shooter.set(ControlMode.PercentOutput, percentOutput);
    }

    public void manualFeed(double percentOutput) {
        feeder.set(ControlMode.PercentOutput, percentOutput);
    }

    public void fire() {
        shooter.set(ControlMode.Velocity, shooterConfig.shooterSpeed);
    }

    public void holdFire() {
        shooter.set(ControlMode.PercentOutput, 0);
    }

    public void feed() {
        feeder.set(ControlMode.PercentOutput, shooterConfig.feederPercentOutput);
    }

    public void doNotFeed() {
        feeder.set(ControlMode.PercentOutput, 0);
    }

    @Log(name = "Shooter Velocity (ticks)", width = 2, columnIndex = 5, rowIndex = 0)
    public int getShooterTicks() {
        return shooter.getSelectedSensorVelocity();
    }

}