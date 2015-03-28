import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ReceiveMessageInterface extends Remote {
    public String clientComm(String comm) throws RemoteException;
}