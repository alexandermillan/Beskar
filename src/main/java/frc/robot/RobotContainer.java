/*----------------------------------------------------------------------------*/
/* Copyright (c) 2020 FRC Team 4639. All Rights Reserved.                     */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
package frc.robot;

import static frc.robot.Constants.Buttons;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;

import frc.robot.commands.ManualDriveCmd;
import frc.robot.commands.PushBallsCmd;
import frc.robot.commands.SpoolShooterCmd;
import frc.robot.commands.Turret90Cmd;
import frc.robot.subsystems.ClimberSys;
import frc.robot.subsystems.DrivetrainSys;
import frc.robot.subsystems.HopperSys;
import frc.robot.subsystems.IntakeSys;
import frc.robot.subsystems.KickerSys;
import frc.robot.subsystems.ShooterSys;
import frc.robot.subsystems.TurretSys;

import com.ctre.phoenix.motorcontrol.NeutralMode;

import command.Command;
import command.ExecuteEndCommand;
import command.InstantCommand;
import command.ParallelCommandGroup;
import command.WaitCommand;

/**
 * This class is where the bulk of the robot should be declared. Since
 * Command-based is a "declarative" paradigm, very little robot logic should
 * actually be handled in the {@link Robot} periodic methods (other than the
 * scheduler calls). Instead, the structure of the robot (including subsystems,
 * commands, and button mappings) should be declared here.
 */
public class RobotContainer {
	private final ClimberSys m_climber;
	private final DrivetrainSys m_drive;
	private final IntakeSys m_intake;
	private final HopperSys m_hopper;
	private final ShooterSys m_shooter;
	private final KickerSys m_kicker;
	private final TurretSys m_turret;

	private final OI m_oi;
	private final Compressor m_compressor;

	/**
	 * The container for the robot. Contains subsystems, OI devices, and commands.
	 */
	public RobotContainer() {
		m_compressor = new Compressor();
		m_compressor.setClosedLoopControl(true);

		m_oi = new OI();

		m_climber = new ClimberSys();
		m_climber.setDefaultCommand(
				new ExecuteEndCommand(() -> m_climber.setClimber(m_oi.getAxis(1, Constants.Axes.RIGHT_STICK_Y)),
						() -> m_climber.setClimber(0), m_climber));
		m_climber.setPistons(DoubleSolenoid.Value.kReverse);

		m_drive = new DrivetrainSys();
		m_drive.setDefaultCommand(new ManualDriveCmd(m_drive, m_oi));

		m_hopper = new HopperSys();

		m_intake = new IntakeSys();
		m_intake.setDefaultCommand(new ExecuteEndCommand(() -> {
			if (m_oi.getAxis(1, Constants.Axes.RIGHT_TRIGGER) > 0) {
				m_hopper.setHopper(-0.2);
				m_intake.setIntake(0.5);
			} else if (m_oi.getAxis(1, Constants.Axes.LEFT_TRIGGER) > 0) {
				m_hopper.setHopper(0);
				m_intake.setIntake(-0.5);
				// } else if(m_oi.getButton(1, Constants.Buttons.RIGHT_BUMPER)) {
				// ///ksbflwglwrglgblwg
				// m_hopper.setHopper(0);
				// m_intake.setIntake(0);
				// }
			} else {
				m_hopper.setHopper(0);
				m_intake.setIntake(0);
			}
		}, () -> {
			m_hopper.setHopper(0);
			m_intake.setIntake(0);
		}, m_intake, m_hopper));

		m_shooter = new ShooterSys();
		m_kicker = new KickerSys();
		m_turret = new TurretSys();
		m_turret.setDefaultCommand(
				new ExecuteEndCommand(() -> m_turret.setTurret(m_oi.getAxis(1, Constants.Axes.LEFT_STICK_X) * 0.5),
						() -> m_turret.setTurret(0), m_turret));

		configureButtonBindings();
	}

	/**
	 * Use this method to define your button->command mappings. Buttons can be
	 * created by instantiating a {@link GenericHID} or one of its subclasses
	 * ({@link edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then
	 * passing it to a {@link command.button.JoystickButton}.
	 */
	private void configureButtonBindings() {
		// Bring intake up
		m_oi.getPovButton(1, 0)
				.whileHeld(new ExecuteEndCommand(() -> m_intake.setPivot(0.7), () -> m_intake.setPivot(0), m_intake));

		// Run Hopper In
		m_oi.getButton(1, Buttons.RIGHT_BUMPER).whileHeld(new InstantCommand(() -> m_hopper.setHopper(0.2), m_hopper));

		// Bring intake down
		m_oi.getPovButton(1, 180)
				.whileHeld(new ExecuteEndCommand(() -> m_intake.setPivot(-0.5), () -> m_intake.setPivot(0), m_intake));

		// Extend the climber pistons
		m_oi.getButton(1, Buttons.Y_BUTTON)
				.whileHeld(new InstantCommand(() -> m_climber.setPistons(DoubleSolenoid.Value.kForward), m_climber));
		m_oi.getButton(1, Buttons.A_BUTTON)
				.whileHeld(new InstantCommand(() -> m_climber.setPistons(DoubleSolenoid.Value.kReverse), m_climber));

		// Move kicker wheel back to clear ball and then spool the shooter
		m_oi.getButton(1, Buttons.X_BUTTON)
				.whileHeld(new ExecuteEndCommand(() -> m_kicker.setKicker(-0.5), () -> m_kicker.setKicker(0), m_kicker)
						.withTimeout(0.1).andThen(new SpoolShooterCmd(m_shooter, m_kicker, 3800)));

		m_oi.getButton(1, Buttons.B_BUTTON)
				.whileHeld(new ExecuteEndCommand(() -> m_kicker.setKicker(-0.5), () -> m_kicker.setKicker(0), m_kicker)
						.withTimeout(0.1).andThen(new SpoolShooterCmd(m_shooter, m_kicker, 4300)));

		// Use the kicker to push the balls in
		m_oi.getButton(0, Buttons.X_BUTTON).whileHeld(new PushBallsCmd(m_hopper, m_intake, m_shooter));
	}

	/**
	 * Use this to pass the autonomous command to the main {@link Robot} class.
	 *
	 * @return the command to run in autonomous
	 */
	public Command getAutonomousCommand() {
		return new ParallelCommandGroup(
				new ExecuteEndCommand(() -> m_drive.arcadeDrive(-0.5, 0), () -> m_drive.arcadeDrive(0, 0), m_drive)
						.withTimeout(1.5),
				new Turret90Cmd(m_turret), new WaitCommand(5))
						.andThen(new ParallelCommandGroup(new SpoolShooterCmd(m_shooter, m_kicker, 3800),
								new PushBallsCmd(m_hopper, m_intake, m_shooter)).withTimeout(7));
	}

	public void setDriveNeutralMode(NeutralMode mode) {
		m_drive.setNeutralMode(mode);
	}
}
