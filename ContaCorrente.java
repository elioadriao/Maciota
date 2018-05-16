/**
 *
 * @author elio.adriao
 * @link https://github.com/elioadriao/Maciota
 * 
 **/
public class ContaCorrente extends Conta{

    public ContaCorrente(String codigo, String senha, double saldo, String variacao) {
        this.setCodigo(codigo);
        this.setSenha(senha);
        this.setSaldo(saldo);
        this.setVariacao(variacao);
    }
    
    @Override
    public void doSaque(double s){
        s += 0.5;
        
        if(checkSaldo(s))
            this.setSaldo(this.getSaldo()-s);
        else
            throw new IllegalArgumentException("Valor de Saque Invalido!");
    }

    @Override
    public double doSaldo() {
                
        if(this.checkSaldo(0.1)){
            this.setSaldo(this.getSaldo()-0.1);
        }
        return this.getSaldo();
    }


}
