package core;

public class FactoryConta {
    
	/** Gera uma conta a partir dos dados Informados. **/
    public static AbstractConta getConta(String codigo, String senha, double saldo, String variacao){
        if(variacao.equals("Corrente"))
            return new ContaCorrente(codigo, senha, saldo, variacao);
        else if (variacao.equals("Poupança"))
            return new ContaPoupanca(codigo, senha, saldo, variacao);
        else
        	return null;
    }
    
    /** Gera uma conta a partir de sua versao String **/
    public static AbstractConta getContaFromStr(String contaStr) {
    	String[] contaAux = contaStr.split(" ");
    	
    	return getConta(contaAux[0], contaAux[1], Double.parseDouble(contaAux[2]), contaAux[3]);
    }
}
