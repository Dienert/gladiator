package br.ufpb.servidor;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
/**
 *
 * @author brian
 */
public class RequestServer{

    private ObjectOutputStream saida;
    private ObjectInputStream entrada;
    private ServerSocket servidor;
    private Socket conexao;
    private Movie movie;
    
    
    public RequestServer(){
        movie = new Movie( "Gladiator", "A man robbed of his name and his dignity strives to win them back, and gain the freedom of his people, in this epic historical drama from director Ridley Scott. In the year 180, the death of emperor Marcus Aurelius (Richard Harris) throws the Roman Empire into chaos. Maximus (Russell Crowe) is one of the Roman army's most capable and trusted generals and a key advisor to the emperor. As Marcus' devious son Commodus (Joaquin Phoenix) ascends to the throne, Maximus is set to be executed.", "Ridley Scott", "Russell Crowe - Maximus Joaquin Phoenix - Commodus" +
        		"    Connie Nielsen - Lucilla    Oliver Reed - Proximo    Derek Jacobi - Gracchus    Djimon Hounsou - Juba    Richard Harris - Marcus Aurelius    David Schofield - Falco    John Shrapnel - Gaius    Tomas Arana - Quintus    Ralph Moeller - Hagen    Spencer Treat Clark - Lucius    David Hemmings - Cassius    Giorgio Cantarini - Maximus' Son    Omid Djalili -    Sven Ole Thorsen - Titus", "2000");
       
    }
    //
    public void runServer(){
        
        try{
            servidor = new ServerSocket( 12346, 100);           //instancia o servidor na porta 12345 com n limite de 100 conxs
            try{
            	while ( true ){
	                esperaConexao();
	                capturaFluxo();
	                processaDados();
            	}
            }
            catch( EOFException eofException ){                     
                mostraMensagem( "\nO servidor terminou a conexao." );
            }
            finally{
                fechaConexao();
            }
        }
        catch( IOException ioException ){                           //trata a excecao de entrada e saida
            ioException.printStackTrace();
        }
    }
    
    private void esperaConexao() throws IOException{
        mostraMensagem( "\nAguardando conexao..." );
        conexao = servidor.accept();
        mostraMensagem( "\nA conexao com o " + conexao.getInetAddress().getHostAddress() );
    }
    
    private void capturaFluxo() throws IOException{
        saida = new ObjectOutputStream( conexao.getOutputStream() );
        saida.flush();
        
        entrada = new ObjectInputStream( conexao.getInputStream() );
        mostraMensagem( "\nFluxo estabelecido." );
    }

    private void processaDados() throws IOException{
        String mensagem = "Conexao estabelecida com sucesso.";
        enviaDados( mensagem );
        
        try {
                mensagem = (String) entrada.readObject();
                if( mensagem.equals( "login" ) ){
                	enviaDados( "Access Granted" );
                	mostraMensagem( "\n" + mensagem );
                }
                else if( mensagem.equals( "extras" ) ){
                	enviaDados( movie.getName() );
                	mostraMensagem( "\n" + mensagem );
                	enviaDados( movie.getSynopsis() );
                	mostraMensagem( "\n" + mensagem );
                	enviaDados( movie.getDirector() );
                	mostraMensagem( "\n" + mensagem );
                	enviaDados( movie.getCast() );
                	mostraMensagem( "\n" + mensagem );
                	enviaDados( movie.getYear() );
                	mostraMensagem( "\n" + mensagem );
                }
                 
            } 
        catch ( ClassNotFoundException classNotFoundException ) {
                mostraMensagem( "\nTipo de objeto recebido desconhecido." );
        }        
        
    }
    
    private void enviaDados( String mensagem ){
        try {
            saida.writeObject( mensagem );
            saida.flush();
            mostraMensagem( "\nSERVER>>>> " + mensagem );
        } 
        catch ( IOException ioExecption ) {
            mostraMensagem( "\nErro ao escrever o objeto." );
        }
        
    }    
    
    private void mostraMensagem( String mensagem ){
        System.out.println( mensagem );
    }

    private void fechaConexao() {
        try {
            entrada.close();
            saida.close();
            conexao.close();
        } 
        catch ( IOException ioException ) {
            ioException.printStackTrace();
        }        
        mostraMensagem( "\nConexao encerrada." );
    }
    
}
