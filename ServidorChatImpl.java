import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ServidorChatImpl extends UnicastRemoteObject implements ServidorChatInterface {

    /**
     * Lista de Clientes conectados.
     */
    private List<UsuarioVO> clientesConectados = new ArrayList<>();

    /**
     * Nomes dos usuários conectados.
     */
    private Set<String> nomesUsuarios = new HashSet<>();

    /**
     * Guarda todas as menságens enviadas pelos usuários com uma chave.
     * A chave é o nome do destinatário caso seja privada ou "todos" para mensagem publica.
     */
    private Map<String, String> mensagens = new HashMap<>();

    public static void main(String[] args) {
        try {
            Naming.rebind("ServidorChat", obj);
            System.out.print("Servidor de Chat Iniciado.");
        } catch (Exception e) {
            System.out.print("Erro: " + e);
            e.printStackTrace();
        }
    }

    @Override
    public void conectaCliente(UsuarioVO usuario) throws RemoteException {
        clientesConectados.add(usuario);
        nomesUsuarios.add(usuario.getNome());
        try {
            Naming.lookup("//localhost/" + usuario.getNome());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (NotBoundException e) {
            e.printStackTrace();
        }
        System.out.println("Usuário " + usuario.getNome() + " conectou-se com o IP: " + usuario.getIp());
    }

    @Override
    public void desconectaCliente(UsuarioVO usuario) throws RemoteException {
        clientesConectados.remove(usuario);
        nomesUsuarios.remove(usuario.getNome());
        System.out.println("Usuário "+usuario.getNome()+ " saiu");
    }

    @Override
    public void enviaMensagemPrivada(UsuarioVO usuario, UsuarioVO receptor, String msg) {
        mensagens.put(receptor.getNome(), "Mensagem\n---------------"+usuario.getNome()+": "+msg);
        System.out.print("Mensagem enviada de "+usuario.getNome()+" para "+ receptor.getNome());
    }

    @Override
    public void enviaMensagemTodos(UsuarioVO usuario, String msg) throws RemoteException {
        mensagens.put("todos", "Mensagem\n---------------"+usuario.getNome()+": "+msg);
        System.out.print("Mensagem enviada de "+usuario.getNome()+" para todos");
    }

}