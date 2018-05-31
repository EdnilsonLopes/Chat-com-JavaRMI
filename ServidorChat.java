import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ServidorChat {

    public static void main(String[] args) {
        try {
            ServicoChat chat = new ServicoChatImpl();
            Naming.rebind("ServidorChat", chat);
            System.out.print("Servidor de Chat Iniciado.");
        } catch (Exception e) {
            System.out.print("Erro: " + e);
            e.printStackTrace();
        }
    }
}
