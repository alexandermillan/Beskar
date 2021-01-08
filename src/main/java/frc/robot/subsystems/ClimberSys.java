/*----------------------------------------------------------------------------*/
/* Copyright (c) 2020 FRC Team 4639. All Rights Reserved.                     */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;

import frc.robot.Constants;

import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import command.SubsystemBase;

public class ClimberSys extends SubsystemBase {
	private final WPI_TalonSRX leftClimber, rightClimber;
	private final DoubleSolenoid leftPiston, rightPiston;

	public ClimberSys() {
		this.leftPiston = new DoubleSolenoid(Constants.LEFT_PISTON_FORWARD_ID, Constants.LEFT_PISTON_REVERSE_ID);
		this.rightPiston = new DoubleSolenoid(Constants.RIGHT_PISTON_FORWARD_ID, Constants.RIGHT_PISTON_REVERSE_ID);

		this.leftClimber = new WPI_TalonSRX(Constants.LEFT_CLIMBER_CAN);
		leftClimber.setSensorPhase(true);
		leftClimber.configFactoryDefault();
		leftClimber.setNeutralMode(NeutralMode.Brake);
		leftClimber.setInverted(InvertType.InvertMotorOutput);
		leftClimber.configReverseSoftLimitEnable(true);
		leftClimber.configForwardSoftLimitEnable(true);
		leftClimber.configForwardSoftLimitThreshold(20_000);

		this.rightClimber = new WPI_TalonSRX(Constants.RIGHT_CLIMBER_CAN);
		rightClimber.setInverted(InvertType.FollowMaster);
		rightClimber.setSensorPhase(true);
		rightClimber.configFactoryDefault();
		rightClimber.setNeutralMode(NeutralMode.Brake);
		rightClimber.follow(leftClimber);
		rightClimber.configReverseSoftLimitEnable(true);
		rightClimber.configForwardSoftLimitEnable(true);
		rightClimber.configForwardSoftLimitThreshold(20_000);
	}

	public void setPistons(DoubleSolenoid.Value value) {
		leftPiston.set(value);
		rightPiston.set(value);
	}

	public void setClimber(double num) {
		leftClimber.set(num);
	}

	@Override
	public void periodic() {
	}
}
