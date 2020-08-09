/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.StartEndCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.subsystems.shooter.ShooterSubsytem;
import frc.robot.utils.PS4Constants;
import io.github.oblarg.oblog.Logger;

public class Robot extends TimedRobot {

    private static final int OPERATOR_JOYSTICK_ID = 0;

    Joystick joystick = new Joystick(OPERATOR_JOYSTICK_ID);
    JoystickButton fireButton = new JoystickButton(joystick, PS4Constants.CIRCLE.getValue());
    JoystickButton feedButton = new JoystickButton(joystick, PS4Constants.CROSS.getValue());

    ShooterSubsytem shooterSubsytem;

    @Override
    public void robotInit() {
        shooterSubsytem = new ShooterSubsytem();
        shooterSubsytem.init();

        // setup the joystick bindings
        configureButtonBindings();

        // The first argument is the root container
        // The second argument is whether logging and config should be given separate
        // tabs
        // NOTE: This must happen after robot initialization so the Logged fields are
        // all valid objects
        Logger.configureLoggingAndConfig(this, false);
    }

    private void configureButtonBindings() {

        // by default, we use left/right y axis sticks to operate the shooter motors
        shooterSubsytem.setDefaultCommand(new RunCommand(() -> {
            shooterSubsytem.manualFire(joystick.getRawAxis(PS4Constants.LEFT_STICK_Y.getValue()));
            shooterSubsytem.manualFeed(joystick.getRawAxis(PS4Constants.RIGHT_STICK_Y.getValue()));
        }, shooterSubsytem));

        // command to velocity when fire button held
        fireButton.whenHeld(
                new StartEndCommand(() -> shooterSubsytem.fire(), () -> shooterSubsytem.holdFire(), shooterSubsytem));
        // command to velocity when fire button held
        feedButton.whenHeld(
                new StartEndCommand(() -> shooterSubsytem.feed(), () -> shooterSubsytem.doNotFeed(), shooterSubsytem));

    }

    @Override
    public void robotPeriodic() {

        // update Oblog entries
        Logger.updateEntries();

        // always run the CommandScheduler during periodic
        CommandScheduler.getInstance().run();
    }

    public static Robot win() {
        return new Robot();
    }

}