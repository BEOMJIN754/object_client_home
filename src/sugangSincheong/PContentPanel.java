package sugangSincheong;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Vector;
import java.util.logging.Level;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import aspect.ExceptionManager;
import aspect.LmsLoggingManager;
import aspect.SessionManager;
import constants.Constants.EConfigurations;
import valueObject.VGangjwa;
import valueObject.VUser;

public class PContentPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private VUser vUser;
	
	private ListSelectionListener listSelectionHandler;
	private PSelectionPanel pSelectionPanel;
	
	private PResultPanel pMiridamgiPanel;
	private PResultPanel pSincheongPanel;
	
	private ActionHandler actionHandler;
	private PControlPanel pControlPanel1;
	private PControlPanel pControlPanel2;
	
	private String sessionId;
	
	
	public PContentPanel(String sessionId) {
		this.sessionId = sessionId;
		System.out.println("CP:"+sessionId);
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));		
		
		this.listSelectionHandler = new ListSelectionHandler();
		this.pSelectionPanel = new PSelectionPanel(this.listSelectionHandler);
		this.add(this.pSelectionPanel);
		
		this.actionHandler = new ActionHandler();
		this.pControlPanel1 = new PControlPanel(this.actionHandler);
		this.add(this.pControlPanel1);		
		
		this.pMiridamgiPanel = new PResultPanel();
		this.add(pMiridamgiPanel);		
		
		this.pControlPanel2 = new PControlPanel(this.actionHandler);	
		this.add(this.pControlPanel2);
		
		this.pSincheongPanel = new PResultPanel();	
		this.add(pSincheongPanel);		
	}

	public void intialize(VUser vUser) throws RemoteException, NotBoundException {
		this.vUser = vUser;	
		
		this.pMiridamgiPanel.initialize(this.vUser.getUserId()+EConfigurations.miridamgiFilePostfix.getText());
		this.pSincheongPanel.initialize(this.vUser.getUserId()+EConfigurations.singcheongFilePostfix.getText());
		
		this.pSelectionPanel.initialize(this.pMiridamgiPanel, this.pSincheongPanel);
		
		this.pControlPanel1.initialize();
		this.pControlPanel2.initialize();
	}
	
	public void finish() throws RemoteException, NotBoundException {
		this.pMiridamgiPanel.finish(this.vUser.getUserId()+EConfigurations.miridamgiFilePostfix.getText());
		this.pSincheongPanel.finish(this.vUser.getUserId()+EConfigurations.singcheongFilePostfix.getText());
	}
	
	/////////////////////////////////////////////////////////////
	// Selection Listener: Gangjwa Table
	////////////////////////////////////////////////////////////
	public void update(Object source) throws RemoteException, NotBoundException {
		this.pSelectionPanel.update(source);
	}
	
	public class ListSelectionHandler implements ListSelectionListener {
		@Override
		public void valueChanged(ListSelectionEvent event) {
			try {
				update(event.getSource());
			}catch(RemoteException | NotBoundException e) {
				ExceptionManager.getInstance().process(e);
			}
		}		
	}
	
	/////////////////////////////////////////////////////
	//  Action Listener: Move Buttons
	/////////////////////////////////////////////////////
	private void moveGangJwas(PGangjwaContainer source, PGangjwaContainer target) throws RemoteException, NotBoundException {
		Vector<VGangjwa> vSelectedGangjwas = source.removeSelectedGangjwas();
		target.addGangjwas(vSelectedGangjwas);
	}
	
	public class ActionHandler implements ActionListener {
	    @Override
	    public void actionPerformed(ActionEvent event) {
	        try {
	        	
	        	if (!SessionManager.verifySession(sessionId)) {
	                System.out.println("action: "+sessionId);
	        		return;
	            }
	            Object source = event.getSource();
	            if (source.equals(pControlPanel1.getMoveRightButton())) {
	                moveGangJwas(pSelectionPanel, pMiridamgiPanel);
	                log(Level.INFO, "Moved Gangjwas from Selection Panel to Miridamgi Panel.");
	            } else if (source.equals(pControlPanel1.getMoveLeftButton())) {
	                moveGangJwas(pMiridamgiPanel, pSelectionPanel);            
	                log(Level.INFO, "Moved Gangjwas from Miridamgi Panel to Selection Panel.");
	            } else if (source.equals(pControlPanel2.getMoveRightButton())) {
	                moveGangJwas(pMiridamgiPanel, pSincheongPanel);            
	                log(Level.INFO, "Moved Gangjwas from Miridamgi Panel to Sincheong Panel.");
	            } else if (source.equals(pControlPanel2.getMoveLeftButton())) {
	                moveGangJwas(pSincheongPanel, pMiridamgiPanel);            
	                log(Level.INFO, "Moved Gangjwas from Sincheong Panel to Miridamgi Panel.");
	            }
	        } catch (RemoteException | NotBoundException e) {
	        	LmsLoggingManager.getLogger().log(Level.SEVERE, "Error in ActionHandler: " + e.getMessage());
	            ExceptionManager.getInstance().process(e);
	        }
	    }
	}
	
	private void log (Level lever, String message) {
		LmsLoggingManager.getLogger().log(lever, message);
	}

}
