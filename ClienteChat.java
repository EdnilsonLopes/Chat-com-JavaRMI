import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

public class ClienteChat {

    public static void main(String[] args) {
        UsuarioVO usuario = new UsuarioVO();
        ServicoChat conServe;
        try {
            conServe = (ServicoChat) Naming.lookup("//localhost/ServidorChat");
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

    public void recebeMensagem(String msg) throws RemoteException {
        System.out.println(msg);
    }
}