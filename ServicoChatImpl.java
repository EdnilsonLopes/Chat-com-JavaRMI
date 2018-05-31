import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ServicoChatImpl extends UnicastRemoteObject implements ServicoChat {

    public static final long serialVersionUID = 1L;

    /**
     * Lista de Clientes conectados.
     */
    public List<UsuarioVO> clientesConectados = new ArrayList<>();

    /**
     * Nomes dos usuários conectados.
     */
    public Set<String> nomesUsuarios = new HashSet<>();

    /**
     * Guarda todas as menságens enviadas pelos usuários com uma chave. A chave é 
     * nome do destinatário caso seja privada ou "todos" para mensagem publica.
     */
    public Map<String, String> mensagens = new HashMap<>();

    public ServicoChatImpl() throws RemoteException {
        super();
    }

    @Override
    public void conectaCliente(UsuarioVO usuario) throws RemoteException{
        clientesConectados.add(usuario);
        nomesUsuarios.add(usuario.getNome());
        System.out.println("O Usuário "+ usuario.getNome()+ "  conectou-se");
    }

    @Override
    public void desconectaCliente(UsuarioVO usuario) throws RemoteException {
        clientesConectados.remove(usuario);
        nomesUsuarios.remove(usuario.getNome());
        System.out.println("Usuário "+usuario.getNome()+" saiu");
    }

    @Override
    public void enviaMensagemPrivada(UsuarioVO usuario, UsuarioVO receptor, String msg) throws RemoteException {
        mensagens.put(receptor.getNome(), "Mensagem\n---------------" + usuario.getNome() + ": " + msg);
        System.out.print("Mensagem enviada de " + usuario.getNome() + " para " + receptor.getNome());
    }

    @Override
    public void enviaMensagemTodos(UsuarioVO usuario, String msg) throws RemoteException {
        mensagens.put("todos", "Mensagem\n---------------" + usuario.getNome() + ": " + msg);
        System.out.print("Mensagem enviada de " + usuario.getNome() + " para todos");
    }
    
    @Override
    public Set<String> getNomesUsuariosConectados() throws RemoteException{
        return nomesUsuarios;
    }

    /**
     * @return os clientesConectados
     */
    public List<UsuarioVO> getClientesConectados() {
        return clientesConectados;
    }

    /**
     * @return as mensagens
     */
    public Map<String, String> getMensagens() {
        return mensagens;
    }

    /**
     * @return os nomesUsuarios
     */
    public Set<String> getNomesUsuarios() {
        return nomesUsuarios;
    }

}