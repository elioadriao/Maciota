/**
 *
 * @author elio
 */
public class ContaPoupanca extends Conta{
        
    public ContaPoupanca(String codigo, String senha, double saldo, String variacao) {
        /*super(codigo, senha, saldo);*/
        this.setCodigo(codigo);
        this.setSenha(senha);
        this.setSaldo(saldo);
        this.setVariacao(variacao);
    }

    @Override
    public void setAnuidade() {
        this.setSaldo(this.getSaldo()+this.getSaldo()*0.1);
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
