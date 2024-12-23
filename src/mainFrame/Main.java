package mainFrame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import aspect.ExceptionManager;
import valueObject.VUser;

public class Main {
	private PLoginDialog pLoginDialog;
	private PMainFrame pMainFrame;

	public Main() {
		try {
			this.pLoginDialog = new PLoginDialog(new ActionHandler());
		} catch (RemoteException | NotBoundException e) {
			// TODO Auto-generated catch block
			ExceptionManager.getInstance().process(e);
		}
	}
	private void intialize() {
		this.pLoginDialog.initialize();
	}
	
	private void validateUser(Object source) {
		try {
			VUser vUser = this.pLoginDialog.validateUser(source);
			if (vUser != null) {
				this.pMainFrame = new PMainFrame(this.pLoginDialog.getSessionId());
				System.out.println("main: "+this.pLoginDialog.getSessionId());
				this.pMainFrame.initialize(vUser);
			}
			this.pLoginDialog.dispose();
		} catch (RemoteException | NotBoundException e) {
			// TODO Auto-generated catch block
			ExceptionManager.getInstance().process(e);
		}
	}
	
	// LoginDialog "OK" and "Cancel" Button Event Handler
	public class ActionHandler implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			validateUser(event.getSource());
		}
	}
	
	public static void main(String[] args) {
		Main main = new Main();
		main.intialize();		
	}
}
