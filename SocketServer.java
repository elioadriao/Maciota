import java.io.IOException;
import java.lang.ClassNotFoundException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import core.AbstractConta;
import core.DataBaseController;
import core.FactoryConta;
import io.reactivex.Observable;
import java.net.ServerSocket;

public class SocketServer {
    private static ServerSocket server;
    private static int port = 5000;
    private ObjectOutputStream output;
    private ObjectInputStream input;
    private Socket socket;
    private static DataBaseController cofre;
    
    public SocketServer() {
        this.socket = null;
    	this.output = null;
        this.input = null;
        try {
			cofre = DataBaseController.init();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
    }
    
    public void startServer() throws IOException, ClassNotFoundException, SQLException {
    	server = new ServerSocket(port);
    }
    
    public void shutServer() throws IOException {
    	server.close();
    }
    
    public void loopConnection() throws IOException, ClassNotFoundException{    	
    	while(true) {
    		System.out.println("<=> Servidor Aguardando.");
    		this.socket = server.accept();
    		System.out.println("<-- Cliente Conectado.");
        	this.output = new ObjectOutputStream(this.socket.getOutputStream());
        	this.input = new ObjectInputStream(this.socket.getInputStream());
        	
    		switch ((String)this.input.readObject()) {
			case "GET[CONTA]":
				this.output.writeObject("OK");
				String[] infoConta = ((String) this.input.readObject()).split(" ");
				
				try {
					Observable.just(cofre.getConta(infoConta[0], infoConta[1]))
					.delaySubscription(2500, TimeUnit.MILLISECONDS)
					.map(conta -> conta.toString())
					.blockingSubscribe(conta -> this.output.writeObject(conta));
				} catch (SQLException | NullPointerException e) {
					// TODO Auto-generated catch block
				}				
				break;
			
			case "GETLIST[CONTA]":
				this.output.writeObject("OK");
				try {
					ArrayList<AbstractConta> listaContas = cofre.getListaContas();
					this.output.writeObject(listaContas.size());
					
					Observable.fromIterable(listaContas)
					.map(conta -> conta.toString())
					.subscribe(conta -> this.output.writeObject(conta));
				} catch (SQLException | NullPointerException e) {
					// TODO Auto-generated catch block
				}
				break;
			
			case "CREATE[CONTA]":
				this.output.writeObject("OK");
				try {
					Observable.just(this.input.readObject())
					.delaySubscription(1500, TimeUnit.MILLISECONDS)
					.map(conta -> (String) conta)
					.map(conta -> FactoryConta.getContaFromStr(conta))
					.blockingSubscribe(conta -> cofre.createConta(conta));
				} catch (ClassNotFoundException | NullPointerException e) {
					// TODO Auto-generated catch block
				}
				break;
			
			case "UPDATE[CONTA]":
				this.output.writeObject("OK");
				try {
					Observable.just(this.input.readObject())
					.delaySubscription(500, TimeUnit.MILLISECONDS)
					.map(conta -> (String) conta)
					.map(conta -> FactoryConta.getContaFromStr(conta))
					.blockingSubscribe(conta -> cofre.updateConta(conta));
				} catch (ClassNotFoundException | NullPointerException e) {
					// TODO Auto-generated catch block
				}
				break;
			
			case "DELETE[CONTA]":
				this.output.writeObject("OK");
				try {
					Observable.just(this.input.readObject())
					.delaySubscription(2000, TimeUnit.MILLISECONDS)
					.map(conta -> (String) conta)
					.map(conta -> FactoryConta.getContaFromStr(conta))
					.blockingSubscribe(conta -> cofre.deleteConta(conta));
				} catch (ClassNotFoundException | NullPointerException e) {
					// TODO Auto-generated catch block
				}
				break;

			default:
				break;
			}
    		
    		this.output.close();
        	this.input.close();
        	this.socket.close();
    	}   	
    }

	public static void main(String[] args) {
		SocketServer server = new SocketServer();
		try {
			server.startServer();
			server.loopConnection();
			server.shutServer();	
		} catch (ClassNotFoundException | IOException | SQLException e) {
			e.printStackTrace();
		}
	}
}
