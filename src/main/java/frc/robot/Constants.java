/*----------------------------------------------------------------------------*/
/* Copyright (c) 2020 FRC Team 4639. All Rights Reserved.                     */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
package frc.robot;

import edu.wpi.first.wpilibj.controller.SimpleMotorFeedforward;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide
 * numerical or boolean constants. This class should not be used for any other
 * purpose. All constants should be declared globally (i.e. public static). Do
 * not put anything functional in this class.
 *
 * <p>
 * It is advised to statically import this class (or one of its inner classes)
 * wherever the constants are needed, to reduce verbosity.
 */
public final class Constants {
	public static final String TURRET_CAMERA_NAME = "";
	public static final double MAX_COMMAND_VOLTAGE = 10;
	public static final double TRACK_WIDTH = 0.55982;
	public static final SimpleMotorFeedforward DRIVETRAIN_FEED_FORWARD = new SimpleMotorFeedforward(0.75, 0.11 * 39.37,
			0.0148 * 39.37);

	public static final double WHEEL_DIAMETER = 0.10795;

	public static final int FRONT_LEFT_DRIVE_CAN = 2;
	public static final int BACK_LEFT_DRIVE_CAN = 1;

	public static final int FRONT_RIGHT_DRIVE_CAN = 3;
	public static final int BACK_RIGHT_DRIVE_CAN = 4;

	public static final int TURRET_CAN = 7;
	public static final int TOP_SHOOTER_CAN = 8;
	public static final int BOTTOM_SHOOTER_CAN = 9;
	public static final SimpleMotorFeedforward SHOOTER_FEEDFORWARD = new SimpleMotorFeedforward(0.384, 0.116, 0.128);
	public static final double SHOOTER_KP = 3.73;
	public static final double SHOOTER_PID_TOLERANCE = 0.5;

	public static final int INTAKE_WHEEL_CAN = 14;
	public static final int INTAKE_PIVOT_CAN = 5;

	public static final int HOPPER_CAN = 12;
	public static final int KICKER_CAN = 13;

	public static final int LEFT_CLIMBER_CAN = 11;
	public static final int RIGHT_CLIMBER_CAN = 10;

	public static final int LEFT_PISTON_FORWARD_ID = 2;
	public static final int LEFT_PISTON_REVERSE_ID = 3;

	public static final int RIGHT_PISTON_FORWARD_ID = 4;
	public static final int RIGHT_PISTON_REVERSE_ID = 5;

	public static final double DEADZONE_VALUE = 0.01;
	public static final int NUMBER_OF_CONTROLLERS = 2;

	public enum Axes {
		LEFT_STICK_X(0), LEFT_STICK_Y(1), LEFT_TRIGGER(2), RIGHT_TRIGGER(3), RIGHT_STICK_X(4), RIGHT_STICK_Y(5);

		private final int value;

		Axes(int value) {
			this.value = value;
		}

		public int getValue() {
			return value;
		}
	}

	public enum Buttons {
		A_BUTTON(1), B_BUTTON(2), X_BUTTON(3), Y_BUTTON(4), LEFT_BUMPER(5), RIGHT_BUMPER(6), BACK_BUTTON(
				7), START_BUTTON(8), LEFT_STICK(9), RIGHT_STICK(10);

		private final int value;

		private Buttons(int value) {
			this.value = value;
		}

		public int getValue() {
			return value;
		}

	}
}
