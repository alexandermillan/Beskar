/*----------------------------------------------------------------------------*/
/* Copyright (c) 2020 FRC Team 4639. All Rights Reserved.                     */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
package frc.robot.commands;

import frc.robot.subsystems.TurretSys;

import command.CommandBase;

public class Turret90Cmd extends CommandBase {
	private final TurretSys turret;

	public Turret90Cmd(TurretSys turret) {
		this.turret = turret;
		addRequirements(turret);
	}

	@Override
	public void initialize() {
		turret.setTurret(0.3);
	}

	@Override
	public void end(boolean interrupted) {
		turret.setTurret(0);
	}

	@Override
	public boolean isFinished() {
		return turret.getDegrees() > 11200;
	}
}
