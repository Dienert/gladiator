package br.com.ufpb;

import android.app.TabActivity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;
import android.net.Uri;

public class Extras extends TabActivity {
	
	private ImageView foto = null;
	private TextView name = null;
	private TextView year = null;
	private TextView director = null;
	private TextView synopsis = null;
	private TextView cast = null;
	private String path = "";
	private Movie movie = null;
	private Button play = null;
	
	private static String message = "";
	
	@Override
	public void onCreate( Bundle savedInstanceState ){
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
                                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.extras);
        
		TabHost.TabSpec spec = getTabHost().newTabSpec( "tag1" );				
		spec.setIndicator( "Details", getResources().getDrawable( R.drawable.detalhes ) );
		spec.setContent( R.id.details );
		getTabHost().addTab( spec );
		
		spec = getTabHost().newTabSpec( "tag2" );		
		spec.setIndicator( "Trailer", getResources().getDrawable( R.drawable.sinopse ) );
		spec.setContent( R.id.trailer );		
		getTabHost().addTab( spec );
		getTabHost().setCurrentTab( 0 );
		
		RequestExtras r = new RequestExtras( Gladiator.getIp() );
		movie = r.getMovie();
		if(movie != null ){
			name = (TextView)findViewById(R.id.name);
			year = (TextView)findViewById(R.id.year);
			director = (TextView)findViewById(R.id.director);
			synopsis = (TextView)findViewById(R.id.synopsis);
			cast = (TextView)findViewById(R.id.cast);
			//foto = (ImageView)findViewById(R.id.photo);
			Typeface font = Typeface.createFromAsset(getAssets(), "Imperator.ttf");  
			name.setTypeface(font);
			year.setTypeface(font);
			director.setTypeface(font);
			synopsis.setTypeface(font);
			cast.setTypeface(font);
			
			name.setText( "Name: " + movie.getName() );
			year.setText( "Year: " + movie.getYear() );
			director.setText( "Director: " + movie.getDirector() );
			synopsis.setText( "Synopsis: " + movie.getSynopsis() );
			cast.setText( "Cast: " + movie.getCast() );
		}
		play = ( Button )findViewById( R.id.trailer );
		play.setOnClickListener( reproduzir );
		
	}
	
	private View.OnClickListener reproduzir = new View.OnClickListener() {
        public void onClick(View v) {
        	startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=IvTT29cavKo")));
        }
    };
	
}
