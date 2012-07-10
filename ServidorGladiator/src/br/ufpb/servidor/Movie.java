package br.ufpb.servidor;

public class Movie {
	
	private String name = "";
	private String synopsis = "";
	private String director = "";
	private String cast = "";
	private String year = "";
	
	public Movie( String name, String synopsis, String director, String cast, String year ){
		setDirector( director );
		setCast( cast );
		setName( name );
		setSynopsis( synopsis );
		setYear( year );
	}
	
	public String getName(){
		return name;
	}
	public void setName( String n ){
		name = n;
	}
	
	public String getSynopsis(){
		return synopsis;
	}
	public void setSynopsis( String s ){
		synopsis = s;
	}
	
	public String getDirector(){
		return director;
	}
	public void setDirector( String d ){
		director = d;
	}
	
	public String getCast(){
		return cast;
	}
	public void setCast( String c ){
		cast = c;
	}
	public String getYear(){
		return year;
	}
	public void setYear( String y ){
		year = y;
	}
	
}

