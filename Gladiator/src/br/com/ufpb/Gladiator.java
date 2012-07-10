package br.com.ufpb;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class Gladiator extends Activity {
    
	private TextView v01 = null;
	private TextView v02 = null;
	private Button qrcode = null;
	private Button help = null;
	//private Button insert = null;
	//private ImageButton comecar = null;
	private static String requestMessage = "";
	private AlertDialog.Builder builder = null;
	private static String ip = "";
	private static boolean x;
	private boolean connectionState = true;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
                                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.main);
        qrcode = (Button)findViewById(R.id.qrcode);
        qrcode.setOnClickListener( mScan );
       // insert = (Button)findViewById(R.id.insert );
       // insert.setOnClickListener( insertNumbers );
        v01 = (TextView)findViewById( R.id.mensagem );
        v02 = (TextView)findViewById( R.id.title);
        Typeface font = Typeface.createFromAsset(getAssets(), "Imperator.ttf");  
        v02.setTypeface( font );
        v02.setText("Welcome to Gladiator");
        v01.setTypeface(font);
        v01.setText("To get start you must scan your ticket QRCode.\nPress QRCODE button to scan or press HELP button to learn how it works.");
        
        builder = new AlertDialog.Builder(this);
    }
    
	private Button.OnClickListener mScan = new Button.OnClickListener() {
        public void onClick(View v) {
            Intent intent = new Intent("com.google.zxing.client.android.SCAN");
            intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
            startActivityForResult(intent, 0);
        }
    };
    /*private Button.OnClickListener insertNumbers = new Button.OnClickListener() {
        public void onClick(View v) {
        	startActivity( new Intent( Gladiator.this, InsertNumbers.class ) );
        	
        }
    };*/
    
    @Override
    public void onPause(){
    	
    	super.onPause();
    }
    
    @Override
    public void onResume(){
    	if( !connectionState )
    		conectar();
    	super.onResume();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                String contents = intent.getStringExtra("SCAN_RESULT");
                String format = intent.getStringExtra("SCAN_RESULT_FORMAT");
                
                qrcode.setClickable( false );
                qrcode.setEnabled( false );
                setIp( contents );
                conectar();
                
            } else if (resultCode == RESULT_CANCELED) {
            	builder.setTitle("Aviso").setMessage( "QRCode scan did'nt work. Please try again." ).setPositiveButton("OK", null).show();
            }
        }
    }
    
    private void conectar(){
    	if( !isNetworkAvailable() ){
    		avisoConexao();  
    	}
    	else{
    		connectionState = true;
    		RequestAccess request = new RequestAccess( getIp() );
    		request.runCliente();
    		if(getRequestMessage().equals("Access Granted") ){
    			startActivity( new Intent( Gladiator.this, MainGladiator.class ) );        		
    		}
    		else{
    			builder.setTitle("Warning").setMessage( requestMessage ).setPositiveButton("OK", null).show();
    		}
    	}
    }
    
    private boolean isNetworkAvailable(){
    	ConnectivityManager cm = (ConnectivityManager)getSystemService( CONNECTIVITY_SERVICE );
    	NetworkInfo info = cm.getActiveNetworkInfo();
    	return( info != null );
    }
    
    private void avisoConexao() {
    	connectionState = false;		
    	builder.setTitle("No connection!").setMessage( "You need connect with your cinema room wifi network.").setPositiveButton("OK", new DialogInterface.OnClickListener() {
    		
    		@Override
    		public void onClick(DialogInterface dialog, int which) {
    			startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
    		}
    	}).show(); 	
    }
    
    public static void setRequest( String requestMessage ){
    	Gladiator.requestMessage = requestMessage;    	
    }
    public static String getRequestMessage(){
    	return requestMessage;
    }
    
    public static boolean getX(){
    	return x;
    }
    public static void setX( boolean y ){
    	x = y;
    }
    
    public static String getIp(){
    	return ip;
    }
    
    public static void setIp( String s ){
    	ip = s;
    }



}
