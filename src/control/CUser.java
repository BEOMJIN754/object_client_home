package control;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import remoteInterface.IUser;
import valueObject.VUser;

public class CUser extends CControl implements IUser {

	private IUser iUser;
	
	public CUser() throws RemoteException, NotBoundException {
 		super();
		this.iUser = (IUser) super.registry.lookup(IUser.OBJECT_NAME);
	}

	@Override
	public VUser getUser(String userId) throws RemoteException {
		System.out.println("*Client: "+this.getClass().getSimpleName()+"getUser started");
		VUser response =  this.iUser.getUser(userId);
		return response;
	}

}
