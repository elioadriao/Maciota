import java.sql.*;
import java.util.Vector;

/**
 *
 * @author elio
 */
public class CrudBD {
    private static CrudBD cofre;
    private Vector<Conta> ListaContas = new Vector<Conta>();
    
    private CrudBD(){}
    
    public static synchronized CrudBD initBD(){
        String sql = "CREATE TABLE IF NOT EXISTS Contas ("+
                        "id int AUTO_INCREMENT,"+
                        "codigo varchar(20) NOT NULL UNIQUE,"+
                        "senha varchar(50) NOT NULL,"+
                        "saldo double,"+
                        "variacao varchar(1) NOT NULL,"+
                        "PRIMARY KEY (id));";
        
        if(cofre == null){
            cofre = new CrudBD();
            cofre.update(sql);
        }
        return cofre;
    }
    
    private void showError(Exception e){
        System.err.println(e.getClass().getName()+": "+e.getMessage());
        //System.exit(1);
    }
    
    private Connection connect(){
        Connection c = null;
        
        try{
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:cofre.db");
            
        }catch(Exception e){
            this.showError(e);
        }
        System.out.println("Opened database successfully");
        
        return c;
    }
    
    public void update(String sql){
        Statement stmt = null;
        
        try {
            Connection c = this.connect();
            stmt = c.createStatement();

            stmt.executeUpdate(sql);
            stmt.close();
            c.close();
            
        }catch(Exception e){
            this.showError(e);
        }
        System.out.println("Table updated successfully");
    }
    
    public void delete(String sql){
        
    }
    
    public Vector<Conta> getListaContas(){
        try{
            Connection con = this.connect();
            Statement stm = con.createStatement();
            ResultSet res = stm.executeQuery("SELECT * FROM Contas;");

            while(res.next()){
                String co = res.getString("codigo");
                String se  = res.getString("senha");
                double sa = res.getDouble("saldo");
                String v = res.getString("variacao");
                
                Conta x = FactoryConta.getConta(co, se, sa, v);
                this.ListaContas.add(x);         
            }
            res.close();
            stm.close();
            con.close();
            
        }catch(Exception e){
            this.showError(e);
        }
        return this.ListaContas;
    }

    public void createConta(String c, String s, String v){
        String sql = "INSERT INTO Contas (codigo, senha, saldo, variacao) "
                    +"VALUES ('"+c+"', '"+s+"', 0.0, '"+v+"');";
        if(c.equals("") || s.equals("") || v.equals(""))
            throw new IllegalArgumentException("Dados Invalidos!");
        else
            this.update(sql);
    }
    
    public void updateConta(Conta c){
        String sql = "UPDATE Contas SET saldo = "+c.getSaldo()+" "
                    +"WHERE codigo = '"+c.getCodigo()+"';";
        
        this.update(sql);        
    }
    
    public Conta getConta(String c, String s){
        Conta conta = null;
        //this.getListaContas();        
        
        for(Conta aux : this.getListaContas()){
            if(aux.auth(c, s)){
                conta = aux;
                break;
            }
        }
        return conta;
    }
    
    private void setListaContas(Vector<Conta> ListaContas) {
        this.ListaContas = ListaContas;
    }
    
    
}
