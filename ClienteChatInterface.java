import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ClienteChatInterface extends Remote {
    public void recebeMensagem(String msg) throws RemoteException;
}