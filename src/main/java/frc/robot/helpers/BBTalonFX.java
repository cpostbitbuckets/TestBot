package frc.robot.helpers;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

public class BBTalonFX extends WPI_TalonFX {

    public BBTalonFX(int deviceNumber) {
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