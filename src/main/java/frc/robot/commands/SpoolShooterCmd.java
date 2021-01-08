/*----------------------------------------------------------------------------*/
/* Copyright (c) 2020 FRC Team 4639. All Rights Reserved.                     */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
package frc.robot.commands;
import frc.robot.subsystems.KickerSys;
import frc.robot.subsystems.ShooterSys;

import command.CommandBase;

public class SpoolShooterCmd extends CommandBase {
	private final ShooterSys shooter;
	private final KickerSys kicker;
	private final double rpm;

	public SpoolShooterCmd(ShooterSys shooter, KickerSys kicker, double rpm) {
		this.shooter = shooter;
		this.kicker = kicker;
		addRequirements(shooter, kicker);
		this.rpm = rpm;
	}

	@Override
	public void initialize() {
		// code expects rps but rpm is easier to use
		shooter.setShooter(rpm / 60.0);
		kicker.setKicker(1.0);
	}

	@Override
	public void end(boolean interrupted) {
		shooter.setShooter(0);
		kicker.setKicker(0);
	}

	@Override
	public boolean isFinished() {
		return false;
	}
}
