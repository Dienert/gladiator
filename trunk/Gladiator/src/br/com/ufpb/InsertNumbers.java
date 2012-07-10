package br.com.ufpb;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class InsertNumbers extends Activity{
	
	private TextView v01 = null;
	private EditText v02 = null;
	private Button v03 = null;

	
	@Override
	public void onCreate( Bundle savedInstanceState ){
		super.onCreate(savedInstanceState);
	    requestWindowFeature(Window.FEATURE_NO_TITLE);
	    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
	                            WindowManager.LayoutParams.FLAG_FULLSCREEN);
	
	    setContentView(R.layout.insertnumbers);
	    v01 = (TextView)findViewById( R.id.mensagem );
	    v02 = (EditText)findViewById( R.id.caixa );
	    Typeface font = Typeface.createFromAsset(getAssets(), "Imperator.ttf");  
        v01.setTypeface( font );
        v01.setText("Type the code here:");
        v03 = ( Button ) findViewById ( R.id.save );
        v03.setOnClickListener( save );
        
	}
	
	private Button.OnClickListener save = new Button.OnClickListener() {
        public void onClick(View v) {
            Gladiator.setIp( v02.getText().toString() );
            Gladiator.setX( true );
            onDestroy();
        }
    };
    
    @Override    
    public void onDestroy(){
        super.onDestroy();
    }
	
}
