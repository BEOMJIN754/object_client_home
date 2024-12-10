package mainFrame;

import java.awt.BorderLayout;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.logging.Level;

import javax.swing.JFrame;

import aspect.ExceptionManager;
import aspect.LmsLoggingManager;
import constants.Constants.EMainFrame;
import sugangSincheong.PSugangSincheongPanel;
import valueObject.VUser;

public class PMainFrame extends JFrame {
	// attributes
	private static final long serialVersionUID = 1L;	
	// components	
	private PMenuBar pMenuBar;
	private PToolBar pToolBar;
	private PSugangSincheongPanel pSugangSincheongPanel;
	// utility
	private WindowListener windowsHandler;
	// value object
	private VUser vUser;
	private String sessionId;
	
	// constructor
	public PMainFrame(String sessionId) {
		this.sessionId = sessionId;
		// attributes
		this.setSize(EMainFrame.width.getInt(), EMainFrame.height.getInt());
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);		
		
		this.setLayout(new BorderLayout());
		
		// utility
		this.windowsHandler = new WinodowsHandler();
		this.addWindowListener(this.windowsHandler);		
		
		// components
		this.pMenuBar = new PMenuBar();
		this.setJMenuBar(this.pMenuBar);		
		
		this.pToolBar = new PToolBar();
		this.add(this.pToolBar, BorderLayout.NORTH);
		
		this.pSugangSincheongPanel = new PSugangSincheongPanel(sessionId);
		System.out.println("MF" + sessionId);
		this.add(this.pSugangSincheongPanel, BorderLayout.CENTER);
	}

	public void initialize(VUser vUser) throws RemoteException, NotBoundException {
		this.vUser = vUser;
		this.setVisible(true);
		
		this.pMenuBar.initialize();
		this.pToolBar.initialize();
		this.pSugangSincheongPanel.initialize(this.vUser);
	}
	private void finish() throws RemoteException, NotBoundException {
		this.pSugangSincheongPanel.finish();
	}
	
	private class WinodowsHandler implements WindowListener {
		@Override
		public void windowOpened(WindowEvent e) {
		}
		
		@Override
		public void windowClosing(WindowEvent e) {			
			try {
				finish();
				LmsLoggingManager.getLogger().log(Level.INFO, "User logout successfully.");
			} catch (RemoteException | NotBoundException exception) {
				ExceptionManager.getInstance().process(exception);
			}
		}
		
		@Override
		public void windowClosed(WindowEvent e) {
		}
		@Override
		public void windowIconified(WindowEvent e) {
		}
		@Override
		public void windowDeiconified(WindowEvent e) {
		}
		@Override
		public void windowActivated(WindowEvent e) {
		}
		@Override
		public void windowDeactivated(WindowEvent e) {
		}		
	}
}
