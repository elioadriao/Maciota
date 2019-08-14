package core;

import java.sql.*;
import java.util.ArrayList;

public class DataBaseController {
    private static DataBaseController vault;
    private Connection connection = null;
    private ResultSet result = null;
    private Statement statement = null;
    
    private DataBaseController(){}
    
    /** Inicia o Banco criando uma unica instancia. 
     * @throws SQLException 
     * @throws ClassNotFoundException **/
    public static synchronized DataBaseController init() throws ClassNotFoundException, SQLException{
        String contas = "CREATE TABLE IF NOT EXISTS Contas ("+
                        "id int AUTO_INCREMENT,"+
                        "codigo varchar(20) NOT NULL UNIQUE,"+
                        "senha varchar(50) NOT NULL,"+
                        "saldo double,"+
                        "variacao varchar(10) NOT NULL,"+
                        "PRIMARY KEY (id));";
        
        if(vault == null){
            vault = new DataBaseController();
            vault.update(contas);
        }
        return vault;
    }
        
    /** Retorna uma Statement, à partir da conexão com o banco.  
     * @throws SQLException 
     * @throws ClassNotFoundException**/
    private Statement createConnection() throws ClassNotFoundException, SQLException{        
        Class.forName("org.sqlite.JDBC");
        this.connection = DriverManager.getConnection("jdbc:sqlite:cofre.db");
                
        return this.connection.createStatement();
    }
    
    /** Executa a querry informada. 
     * @throws SQLException 
     * @throws ClassNotFoundException **/
    private void update(String sql) throws ClassNotFoundException, SQLException{
    	this.statement = createConnection();
        this.statement.executeUpdate(sql);
        this.statement.close();
        this.connection.close();

        System.out.println("--> Table Updated...");
    }
      
    /** Pega Lista de Contas do Banco. 
     * @throws SQLException 
     * @throws ClassNotFoundException **/
    public ArrayList<AbstractConta> getListaContas() throws ClassNotFoundException, SQLException {
    	ArrayList<AbstractConta> ListaContas = new ArrayList<AbstractConta>();
    	this.statement = createConnection();
        this.result = this.statement.executeQuery("SELECT * FROM Contas;");

        while(this.result.next()){
            String co = this.result.getString("codigo");
            String se  = this.result.getString("senha");
            double sa = this.result.getDouble("saldo");
            String v = this.result.getString("variacao");
            ListaContas.add(FactoryConta.getConta(co, se, sa, v));         
        }
        this.result.close();
        this.statement.close();
        this.connection.close();
        
        return ListaContas;
    }
    
    /** Cria uma nova Conta no Banco. 
     * @throws SQLException 
     * @throws ClassNotFoundException **/
    public void createConta(AbstractConta conta) throws ClassNotFoundException, SQLException{
        String querry = "INSERT INTO Contas (codigo, senha, saldo, variacao) "+
                    	"VALUES ('"+conta.getCodigo()+"', '"+conta.getSenha()+"', 0.0, '"+conta.getVariacao()+"');";
        if(conta.getCodigo().equals("") || conta.getSenha().equals("") || conta.getVariacao().equals(""))
            throw new IllegalArgumentException("Dados Invalidos!");
        else
            this.update(querry);
    }
    
    /** Atualiza informações da Conta. 
     * @throws SQLException 
     * @throws ClassNotFoundException **/
    public void updateConta(AbstractConta conta) throws ClassNotFoundException, SQLException{
        String querry = "UPDATE Contas SET saldo = "+conta.getSaldo()+" "+
                    	"WHERE codigo = '"+conta.getCodigo()+"';";
        
        this.update(querry);
    }
    
    /** Pega uma Conta especifica do Banco 
     * @throws SQLException 
     * @throws ClassNotFoundException **/
    public AbstractConta getConta(String codigo, String senha) throws ClassNotFoundException, SQLException{
    	this.statement = createConnection();
    	this.result = this.statement.executeQuery("SELECT * FROM Contas WHERE codigo = '"+codigo+"' AND senha = '"+senha+"';");

        String co = this.result.getString("codigo");
        String se  = this.result.getString("senha");
        double sa = this.result.getDouble("saldo");
        String v = this.result.getString("variacao");
        
        this.result.close();
        this.statement.close();
        this.connection.close();
        
        return FactoryConta.getConta(co, se, sa, v);  
    }
    
    /** Deleta uma Conta do Banco. 
     * @throws SQLException 
     * @throws ClassNotFoundException **/
    public void deleteConta(AbstractConta conta) throws ClassNotFoundException, SQLException {
    	String queryConta = "DELETE FROM Contas WHERE codigo = '"+conta.getCodigo()+"';";
    	
    	this.update(queryConta);
    }    
}
