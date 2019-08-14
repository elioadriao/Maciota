package core;
import java.io.IOException;
import java.lang.ClassNotFoundException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class SocketClient {
	private static String host = "127.0.0.1";
	private static int port = 5000;
	private Socket socket;
    private ObjectOutputStream output;
    private ObjectInputStream input;
	
	public SocketClient() {
        this.socket = null;
        this.output = null;
        this.input = null;
	}
	
	public void initConnection() throws UnknownHostException, IOException {
		this.socket = new Socket(host, port);
		this.output = new ObjectOutputStream(socket.getOutputStream());
		this.input = new ObjectInputStream(socket.getInputStream());
	}
	
	public AbstractConta getConta(String codigo, String senha) throws IOException, ClassNotFoundException {
		this.initConnection();
		
		this.output.writeObject("GET[CONTA]");
		if(((String) this.input.readObject()).equals("OK")) {
			this.output.writeObject(codigo+" "+senha);
			return FactoryConta.getContaFromStr((String) this.input.readObject());
		}
		
		return null;
	}
	
	public ArrayList<AbstractConta> getListaConta() throws IOException, ClassNotFoundException {
		this.initConnection();
		ArrayList<AbstractConta> response = new ArrayList<AbstractConta>();
		
		this.output.writeObject("GETLIST[CONTA]");
		if(((String) this.input.readObject()).equals("OK")) {
			int listSize =(int) this.input.readObject();
			for(int i=0; i < listSize; i++) {
				response.add(FactoryConta.getContaFromStr((String) this.input.readObject()));
			}	
		}
		
		return response;
	}
	
	public void createConta(AbstractConta conta) throws IOException, ClassNotFoundException {
		this.initConnection();
		
		this.output.writeObject("CREATE[CONTA]");
		if(((String) this.input.readObject()).equals("OK")) {
			this.output.writeObject(conta.toString());
		}
	}
	
	public void updateConta(AbstractConta conta) throws IOException, ClassNotFoundException {
		this.initConnection();
		
		this.output.writeObject("UPDATE[CONTA]");
		if(((String) this.input.readObject()).equals("OK")) {
			this.output.writeObject(conta.toString());
		}
	}
	
	public void deleteConta(AbstractConta conta) throws IOException, ClassNotFoundException {
		this.initConnection();
		
		this.output.writeObject("DELETE[CONTA]");
		if(((String) this.input.readObject()).equals("OK")) {
			this.output.writeObject(conta.toString());
		}
	}
}
