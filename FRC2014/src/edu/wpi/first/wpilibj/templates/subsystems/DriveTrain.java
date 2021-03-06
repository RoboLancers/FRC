/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.templates.RobotMap;
import edu.wpi.first.wpilibj.templates.commands.DriveTrain.DriveLoop;

/**
 * @author Armond
 */

public class DriveTrain extends Subsystem{
    public RobotDrive driveStandard;
    private double x, y, leftDrive, rightDrive;
    public static boolean isHighGear = true;
            
    public static SpeedController leftFrontMotor, rightFrontMotor, leftRearMotor, rightRearMotor; //Standard Drive Motors
    public static SpeedController sliderMotor1, sliderMotor2; //Standard Drive Motors
    public static DoubleSolenoid sonicShifterPair;
    
    public DriveTrain(){
        super("Drive Train");
        
       leftFrontMotor = new Talon(RobotMap.LEFT_MOTOR_FRONT);
       leftRearMotor = new Talon(RobotMap.LEFT_MOTOR_REAR);
       rightFrontMotor = new Talon(RobotMap.RIGHT_MOTOR_FRONT);
       rightRearMotor = new Talon(RobotMap.RIGHT_MOTOR_REAR);
       
       sonicShifterPair = new DoubleSolenoid(RobotMap.SS_FOR,RobotMap.SS_REV);
       
       driveStandard = new RobotDrive(leftFrontMotor, leftRearMotor, rightFrontMotor, rightRearMotor);
       driveStandard.setSafetyEnabled(false); //have this so compiler wont show "Robot Drive not outputting enough data"
       
       sonicShifterPair.set(Value.kForward);
    }
    
    public void initDefaultCommand(){
        setDefaultCommand(new DriveLoop());
    }
    
    public void moveWithJoystick(double moveValue, double rotateValue, double speed){
        if (RobotMap.ARCADE_DRIVE || RobotMap.RC_DRIVE){
            y = (moveValue * moveValue * moveValue) *speed;
            x = (rotateValue *rotateValue *rotateValue) *speed;
            driveStandard.arcadeDrive(y,x);
        }
        else if(RobotMap.TANK_DRIVE){
            leftDrive = moveValue*speed;
            rightDrive = rotateValue*speed;
            driveStandard.tankDrive(leftDrive,rightDrive);
        }
    }
}