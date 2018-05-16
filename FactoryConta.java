/**
 *
 * @author elio.adriao
 * @link https://github.com/elioadriao/Maciota
 * 
 **/
public class FactoryConta {
    
	/** UTILIZA O PADRAO DE PROJETO FACTORY METHOD NA CRIACAO DAS CONTAS **/
    public static Conta getConta(String c, String se, double sa, String v){
        if(v.equals("1"))
            return new ContaCorrente(c, se, sa, v);
        else
            return new ContaPoupanca(c, se, sa, v);
    }
}
