import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import java.io.IOException;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.wb.swt.SWTResourceManager;
import core.SocketClient;
import io.reactivex.Observable;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Listener;

public class AdminWindow {

	protected Shell shlAdminBancoMaciota;
	private SocketClient socket;
	
	public void addToList(List list, String item) {
		list.add(item);
	}
	
	public void removeFromList(List list, String item) {
		list.remove(item);
	}
		
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shlAdminBancoMaciota.open();
		shlAdminBancoMaciota.layout();
		while (!shlAdminBancoMaciota.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	protected void createContents() {
		shlAdminBancoMaciota = new Shell();
		shlAdminBancoMaciota.setMinimumSize(new Point(400, 500));
		shlAdminBancoMaciota.setSize(400, 500);
		shlAdminBancoMaciota.setText("Admin Banco Maciota");
		
		Composite fullBodyFrame = new Composite(shlAdminBancoMaciota, SWT.NONE);
		fullBodyFrame.setBounds(10, 10, 374, 451);
		
		Composite headerFrame = new Composite(fullBodyFrame, SWT.NONE);
		headerFrame.setBounds(10, 10, 354, 70);
		
		Label logoLabel = new Label(headerFrame, SWT.CENTER);
		logoLabel.setText("Banco Maciota");
		logoLabel.setFont(SWTResourceManager.getFont("Dingbats", 22, SWT.BOLD));
		logoLabel.setBounds(10, 10, 334, 35);
		
		Label subLogoLabel = new Label(headerFrame, SWT.CENTER);
		subLogoLabel.setText("Seja Bem vindo ao acesso Administrador.");
		subLogoLabel.setFont(SWTResourceManager.getFont("Dingbats", 9, SWT.BOLD));
		subLogoLabel.setBounds(10, 51, 334, 17);
		
		Composite bodyFrame = new Composite(fullBodyFrame, SWT.NONE);
		bodyFrame.setBounds(10, 86, 354, 355);
		bodyFrame.setLayout(null);
		
		Button contasPoupancaButton = new Button(bodyFrame, SWT.NONE);
		contasPoupancaButton.setToolTipText("");
		contasPoupancaButton.setBounds(10, 10, 164, 34);
		contasPoupancaButton.setText("Contas Poupança");
		
		Button contasCorrenteButton = new Button(bodyFrame, SWT.NONE);
		contasCorrenteButton.setToolTipText("");
		contasCorrenteButton.setBounds(180, 10, 164, 34);
		contasCorrenteButton.setText("Contas Correntes");
		
		List bodyList = new List(bodyFrame, SWT.BORDER);
		bodyList.setLocation(10, 50);
		bodyList.setSize(334, 295);
		
		contasPoupancaButton.addListener(SWT.Selection, new Listener(){
			@Override
			public void handleEvent(Event arg0) {
				socket = new SocketClient();
				bodyList.removeAll();
				try {
					Observable.fromIterable(socket.getListaConta())
					.filter(conta -> conta.getVariacao().equals("Poupança"))
					.map(conta -> "["+conta.getVariacao()+"]["+conta.getCodigo()+"]["+conta.getSenha()+"]["+conta.getSaldo()+"]")
					.subscribe(conta -> addToList(bodyList, conta));
				} catch (ClassNotFoundException | IOException e) { 
					// TODO Auto-generated catch block
				}
			}
		});
		
		contasCorrenteButton.addListener(SWT.Selection, new Listener(){
			@Override
			public void handleEvent(Event arg0) {
				socket = new SocketClient();
				bodyList.removeAll();
				try {
					Observable.fromIterable(socket.getListaConta())
					.filter(conta -> conta.getVariacao().equals("Corrente"))
					.map(conta -> "["+conta.getVariacao()+"]["+conta.getCodigo()+"]["+conta.getSenha()+"][Saldo:"+conta.getSaldo()+"]")
					.subscribe(conta -> addToList(bodyList, conta));
				} catch (ClassNotFoundException | IOException e) {
					// TODO Auto-generated catch block
				}
			}
		});
	}
	
	public static void main(String[] args) {
		try {
			AdminWindow window = new AdminWindow();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
