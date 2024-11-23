package sugangSincheong;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Vector;

import javax.swing.JPanel;

import valueObject.VGangjwa;

public abstract class PGangjwaContainer extends JPanel {
	private static final long serialVersionUID = 1L;
	
	abstract public void addGangjwas(Vector<VGangjwa> vSelectedGangjwas) throws RemoteException, NotBoundException;
	abstract public Vector<VGangjwa> removeSelectedGangjwas();
}
