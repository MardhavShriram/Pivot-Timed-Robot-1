// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  private static final String kDefaultAuto = "Default";
  private static final String kCustomAuto = "My Auto";
  private String m_autoSelected;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();

  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  @Override
  public void robotInit() {
    m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
    m_chooser.addOption("My Auto", kCustomAuto);
    SmartDashboard.putData("Auto choices", m_chooser);
  }

  //Defining Drivetrain Motors
  private final CANSparkMax front_left = new CANSparkMax(0, MotorType.kBrushless);
  private final CANSparkMax front_right = new CANSparkMax(1, MotorType.kBrushless);
  private final CANSparkMax back_left = new CANSparkMax(2, MotorType.kBrushless);
  private final CANSparkMax back_right = new CANSparkMax(3, MotorType.kBrushless);

  //Create the Pivot Motors
  private final CANSparkMax pivot_left = new CANSparkMax(4,MotorType.kBrushless);
  private final CANSparkMax pivot_right = new CANSparkMax(4,MotorType.kBrushless);
  //Intake Motors
  private final CANSparkMax intake_left = new CANSparkMax(5, MotorType.kBrushless);
  private final CANSparkMax intake_right = new CANSparkMax(6, MotorType.kBrushless);

  //Create the Joysticks
  private final Joystick left_joystick = new Joystick(1);
  private final Joystick right_joystick = new Joystick(2);

  //Create the Joystick buttons
  private final JoystickButton intake_in = new JoystickButton(left_joystick, 1);
  private final JoystickButton intake_out = new JoystickButton(left_joystick, 2);
  private final JoystickButton pivot_up = new JoystickButton(left_joystick, 3);
  private final JoystickButton pivot_down = new JoystickButton(left_joystick, 4);

  /**
   * This function is called every 20 ms, no matter the mode. Use this for items like diagnostics
   * that you want ran during disabled, autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before LiveWindow and
   * SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {}

  /**
   * This autonomous (along with the chooser code above) shows how to select between different
   * autonomous modes using the dashboard. The sendable chooser code works with the Java
   * SmartDashboard. If you prefer the LabVIEW Dashboard, remove all of the chooser code and
   * uncomment the getString line to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional comparisons to the switch structure
   * below with additional strings. If using the SendableChooser make sure to add them to the
   * chooser code above as well.
   */
  @Override
  public void autonomousInit() {
    m_autoSelected = m_chooser.getSelected();
    // m_autoSelected = SmartDashboard.getString("Auto Selector", kDefaultAuto);
    System.out.println("Auto selected: " + m_autoSelected);
  }

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {
    switch (m_autoSelected) {
      case kCustomAuto:
        // Put custom auto code here
        break;
      case kDefaultAuto:
      default:
        // Put default auto code here
        break;
    }
  }

  /** This function is called once when teleop is enabled. */
  @Override
  public void teleopInit() {
    //Set the Back motors to follow the Front
    back_left.follow(front_right);
    back_right.follow(front_right);

    //set Idle Mode Drivetrain Motors
    front_left.setIdleMode(IdleMode.kBrake);
    front_right.setIdleMode(IdleMode.kBrake);
    back_left.setIdleMode(IdleMode.kBrake);
    back_right.setIdleMode(IdleMode.kBrake);
    //set Idle Mode to Pivot Motors
    pivot_right.setIdleMode(IdleMode.kBrake);
    pivot_left.setIdleMode(IdleMode.kBrake);
    //Set Idle Mode to Intake Motors
    intake_left.setIdleMode(IdleMode.kBrake);
    intake_right.setIdleMode(IdleMode.kBrake);
  }

  /** This function is called periodically during operator control. */
  @Override
  public void teleopPeriodic() {
    //Setting the Drivetrain Speed
    front_left.set(left_joystick.getY()+right_joystick.getX());
    front_right.set(left_joystick.getY()-right_joystick.getX());
    
    //Setting Intake Motor
    if (intake_in.getAsBoolean()){
      intake_left.set(.5);
      intake_right.set(-.5);
    } 
    else if (intake_out.getAsBoolean()){
      intake_left.set(-.5);
      intake_right.set(.5);
    } 
    else {
      intake_left.set(0);
      intake_right.set(0);
    }
  //Setting the Pivot Motion
  if (pivot_up.getAsBoolean()){
    pivot_left.set(.5);
    pivot_right.set(-.5);
  } 
  else if (pivot_down.getAsBoolean()){  
    pivot_left.set(-.5);
    pivot_right.set(.5);
  }
  else {
    pivot_left.set(0);
    pivot_right.set(0);
  }


  }

  /** This function is called once when the robot is disabled. */
  @Override
  public void disabledInit() {}

  /** This function is called periodically when disabled. */
  @Override
  public void disabledPeriodic() {}

  /** This function is called once when test mode is enabled. */
  @Override
  public void testInit() {}

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {}

  /** This function is called once when the robot is first started up. */
  @Override
  public void simulationInit() {}

  /** This function is called periodically whilst in simulation. */
  @Override
  public void simulationPeriodic() {}
}
