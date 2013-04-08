package edu.simpson.cmsc.algos.morbov2;
import ioio.lib.api.DigitalOutput;
import ioio.lib.api.IOIO;
import ioio.lib.api.PwmOutput;
import ioio.lib.api.exception.ConnectionLostException;
import ioio.lib.util.BaseIOIOLooper;

public class Motor extends BaseIOIOLooper{

	private boolean inputOneValue;
	private boolean inputTwoValue;
	private float powerValue;
	
	private DigitalOutput inputOne;
	private DigitalOutput inputTwo;
	private PwmOutput pwm;
	private IOIO ioio;

	public Motor(IOIO ioioIn) {
		ioio = ioioIn;
		inputOneValue = false;
		inputTwoValue = false;
		powerValue = 0;
	}

	public void Setup(int inputOnePin, 
					  int inputTwoPin, 
					  int pwmPin) throws ConnectionLostException {
		inputOne = ioio.openDigitalOutput(inputOnePin);
		inputTwo = ioio.openDigitalOutput(inputTwoPin);
		pwm = ioio.openPwmOutput(pwmPin, 100);
	}
	
	public void Refresh() throws ConnectionLostException{
		inputOne.write(inputOneValue);
		inputTwo.write(inputTwoValue);
		pwm.setDutyCycle(powerValue);
	}
	
	public void FullPower()
	{
		powerValue = 1;
	}
	public void FullStop()
	{
		powerValue = 0;
	}

	public void Clockwise(){
		inputOneValue = false;
		inputTwoValue = true;
	}
	public void CounterClockwise(){
		inputOneValue = true;
		inputTwoValue = false;
	}
	
}
