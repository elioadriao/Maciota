import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.wb.swt.SWTResourceManager;
import core.AbstractConta;
import core.OptionPanel;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormAttachment;

public class UserWindow {

	protected Shell shlBancoMaciota;
	private AbstractConta contaAtiva;
	private static OptionPanel op;
	
	public void setContaAtiva(AbstractConta conta) {
		this.contaAtiva = conta;
	}
	
	public void setContaAtivaLabels(Label codigoLabel, Label saldoLabel, Label tipoLabel) {
		codigoLabel.setText("Conta: "+this.contaAtiva.getCodigo());
		saldoLabel.setText("Saldo: R$"+String.format("%.2f", this.contaAtiva.getSaldo()));
		tipoLabel.setText("Tipo: "+this.contaAtiva.getVariacao());
	}
	
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shlBancoMaciota.open();
		shlBancoMaciota.layout();
		while (!shlBancoMaciota.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	protected void createContents() {
		shlBancoMaciota = new Shell();
		shlBancoMaciota.setMinimumSize(new Point(400, 500));
		shlBancoMaciota.setSize(400, 500);
		shlBancoMaciota.setText("Banco Maciota");
		shlBancoMaciota.setLayout(new FormLayout());
		
		Composite fullBodyFrame = new Composite(shlBancoMaciota, SWT.NONE);
		FormData fd_fullBodyFrame = new FormData();
		fd_fullBodyFrame.top = new FormAttachment(0, 10);
		fd_fullBodyFrame.left = new FormAttachment(0, 10);
		fd_fullBodyFrame.bottom = new FormAttachment(0, 461);
		fd_fullBodyFrame.right = new FormAttachment(0, 384);
		fullBodyFrame.setLayoutData(fd_fullBodyFrame);
		fullBodyFrame.setLayout(new FormLayout());
		
		Composite headerFrame = new Composite(fullBodyFrame, SWT.NONE);
		FormData fd_headerFrame = new FormData();
		fd_headerFrame.top = new FormAttachment(0, 10);
		fd_headerFrame.left = new FormAttachment(0, 10);
		fd_headerFrame.right = new FormAttachment(0, 364);
		headerFrame.setLayoutData(fd_headerFrame);
		
		Label logoLabel = new Label(headerFrame, SWT.CENTER);
		logoLabel.setFont(SWTResourceManager.getFont("Dingbats", 22, SWT.BOLD));
		logoLabel.setBounds(10, 10, 334, 35);
		logoLabel.setText("Banco Maciota");
		
		Label subLogoLabel = new Label(headerFrame, SWT.CENTER);
		subLogoLabel.setFont(SWTResourceManager.getFont("Dingbats", 9, SWT.BOLD));
		subLogoLabel.setBounds(10, 51, 334, 17);
		subLogoLabel.setText("Seja Bem vindo ao acesso Cliente.");
		
		Composite bodyFrame = new Composite(fullBodyFrame, SWT.NONE);
		fd_headerFrame.bottom = new FormAttachment(100, -371);
		FormData fd_bodyFrame = new FormData();
		fd_bodyFrame.right = new FormAttachment(headerFrame, 0, SWT.RIGHT);
		fd_bodyFrame.top = new FormAttachment(headerFrame, 6);
		StackLayout sl_bodyFrame = new StackLayout();
		fd_bodyFrame.bottom = new FormAttachment(0, 441);
		fd_bodyFrame.left = new FormAttachment(0, 10);
		bodyFrame.setLayoutData(fd_bodyFrame);
		bodyFrame.setLayout(sl_bodyFrame);
		
		Composite loginFrame = new Composite(bodyFrame, SWT.NONE);
		sl_bodyFrame.topControl = loginFrame;
		
		Label infoLoginLabel = new Label(loginFrame, SWT.NONE);
		infoLoginLabel.setFont(SWTResourceManager.getFont("Noto Sans", 14, SWT.BOLD));
		infoLoginLabel.setBounds(54, 191, 246, 26);
		infoLoginLabel.setText("Efetue o Login no Sistema:");
		
		Label infoLoginLabel2 = new Label(loginFrame, SWT.NONE);
		infoLoginLabel2.setFont(SWTResourceManager.getFont("Noto Sans", 14, SWT.BOLD));
		infoLoginLabel2.setBounds(76, 69, 202, 26);
		infoLoginLabel2.setText("Crie uma Nova Conta:");
		
		Button logarButton = new Button(loginFrame, SWT.NONE);
		logarButton.setForeground(SWTResourceManager.getColor(SWT.COLOR_GREEN));
		logarButton.setBounds(111, 223, 132, 34);
		logarButton.setText("Logar");
				
		Button criarContaButton = new Button(loginFrame, SWT.NONE);
		criarContaButton.setForeground(SWTResourceManager.getColor(SWT.COLOR_YELLOW));
		criarContaButton.setBounds(111, 101, 132, 34);
		criarContaButton.setText("Criar Conta");
		
		Composite menuFrame = new Composite(bodyFrame, SWT.NONE);
		
		Label menuContaLabel = new Label(menuFrame, SWT.NONE);
		menuContaLabel.setToolTipText("Codigo da Conta");
		menuContaLabel.setFont(SWTResourceManager.getFont("Noto Sans", 14, SWT.BOLD));
		menuContaLabel.setBounds(10, 10, 334, 26);
		menuContaLabel.setText("Conta:");
		
		Label menuSaldoLabel = new Label(menuFrame, SWT.NONE);
		menuSaldoLabel.setToolTipText("Valor do Saldo");
		menuSaldoLabel.setFont(SWTResourceManager.getFont("Noto Sans", 14, SWT.BOLD));
		menuSaldoLabel.setBounds(10, 74, 334, 26);
		menuSaldoLabel.setText("Saldo:");
		
		Label menuTipoLabel = new Label(menuFrame, SWT.NONE);
		menuTipoLabel.setToolTipText("Tipo da Conta");
		menuTipoLabel.setText("Tipo:");
		menuTipoLabel.setFont(SWTResourceManager.getFont("Noto Sans", 14, SWT.BOLD));
		menuTipoLabel.setBounds(10, 42, 334, 26);
		
		Composite subMenuFrame = new Composite(menuFrame, SWT.NONE);
		subMenuFrame.setBounds(10, 140, 334, 205);
		
		Button menuDepositoButton = new Button(subMenuFrame, SWT.NONE);
		menuDepositoButton.setBounds(10, 10, 314, 34);
		menuDepositoButton.setForeground(SWTResourceManager.getColor(SWT.COLOR_CYAN));
		menuDepositoButton.setToolTipText("Fazer Deposito");
		menuDepositoButton.setText("Deposito");
		
		Button menuSaqueButton = new Button(subMenuFrame, SWT.NONE);
		menuSaqueButton.setBounds(10, 62, 314, 34);
		menuSaqueButton.setForeground(SWTResourceManager.getColor(SWT.COLOR_CYAN));
		menuSaqueButton.setToolTipText("Fazer Saque");
		menuSaqueButton.setText("Saque");
		
		Button menuSairButton = new Button(subMenuFrame, SWT.NONE);
		menuSairButton.setBounds(10, 161, 314, 34);
		menuSairButton.setForeground(SWTResourceManager.getColor(SWT.COLOR_YELLOW));
		menuSairButton.setToolTipText("Volta ao Menu Inicial");
		menuSairButton.setText("Sair");
		
		Button menuDeleteContaButton = new Button(subMenuFrame, SWT.NONE);
		menuDeleteContaButton.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		menuDeleteContaButton.setToolTipText("Apaga conta do Sistema");
		menuDeleteContaButton.setBounds(10, 115, 314, 34);
		menuDeleteContaButton.setText("Excluir Conta");
		
		// Buttons Events //
		
		logarButton.addListener(SWT.Selection, new Listener(){
			@Override
			public void handleEvent(Event arg0) {
				setContaAtiva(op.logarPanel());
				
				if (contaAtiva != null) {
					setContaAtivaLabels(menuContaLabel, menuSaldoLabel, menuTipoLabel);
					sl_bodyFrame.topControl = menuFrame;
					bodyFrame.layout();
				}
			}
		});
		
		criarContaButton.addListener(SWT.Selection, new Listener(){
			@Override
			public void handleEvent(Event arg0) {
				op.criarContaPanel();
			}
		});
		
		menuSaqueButton.addListener(SWT.Selection, new Listener(){
			@Override
			public void handleEvent(Event arg0) {
				op.saquePanel(contaAtiva);		
				setContaAtivaLabels(menuContaLabel, menuSaldoLabel, menuTipoLabel);
			}
		});
		
		menuDepositoButton.addListener(SWT.Selection, new Listener(){
			@Override
			public void handleEvent(Event arg0) {
				op.depositoPanel(contaAtiva);
				setContaAtivaLabels(menuContaLabel, menuSaldoLabel, menuTipoLabel);
			}
		});
		
		menuSairButton.addListener(SWT.Selection, new Listener(){
			@Override
			public void handleEvent(Event arg0) {
				contaAtiva = null;
				sl_bodyFrame.topControl = loginFrame;
				bodyFrame.layout();
			}
		});
		
		menuDeleteContaButton.addListener(SWT.Selection, new Listener(){
			@Override
			public void handleEvent(Event arg0) {
				op.deletarContaPanel(contaAtiva);
				contaAtiva = null;
				sl_bodyFrame.topControl = loginFrame;
				bodyFrame.layout();
			}
		});
	}
	
	public static void main(String[] args) {
		try {
			op = new OptionPanel();
			UserWindow window = new UserWindow();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
