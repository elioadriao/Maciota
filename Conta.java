/**
 *
 * @author elio
 */
public abstract class Conta {
    
    private String codigo;
    private String senha;
    private double saldo;
    private String variacao;

    public boolean auth(String c, String s){
        return (this.codigo.equals(c) && this.senha.equals(s));
    }
    
    protected double getSaldo(){
        return this.saldo;
    }
    
    public void setSaldo(double s){
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
        
    public void setVariacao(String v){
        this.variacao = v;
    }

    public String getVariacao() {
        if(variacao.equals("1"))
            return "Corrente";
        else
            return "Poupança";
    }    
    
    protected boolean checkSaldo(double s){
        return this.saldo >= s;
    }
    
    public abstract void doSaque(double s);
    
    public void doDeposito(double s){
        this.saldo += s;
    }
      
    public abstract double doSaldo();
}
