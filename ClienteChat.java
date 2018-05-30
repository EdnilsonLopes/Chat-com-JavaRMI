import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

public class ClienteChat extends UnicastRemoteObject implements ClienteChatInterface {
    public static final long serialVersionUID = 1L;

    public ClienteChat() throws RemoteException{
        super();
    }

    public static void main(String[] args) {
        UsuarioVO usuario = new UsuarioVO();
        ServidorChatInterface conServe;
        try {
            conServe = (ServidorChatInterface) Naming.lookup("//localhost/ServidorChat");
            System.out.print("Digite seu nome: ");
            Scanner leia = new Scanner(System.in);
            String nome = leia.nextLine();
            usuario.setNome(nome);
            conServe.conectaCliente(usuario);
            System.out.println("Está Conectado"+ usuario.getNome());
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void recebeMensagem(String msg) throws RemoteException {
        System.out.println(msg);
    }
}