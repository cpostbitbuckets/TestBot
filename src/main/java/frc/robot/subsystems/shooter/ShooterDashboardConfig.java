package frc.robot.subsystems.shooter;

import io.github.oblarg.oblog.Loggable;
import io.github.oblarg.oblog.annotations.Config;

public class ShooterDashboardConfig implements Loggable {

    double shooterSpeed = 9215;

    @Config(name = "Shooter Speed", defaultValueNumeric = 9215, columnIndex = 0, rowIndex = 0)
    void setShooterSpeed(double shooterSpeed) {
        this.shooterSpeed = shooterSpeed;
    }

    double feederPercentOutput = .5;

    @Config(name = "Feeder Percent Output", defaultValueNumeric = .5, columnIndex = 0, rowIndex = 1)
    void setFeederPercentOutput(double feederPercentOutput) {
        this.feederPercentOutput = feederPercentOutput;
    }

    /**
     * Skip the layout to make all these config values show up on the main Subsystem
     * dashboard
     */
    @Override
    public boolean skipLayout() {
        return true;
    }
}