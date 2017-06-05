/**
 *
 * @author elio
 */
public class FactoryConta {
    
    public static Conta getConta(String c, String se, double sa, String v){
        if(v.equals("1"))
            return new ContaCorrente(c, se, sa, v);
        else
            return new ContaPoupanca(c, se, sa, v);
    }
}
