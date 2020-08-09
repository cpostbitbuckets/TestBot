package frc.robot.helpers;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

public class BBTalonSRX extends WPI_TalonSRX {

    public BBTalonSRX(int deviceNumber) {
        super(deviceNumber);
    }

    /**
     * Override get() to query the motor for it's current OutputPercent
     */
    @Override
    public double get() {
        return getMotorOutputPercent();
    }

}