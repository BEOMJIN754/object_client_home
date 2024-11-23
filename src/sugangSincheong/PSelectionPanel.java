package sugangSincheong;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Vector;

import javax.swing.BoxLayout;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionListener;

import valueObject.VGangjwa;

public class PSelectionPanel extends PGangjwaContainer {
	private static final long serialVersionUID = 1L;

	private PHakgwaSelectionPanel pHakgwaSelectionPanel;
	private PGangjwaSelectionPanel pGangjwaSelectionPanel;
	
	public PSelectionPanel(ListSelectionListener listSelectionHandler) {
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		this.pHakgwaSelectionPanel = new PHakgwaSelectionPanel(listSelectionHandler);
		this.add(this.pHakgwaSelectionPanel);
		
		JScrollPane scrollPane = new JScrollPane();
		this.pGangjwaSelectionPanel = new PGangjwaSelectionPanel(listSelectionHandler);
		scrollPane.setViewportView(this.pGangjwaSelectionPanel);
		this.add(scrollPane);
	}
	
	public void initialize(PResultPanel pMiridamgiPanel, PResultPanel pSincheongPanel) throws RemoteException, NotBoundException {
		this.pHakgwaSelectionPanel.initialize();
		String hakwaFileName = this.pHakgwaSelectionPanel.getFileName();
		this.pGangjwaSelectionPanel.intialize(hakwaFileName, pMiridamgiPanel, pSincheongPanel);
	}
	
	public void update(Object source) throws RemoteException, NotBoundException {
		String hakgwaFileName = this.pHakgwaSelectionPanel.update(source);
		this.pGangjwaSelectionPanel.update(hakgwaFileName);
	}

	@Override
	public void addGangjwas(Vector<VGangjwa> vSelectedGangjwas) throws RemoteException, NotBoundException {
		this.pGangjwaSelectionPanel.update();		
	}
	
	@Override
	public Vector<VGangjwa> removeSelectedGangjwas() {
		return this.pGangjwaSelectionPanel.removeSelectedGangjwas();
	}

}
