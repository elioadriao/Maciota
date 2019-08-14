package core;

import java.io.IOException;

public class ContaPoupanca extends AbstractConta{

	public ContaPoupanca(String codigo, String senha, double saldo, String variacao) {
        this.setCodigo(codigo);
        this.setSenha(senha);
        this.setSaldo(saldo);
        this.setVariacao(variacao);
    }
    
    @Override
    public void fazerSaque(double valor) throws ClassNotFoundException, IOException{
        if(isSaldoAvailable(valor))
            this.setSaldo(this.getSaldo()-valor);
        else
            throw new IllegalArgumentException("Valor de Saque Invalido!");
        
        this.update();
    }

	@Override
	public void fazerDeposito(double valor) throws ClassNotFoundException, IOException {
		this.setSaldo(this.getSaldo()+valor);
		this.update();
	}

    
}
