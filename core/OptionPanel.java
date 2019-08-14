package core;
import java.io.IOException;
import java.sql.SQLException;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.text.MaskFormatter;

public class OptionPanel {	    
    private JTextField codField;
    private JTextField senField;
    private JTextField numField;    
    private final String titulo = "Banco Maciota";
    
    public OptionPanel() {
    	
    }
    
    private MaskFormatter maskCodigo(String mask){
        MaskFormatter m = null;
   
        try{
            m = new MaskFormatter(mask);
            m.setInvalidCharacters(" ");
        }catch(Exception e){
        	e.printStackTrace();
        }
        return m;
    }
            
    public void resetFields(){
        this.codField = new JFormattedTextField(this.maskCodigo("##.###-#"));
        this.numField = new JFormattedTextField();
        this.senField = new JPasswordField();
    }
        
    public void alertPanel(String text, int icon){
        JOptionPane.showMessageDialog(null, 
                text, 
                this.titulo, 
                icon);
    }
        
    /** Inicia o Painel de login **/
    public AbstractConta logarPanel(){
    	this.resetFields();
    	
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
            String codigo = codField.getText();
            String senha = senField.getText();

            try {
				return FactoryConta.getConta(codigo, senha, 0.0, "Corrente").auth();
			} catch (ClassNotFoundException | IOException | NullPointerException e) {
				this.alertPanel("Login ou Senha Incorretos!", JOptionPane.ERROR_MESSAGE);
			}
        }
        
        return null;
    }
    
    /** Inicia o Painel de Cadastro **/
    public void criarContaPanel(){
        this.resetFields();
                
        Object[] txt = {"[Cadastro no Sistema]\n\n",
                        "Informe seus Dados:",
                        "Codigo:", this.codField,
                        "Senha :", this.senField,
                        "\n"};
        Object[] opc = { "Corrente", "Poupança" };
        
        int op = JOptionPane.showConfirmDialog(null, 
                txt, 
                this.titulo, 
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.INFORMATION_MESSAGE);
        
        String var = (String) JOptionPane.showInputDialog(null,
        		"Informe o Tipo de Conta:",
        		this.titulo,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                opc,
                opc[0]);
        
        if(op == JOptionPane.OK_OPTION && var != null){
            String cod = this.codField.getText();
            String sen = this.senField.getText();

            try{
                FactoryConta.getConta(cod, sen, 0.0, var).save();
                this.alertPanel("Conta criado com sucesso!", JOptionPane.INFORMATION_MESSAGE);
            }catch(Exception e){
                this.alertPanel("Não foi possivel criar a Conta!", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    /** Inicia o Painel de Saque **/
    public void saquePanel(AbstractConta conta){
        this.resetFields();
        
        Object[] txt = {"\nDigite o Valor de Saque:\n",
                        this.numField,
                        "\n"};

        int op = JOptionPane.showConfirmDialog(null,
                        txt,
                        this.titulo, 
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.INFORMATION_MESSAGE);
        
        if(op == JOptionPane.OK_OPTION){
            try {
				conta.fazerSaque(Double.parseDouble(this.numField.getText()));
			} catch (NumberFormatException | ClassNotFoundException | SQLException | IOException e) {
				this.alertPanel("Não foi possivel efetuar o Saque!", JOptionPane.ERROR_MESSAGE);
			}
        }
    }
    
    /** Inicia o Painel de Deposito **/
    public void depositoPanel(AbstractConta conta){
        this.resetFields();
        
        Object[] txt = {"\nDigite o Valor do Deposito:\n",
                        this.numField,
                        "\n"};

        int op = JOptionPane.showConfirmDialog(null,
                        txt,
                        this.titulo, 
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.INFORMATION_MESSAGE);
        
        if(op == JOptionPane.OK_OPTION){
            try {
				conta.fazerDeposito(Double.parseDouble(this.numField.getText()));
			} catch (NumberFormatException | ClassNotFoundException | SQLException | IOException e) {
				this.alertPanel("Não foi possivel efetuar o Deposito!", JOptionPane.ERROR_MESSAGE);
			}
        }
    }
    
    /** Inicia o Painel de Deletar Conta **/
    public void deletarContaPanel(AbstractConta conta) {
		int op = JOptionPane.showConfirmDialog(null,
						"Tem Certeza que deseja Deletar sua conta?\n",
		                this.titulo, 
		                JOptionPane.OK_CANCEL_OPTION,
		                JOptionPane.INFORMATION_MESSAGE);
		
		if(op == JOptionPane.OK_OPTION){
		    try {
				conta.delete();
			} catch (NumberFormatException | ClassNotFoundException | IOException e) {
				this.alertPanel("Não foi possivel Deletar sua Conta!", JOptionPane.ERROR_MESSAGE);
			}
		}
    }
}
