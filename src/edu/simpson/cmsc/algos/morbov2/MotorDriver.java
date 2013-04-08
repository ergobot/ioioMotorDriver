package edu.simpson.cmsc.algos.morbov2;

import ioio.lib.api.DigitalOutput;
import ioio.lib.api.IOIO;
import ioio.lib.api.PwmOutput;
import ioio.lib.api.exception.ConnectionLostException;
import ioio.lib.util.BaseIOIOLooper;

public class MotorDriver extends BaseIOIOLooper{

	private DigitalOutput standby;
	private Motor motorA;
	private Motor motorB;
	private IOIO ioio;
	
	public MotorDriver(IOIO ioioIn)
	{
		ioio = ioioIn;
		motorA = new Motor(ioio);
		motorB = new Motor(ioio);
		
	}
	
	public void SetupMotorA(int inputOnePin, int inputTwoPin, int pwmPin) throws ConnectionLostException
	{
		motorA.Setup(inputOnePin, inputTwoPin, pwmPin);
	}
	
	public void SetupMotorB(int inputOnePin, int inputTwoPin, int pwmPin) throws ConnectionLostException
	{
		motorB.Setup(inputOnePin, inputTwoPin, pwmPin);
	}
	
	public void SetupStandBy(int standByPin) throws ConnectionLostException
	{
		standby = ioio.openDigitalOutput(standByPin);
		standby.write(true);
	}
	
	public void RefreshAll() throws ConnectionLostException{
		MotorA().Refresh();
		MotorB().Refresh();
		
	}
	
	public Motor MotorA() {return motorA;}
	public Motor MotorB() {return motorB;}

}
