package br.com.ufpb;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;


public class RequestExtras {
	private Socket requestSocket;
	private ObjectOutputStream output;
	private ObjectInputStream input;
	private Movie movie = null;
	private String ip = "";
	private String mensagem = "";
	
	public RequestExtras( String ip ) {
		this.ip = ip;		
		movie = new Movie( "", "", "", "", "" );
	}	
	
	public Movie getMovie(){
	        
	        try{
	            conecta();
	            capturaFluxo();
	            processaConexao();
	        }
	        catch( EOFException eofException ){
	        	eofException.printStackTrace();
	        }
	        catch( IOException ioException ){
	            ioException.printStackTrace();
	        }
	        finally{
	            fechaConexao();
	        }
	        return movie;
	    }
	    
	    private void conecta() throws IOException{
	        requestSocket = new Socket( InetAddress.getByName( ip ), 12346);
	    }
	    
	    private void capturaFluxo() throws IOException{
	        output = new ObjectOutputStream( requestSocket.getOutputStream() );
	        output.flush();
	        
	        input = new ObjectInputStream( requestSocket.getInputStream() );
	    }
	    
	    private void processaConexao() throws IOException{	        
	        	int count = 0;
	            try {
	                mensagem = (String) input.readObject();
	                if( mensagem.equals( "Conexao estabelecida com sucesso.") ){
	                	enviaDados( "extras" );
	                	mensagem = "";
	                	do{ 
	                		mensagem = ( String )input.readObject();
	                		if( !mensagem.equals( "" ) ){
	                			switch ( count ){
	                				case 0: movie.setName( mensagem );
	                						count++;
	                						mensagem = "";
	                						break;
	                				case 1: movie.setSynopsis( mensagem );
		            						count++;
		            						mensagem = "";
		            						break;
	                				case 2: movie.setDirector( mensagem );
		            						count++;
		            						mensagem = "";
		            						break;
	                				case 3: movie.setCast( mensagem );
		            						count++;
		            						mensagem = "";
		            						break;
	                				case 4: movie.setYear( mensagem );
		            						count++;
		            						mensagem = "";
		            						break;
	                			}
	                			
	                		}
	                	}while ( count < 5 );
	                }               
	            } 
	            catch (ClassNotFoundException classNotFoundException ) {
	            	classNotFoundException.printStackTrace();
	            }	   
	    }
	    
	    private void enviaDados( String mensagem ){
	        try {
	        	output.writeObject( mensagem );
	        	output.flush();
	        } 
	        catch ( IOException ioException ) {
	            ioException.printStackTrace();
	        }
	        
	    }    
	
	    private void fechaConexao() {
	        try {
	            //input.close();
	            //output.close();
	            requestSocket.close();
	        } 
	        catch ( IOException ioException ) {
	            ioException.printStackTrace();
	        }        
    }
	
}
