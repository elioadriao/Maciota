package core;

import java.io.IOException;
import java.sql.SQLException;

public abstract class AbstractConta{
	private String codigo;
    private String senha;
    private double saldo;
    private String variacao;
    private SocketClient socket;
    
    public String toString() {
    	return this.codigo+" "+this.senha+" "+this.saldo+" "+this.variacao;
    }
    
    public double getSaldo() {
        return this.saldo;
    }
    
    public void setSaldo(double s) {
        this.saldo = s;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
        
    public String getVariacao() {
    	return this.variacao;
    }

    public void setVariacao(String v) {
        this.variacao = v;
    }
        
    protected boolean isSaldoAvailable(double s) {
        return this.saldo >= s && s > 0;
    }
    
    public AbstractConta auth() throws ClassNotFoundException, IOException{
    	socket = new SocketClient();
    	AbstractConta aux = socket.getConta(this.codigo, this.senha);
    	this.saldo = aux.getSaldo();
    	this.variacao = aux.getVariacao();
    	
    	return aux;
    }
    
    public void save() throws ClassNotFoundException, IOException {
    	socket = new SocketClient();
    	socket.createConta(this);
    }
    
    public void update() throws ClassNotFoundException, IOException {
    	socket = new SocketClient();
    	socket.updateConta(this);
    }
    
    public void delete() throws ClassNotFoundException, IOException {
    	socket = new SocketClient();
    	socket.deleteConta(this);
    }
    
    public abstract void fazerSaque(double s) throws ClassNotFoundException, SQLException, IOException;
    
    public abstract void fazerDeposito(double s) throws ClassNotFoundException, SQLException, IOException;

}
