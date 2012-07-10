package br.com.ufpb;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class RequestAccess {
	
	private Socket requestSocket;
	private ObjectOutputStream output;
	private ObjectInputStream input;
	private String ip;
	private String mensagem = "";
	
	public RequestAccess( String ip ){
		this.ip = ip;
	}
	
    public void runCliente(){
        
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
        
        do{
            try {
                mensagem = (String) input.readObject();
                if( mensagem.equals( "Conexao estabelecida com sucesso.") ){
                	enviaDados( "login" );
                }
                else if( mensagem.equals( "Access Granted" ) ){
                	Gladiator.setRequest( mensagem );
                }
            } 
            catch (ClassNotFoundException classNotFoundException ) {
            	classNotFoundException.printStackTrace();
            }

        }while( !mensagem.equals( "Access Granted" ) ); 
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
            input.close();
            output.close();
            requestSocket.close();
        } 
        catch ( IOException ioException ) {
            ioException.printStackTrace();
        }        
    }
	

}
