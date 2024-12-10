package sugangSincheong;

import java.awt.BorderLayout;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import javax.swing.JPanel;

import valueObject.VUser;

public class PSugangSincheongPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private PHeaderPanel pHeaderPanel;
	private PContentPanel pContentPanel;
	private PFooterPanel pFooterPanel;
	
	public PSugangSincheongPanel(String sessionId) {
		this.setLayout(new BorderLayout());
		
		this.pHeaderPanel = new PHeaderPanel();
		this.add(this.pHeaderPanel, BorderLayout.NORTH);
		
		this.pContentPanel = new PContentPanel(sessionId);
		System.out.println("content"+sessionId);
		this.add(this.pContentPanel, BorderLayout.CENTER);
		
		this.pFooterPanel = new PFooterPanel();
		this.add(this.pFooterPanel, BorderLayout.SOUTH);
	}

	public void initialize(VUser vUser) throws RemoteException, NotBoundException {
		this.pHeaderPanel.intialize(vUser);
		this.pContentPanel.intialize(vUser);		
		this.pFooterPanel.intialize();		
	}

	public void finish() throws RemoteException, NotBoundException {
		this.pContentPanel.finish();
	}
}
