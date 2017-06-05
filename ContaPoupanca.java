/**
 *
 * @author elio
 */
public class ContaPoupanca extends Conta{
        
    public ContaPoupanca(String codigo, String senha, double saldo, String variacao) {
        this.setCodigo(codigo);
        this.setSenha(senha);
        this.setSaldo(saldo);
        this.setVariacao(variacao);
    }
    
    @Override
    public void doSaque(double s){
        if(checkSaldo(s))
            this.setSaldo(this.getSaldo()-s);
        else
            throw new IllegalArgumentException("Valor de Saque Invalido!");
    }

    @Override
    public double doSaldo() {
        return this.getSaldo();
    }

    
}
