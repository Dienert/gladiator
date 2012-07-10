package br.com.ufpb;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class MainGladiator extends Activity implements SensorEventListener{
	
	private View v;
	private TextView centro = null;
	private TextView left = null;
	private TextView right = null;
	private Button extras = null;
	private Button watch = null;
	private SensorManager sensorManager;
	private float y;
	private float lastY;
	private String answer = "";
	private boolean status = true;
	private Sensor sensor;
	private long lastUpdate;
	private View mainLayout = null;
	private CountDownTimer trigger = null;
	private boolean up;
	private boolean down;
	
	@Override
	public void onCreate( Bundle savedInstanceState ){
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
                                WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.maingladiator);
        
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        
        sensorManager.registerListener(this,sensor,	SensorManager.SENSOR_DELAY_UI);
		
		left = (TextView)findViewById( R.id.left);
		right = (TextView)findViewById( R.id.right);
		Typeface font = Typeface.createFromAsset(getAssets(), "Imperator.ttf");  
        left.setTypeface(font);
        right.setTypeface(font);
		extras = (Button)findViewById( R.id.extras );
		//watch = (Button)findViewById( R.id.watch);
        extras.setOnClickListener( extra );      
        
        
        left.setText( "Waiting for options." );
        right.setText("Waiting for options.");       
        
        mainLayout = findViewById(R.id.maingladiator);
        
    }
	
	private View.OnClickListener extra = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			startActivity( new Intent ( MainGladiator.this, Extras.class ) );
		}
	};
	
	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {

	}
	@Override
	public void onPause(){
		super.onPause();
	}
	
	@Override
	public void onResume(){
		super.onResume();
	}
	
	
	@Override
	public void onSensorChanged(SensorEvent event) {
		if (event.sensor == sensor) {
			getAccelerometer(event);
		}			
	}
	
	private void getAccelerometer(SensorEvent event) {
		y = event.values[1];
				
		if ( status ){
			if (y > 5 ) {
				if(!up){
					mainLayout.setBackgroundResource(R.drawable.arenagreenback); 
					down = false;
					up = true;
					new CountDownTimer(1200, 100) {
					     public void onTick(long millisUntilFinished) {
						     if( up ){
					    	 	 if( millisUntilFinished >= 1200)
						    		 mainLayout.setBackgroundResource(R.drawable.arenagreenback01);
						    	 else if( millisUntilFinished > 1100 && millisUntilFinished < 1200 )
						    		 mainLayout.setBackgroundResource(R.drawable.arenagreenback015);
						    	 else if( millisUntilFinished > 1000 && millisUntilFinished < 1100 )
						    		 mainLayout.setBackgroundResource(R.drawable.arenagreenback02);
						    	 else if( millisUntilFinished > 900 && millisUntilFinished < 1000 )
						    		 mainLayout.setBackgroundResource(R.drawable.arenagreenback025);
						    	 else if( millisUntilFinished > 800 && millisUntilFinished < 900 )
						    		 mainLayout.setBackgroundResource(R.drawable.arenagreenback03);
						    	 else if( millisUntilFinished > 700 && millisUntilFinished < 800 )
						    		 mainLayout.setBackgroundResource(R.drawable.arenagreenback035);
						    	 else if( millisUntilFinished > 600 && millisUntilFinished < 700 )
						    		 mainLayout.setBackgroundResource(R.drawable.arenagreenback04);
						    	 else if( millisUntilFinished > 500 && millisUntilFinished < 600 )
						    		 mainLayout.setBackgroundResource(R.drawable.arenagreenback045);
						    	 else if( millisUntilFinished > 400 && millisUntilFinished < 500 )
						    		 mainLayout.setBackgroundResource(R.drawable.arenagreenback05);
						    	 else if( millisUntilFinished > 300 && millisUntilFinished < 400 )
						    		 mainLayout.setBackgroundResource(R.drawable.arenagreenback055);
						    	 else if( millisUntilFinished > 200 && millisUntilFinished < 300 )
						    		 mainLayout.setBackgroundResource(R.drawable.arenagreenback06);
						    	 else if( millisUntilFinished > 100 && millisUntilFinished < 200 )
						    		 mainLayout.setBackgroundResource(R.drawable.arenagreenback065);
						    	 else
						    		 mainLayout.setBackgroundColor(android.R.color.white);
						     }
						     else
						    	 cancel();
					     }
					     public void onFinish() {
					    	 mainLayout.setBackgroundResource(R.drawable.arenaback);
					     }
					}.start();
				}
			} 
			else if (y < -7) {
				if( !down ){
					mainLayout.setBackgroundResource(R.drawable.arenaredback); 
					down = true;
					up = false;				
					
				}
			} 
			else {
				down = false;
				up = false;
				mainLayout.setBackgroundResource(R.drawable.arenaback); 
			}
		}
	
	}

}
