package edu.simpson.cmsc.algos.morbov2;

import ioio.lib.api.DigitalOutput;
import ioio.lib.api.PwmOutput;
import ioio.lib.api.exception.ConnectionLostException;
import ioio.lib.util.BaseIOIOLooper;
import ioio.lib.util.IOIOLooper;
import ioio.lib.util.android.IOIOActivity;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.TextView;

public class MainActivity extends IOIOActivity {

	TextView status;
	
	private static final int AIN1_PIN = 32;//41;
	private static final int AIN2_PIN = 31;//40;
	private static final int PWMA_PIN = 30;//39;
	private static final int PWMB_PIN = 45;
	private static final int BIN2_PIN = 44;
	private static final int BIN1_PIN = 43;
	private static final int STBY_PIN = 42;
	private static final int LED_PIN = 0;

	private static final int PWM_FREQ = 100;

	private float left_ = 0;
	private float right_ = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		status = (TextView)findViewById(R.id.status);
		
	}

	/**
	 * This is the thread on which all the IOIO activity happens. It will be run
	 * every time the application is resumed and aborted when it is paused. The
	 * method setup() will be called right after a connection with the IOIO has
	 * been established (which might happen several times!). Then, loop() will
	 * be called repetitively until the IOIO gets disconnected.
	 */
	class Looper extends BaseIOIOLooper {
//		private DigitalOutput ain1_;
//		private DigitalOutput ain2_;
//		private PwmOutput pwma_;
//		private PwmOutput pwmb_;
		private DigitalOutput led_;
//		private DigitalOutput bin2_;
//		private DigitalOutput bin1_;
//		private DigitalOutput stby_;
		
		
		private MotorDriver motorDriver;

		/**
		 * Called every time a connection with IOIO has been established.
		 * Typically used to open pins.
		 * 
		 * @throws ConnectionLostException
		 *             When IOIO connection is lost.
		 * 
		 * @see ioio.lib.util.AbstractIOIOActivity.IOIOThread#setup()
		 */
		@Override
		protected void setup() throws ConnectionLostException {
			motorDriver = new MotorDriver(ioio_);
			motorDriver.SetupMotorA(AIN1_PIN, AIN2_PIN, PWMA_PIN);
			motorDriver.SetupMotorB(BIN1_PIN, BIN2_PIN, PWMB_PIN);
		    motorDriver.SetupStandBy(STBY_PIN);
																		
			led_ = ioio_.openDigitalOutput(0);
		}

		/**
		 * Called repetitively while the IOIO is connected.
		 * 
		 * @throws ConnectionLostException
		 *             When IOIO connection is lost.
		 * @throws InterruptedException
		 * 
		 * @see ioio.lib.util.AbstractIOIOActivity.IOIOThread#loop()
		 */

		public void loop() throws ConnectionLostException {
			
			
			// Forward movement
			motorDriver.MotorA().FullPower();
			motorDriver.MotorB().FullPower();
			
			motorDriver.MotorA().Clockwise();
			motorDriver.MotorB().Clockwise();
			motorDriver.RefreshAll();
			led_.write(true);
			
			// Wait 2 seconds
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			// Stop
			motorDriver.MotorA().FullStop();
			motorDriver.MotorB().FullStop();
			motorDriver.RefreshAll();
			led_.write(false);
			
			// Wait 2 seconds
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			// Backward movement
			motorDriver.MotorA().FullPower();
			motorDriver.MotorB().FullPower();
			
			motorDriver.MotorA().CounterClockwise();
			motorDriver.MotorB().CounterClockwise();
			motorDriver.RefreshAll();
			led_.write(true);
			
			// Wait 2 seconds
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			// Stop
			motorDriver.MotorA().FullStop();
			motorDriver.MotorB().FullStop();
			motorDriver.RefreshAll();
			led_.write(false);
			
			// Wait 2 seconds
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			
		}

	}

	/**
	 * A method to create our IOIO thread.
	 * 
	 * @see ioio.lib.util.AbstractIOIOActivity#createIOIOThread()
	 */
	@Override
	protected IOIOLooper createIOIOLooper() {
		return new Looper();
	}

}
