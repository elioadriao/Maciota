import java.text.DecimalFormat;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.text.MaskFormatter;

/**
 *
 * @author elio
 */
public class UserInterface {
    private final String titulo = "Banco Maciota";
    private Conta contAtiva = null;
    
    private JTextField codField;
    private JTextField varField;
    private JTextField senField;
    private JTextField numField;
    
    private CrudBD COFRE = CrudBD.initBD();
    
    //Inicia o loop da interface
    public void initUi(){        
        this.alertUi("BEM VINDO!", JOptionPane.INFORMATION_MESSAGE);
        
        int op = JOptionPane.showConfirmDialog (null,
                "Já Possui [Conta] no Sistema?",
                this.titulo, 
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);
        
        switch(op){
            case 0: this.logarUi();
                    break;
                
            case 1: this.criarContaUi();
                    break;
                   
            default: this.exitUi();
        }
    }
    
    //Inicia a interface de login
    public void logarUi(){
        this.clearFields();
        Object[] txt = {"[Login no Sistema]\n\n",
                        "Informe seus Dados:",
                        "Codigo:", this.codField,
                        "Senha :", this.senField,
                        "\n"};
        int op = JOptionPane.showConfirmDialog(null, 
                txt, 
                this.titulo, 
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.INFORMATION_MESSAGE);
           
        if(op == JOptionPane.OK_OPTION){
            String cod = codField.getText();
            String sen = senField.getText();

            this.logarEvent(cod, sen);
        }else{
            this.exitUi();
        }
    }
    
    //Inicia a interface de cadastro
    public void criarContaUi(){
        this.clearFields();
        this.alertUi("Preencha Corretamente!", JOptionPane.WARNING_MESSAGE);
        
        Object[] txt = {"[Cadastro no Sistema]\n\n",
                        "Informe seus Dados:",
                        "Codigo:", this.codField,
                        "Senha :", this.senField,
                        "Variação:", this.varField,
                        "\n"};
        
        int op = JOptionPane.showConfirmDialog(null, 
                txt, 
                this.titulo, 
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.INFORMATION_MESSAGE);
        
        if(op == JOptionPane.OK_OPTION){
            String cod = this.codField.getText();
            String sen = this.senField.getText();
            String var = this.varField.getText();
            
            
            try{
                COFRE.createConta(cod, sen, var);
            }catch(Exception e){
                this.alertUi(e.getMessage(), JOptionPane.ERROR_MESSAGE);
                this.exitUi();
            }
            //this.logarEvent(cod, sen);
            this.exitUi();
        }else{
            this.exitUi();
        }
    }
    
    //Interface do menu principal
    public void menuUi(){   
        this.clearFields();
        Object[] txt = {this.getLabel(),
                        "\nDigite a Operação:\n",
                        "1: Saldo",
                        "2: Saque",
                        "3: Deposito",
                        "0: Sair",
                        this.varField,
                        "\n"};

        int op = JOptionPane.showConfirmDialog(null,
                        txt,
                        this.titulo, 
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.INFORMATION_MESSAGE);

        if(op == JOptionPane.OK_OPTION){
            try{
                int wish = Integer.parseInt(this.varField.getText());
                
                switch(wish){
                    case 1: this.saldoUi();
                            break;

                    case 2: this.saqueUi();
                            break;

                    case 3: this.depositoUi();
                            break;
                            
                    case 0: this.logoff();
                            break;

                    default: this.exitUi();
                }
            }catch(Exception e){
                this.alertUi("Digito Informado Invalido!", JOptionPane.ERROR_MESSAGE);
                this.exitUi();
            }
        }else{
            this.exitUi();
        }
    }
    
    //Loop do sistema, se logado retorna pro menu, senao reinicia
    public void exitUi(){
        int op = JOptionPane.showConfirmDialog(null, 
                "Deseja Realizar Outra Operação?", 
                this.titulo, 
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);
        
        if(op == JOptionPane.OK_OPTION){
            if(this.isLogoff())
                this.initUi();
            else
                this.menuUi();
        }        
    }
    
    public void alertUi(String text, int icon){
        JOptionPane.showMessageDialog(null, 
                text, 
                this.titulo, 
                icon);
    }
    
    public void logoff(){
        this.setContAtiva(null);
        this.exitUi();
    }
    
    public boolean isLogoff() {
        return (contAtiva == null);
    }
    
    //Pop-up de saldo
    public void saldoUi(){
        DecimalFormat o = new DecimalFormat("R$ #,##0.00");
        String txt = this.getLabel()+"\n\nSaldo: "+o.format(contAtiva.doSaldo());
        
        this.alertUi(txt, JOptionPane.INFORMATION_MESSAGE);
        
        this.exitUi();
    }
    
    //Janela de saque
    public void saqueUi(){
        this.clearFields();
        Object[] txt = {this.getLabel(),
                        "\nDigite o Valor de Saque:\n",
                        this.numField,
                        "\n"};

        int op = JOptionPane.showConfirmDialog(null,
                        txt,
                        this.titulo, 
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.INFORMATION_MESSAGE);
        
        if(op == JOptionPane.OK_OPTION){
            double valor = Double.parseDouble(this.numField.getText());
            System.out.println(valor);
            
            try{
                this.contAtiva.doSaque(valor);
                
            }catch(Exception e){
                this.alertUi(e.getMessage(), JOptionPane.ERROR_MESSAGE);
                this.exitUi();
            }         
            this.COFRE.updateConta(contAtiva);
            this.exitUi();
            
        }else{
            this.exitUi();
        }
    }
    
    //Janela de deposito
    public void depositoUi(){
        this.clearFields();
        Object[] txt = {this.getLabel(),
                        "\nDigite o Valor do Deposito:\n",
                        this.numField,
                        "\n"};

        int op = JOptionPane.showConfirmDialog(null,
                        txt,
                        this.titulo, 
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.INFORMATION_MESSAGE);
        
        if(op == JOptionPane.OK_OPTION){
            double valor = Double.parseDouble(this.numField.getText());
                        
            this.contAtiva.doDeposito(valor);
            this.COFRE.updateConta(contAtiva);
            this.exitUi();
            
        }else{
            this.exitUi();
        }
    }
    
    //Explana a existencia da conta
    public void logarEvent(String cod, String sen){
        Conta aux = COFRE.getConta(cod, sen);
        
        if(aux == null){
            this.alertUi("Login ou Senha Incorretos!", JOptionPane.ERROR_MESSAGE);
            this.exitUi();
        }else{
            this.setContAtiva(aux);
            this.menuUi();
        }
    }

    public void setContAtiva(Conta contAtiva) {
        this.contAtiva = contAtiva;
    }
    
    private MaskFormatter maskCodigo(String mask){
        MaskFormatter m = null;
   
        try{
            m = new MaskFormatter(mask);
            m.setInvalidCharacters(" ");
        }catch(Exception e){
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            // será que cai aqui?
        }
        return m;
    }
            
    public void clearFields(){
        this.codField = new JFormattedTextField(this.maskCodigo("##.###-#"));
        this.varField = new JFormattedTextField(this.maskCodigo("#"));
        this.numField = new JFormattedTextField();
        this.senField = new JPasswordField();
    }
    
    public String getLabel(){
        String contaLabel = "Conta: ["+this.contAtiva.getCodigo()+"]\n"+
                            "Tipo   : ["+this.contAtiva.getVariacao()+"]";
        return contaLabel;
    }
    
}
