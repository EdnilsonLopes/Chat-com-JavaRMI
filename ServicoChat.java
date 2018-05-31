import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Set;

public interface ServicoChat extends Remote{
    /**
     * Conecta um cliente ao servidor de chat.
     * @param usuario Usuario (Cliente) que será conectado.
     */
    public void conectaCliente(UsuarioVO usuario) throws RemoteException;

    /**
     * Envia uma mensagem para todos que estão conctados ao Seridor do Chat.
     * @param usuario Usuario (Cliente) que envia a mensagem.
     * @param msg Mensagem enviada.
     */
    public void enviaMensagemTodos(UsuarioVO usuario, String msg) throws RemoteException;

    /**
     * Envia uma mensagem privada para um destinatário.
     * @param usuario Usuário (Cliente) que envia a mensagem.
     * @param receptor Usuário (Cliente) que recebe a mensagem
     * @param msg Mensagem enviada
     */
    public void enviaMensagemPrivada(UsuarioVO usuario, String receptor, String msg) throws RemoteException;

    /**
     * Desconecta um cliente do servidor do chat.
     * @param usuario Cliente que será desconectado
     */
    public void desconectaCliente(UsuarioVO usuario) throws RemoteException;

    /**
     * Pega todos os nomes dos usuários conectados.
     */
    public Set<String> getNomesUsuariosConectados() throws RemoteException;

    /**
     * Pega as mensagens enviadas ao Usuario parassado como parametro.
     */
    public String getMensagensEnvidadasParaUsuario(UsuarioVO usuario) throws RemoteException; 
}