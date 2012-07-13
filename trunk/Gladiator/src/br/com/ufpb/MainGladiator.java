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
	private static MainGladiator instance;
	private boolean paused = false;
	private static AnswersReceiver answersReceiver;
	private boolean already = true;


	public static MainGladiator getInstance() {
        return instance;
	}
	
	@Override
	public void onCreate( Bundle savedInstanceState ){
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
                                WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.maingladiator);
		instance = this;
        
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        
        sensorManager.registerListener(this,sensor,	SensorManager.SENSOR_DELAY_UI);
		
		left = (TextView)findViewById( R.id.left);
		right = (TextView)findViewById( R.id.right);
		centro = (TextView)findViewById( R.id.centro);
		Typeface font = Typeface.createFromAsset(getAssets(), "Imperator.ttf");  
        left.setTypeface(font);
        right.setTypeface(font);
		extras = (Button)findViewById( R.id.extras );
		//watch = (Button)findViewById( R.id.watch);
        extras.setOnClickListener( extra );      
        
        
        left.setText( "?" );
        centro.setText( "Waiting for options." );
        right.setText("?");       
        
        mainLayout = findViewById(R.id.maingladiator);
        if (answersReceiver == null) {
            answersReceiver = new AnswersReceiver();
            answersReceiver.execute();
        }

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
		paused = true;
	}
	
	@Override
	public void onResume(){
		super.onResume();
		paused = false;
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
					answer = left.getText().toString();
					new CountDownTimer(3000, 500) {
					     public void onTick(long millisUntilFinished) {					     
						     if(!up)
						    	 cancel();						    	 
					     }
					     public void onFinish() {
					    	 if(!up)
					    		 cancel();
					    	 else{
					    		 mainLayout.setBackgroundResource(R.drawable.arenaback);
					    		 if(!already){
					    			 SendAnswer sendAnswer = new SendAnswer();
					    			 sendAnswer.execute("answer#"+answer);
						             left.setText("?");
						             right.setText("?");
						             centro.setText("Waiting for new options" );
						             setAlready( true );
					    		 }
					             
					    	 }					    	 
					     }
					}.start();
				}
			} 
			else if (y < -7) {
				if( !down ){
					mainLayout.setBackgroundResource(R.drawable.arenaredback); 
					down = true;
					up = false;		
					answer = right.getText().toString();
					new CountDownTimer(3000, 500) {
					     public void onTick(long millisUntilFinished) {					     
						     if(!down)
						    	 cancel();						    	 
					     }
					     public void onFinish() {
					    	 if(!down)
					    		 cancel();
					    	 else{
					    		 mainLayout.setBackgroundResource(R.drawable.arenaback);
					    		 if(!already){
					    			SendAnswer sendAnswer = new SendAnswer();
					             	sendAnswer.execute("answer#"+answer);
					             	left.setText("?");
					             	right.setText("?");
					             	centro.setText("Waiting for new options" );
					             	setAlready( true );
					    		 }
					    	 }					    	 
					     }
					}.start();					
				}
			} 
			else {
				down = false;
				up = false;
				mainLayout.setBackgroundResource(R.drawable.arenaback); 
			}
		}
	
	}
	public TextView getLeft(){
    	return left;
    }
    public TextView getRight(){
    	return right;
    }
    public TextView getCentro(){
    	return centro;
    }
    public boolean isPaused() {
        return paused;
    }
    public void setAlready( boolean x ){
    	already = x;
    }

}
