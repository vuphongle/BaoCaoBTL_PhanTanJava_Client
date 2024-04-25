package app;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface AppKaraoke_Remote extends Remote {
	    void runApp() throws RemoteException; 
}

