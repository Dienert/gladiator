package br.ufpb.servidor;

public class MainServer {
	public static void main( String [] args ){
		RequestServer server = new RequestServer();
		server.runServer();
	}
}
