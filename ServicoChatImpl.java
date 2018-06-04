import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.io.File;

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
    public Map<String, List<String>> mensagens = new HashMap<>();


    private int contador = 0;

    public ServicoChatImpl() throws RemoteException {
        super();
    }

    @Override
    public void conectaCliente(UsuarioVO usuario) throws RemoteException{
        clientesConectados.add(usuario);
        nomesUsuarios.add(usuario.getNome());
        //contador += nomesUsuarios.size();
        System.out.println("O Usuario "+ usuario.getNome()+ "  conectou-se");
    }

    @Override
    public void desconectaCliente(UsuarioVO usuario) throws RemoteException {
        clientesConectados.remove(usuario);
        nomesUsuarios.remove(usuario.getNome());
        //contador += nomesUsuarios.size();
        System.out.println("Usuario "+usuario.getNome()+" saiu");
    }

    @Override
    public void enviaMensagemPrivada(UsuarioVO usuario, String receptor, String msg) throws RemoteException {
        List<String> listaMsg = new ArrayList<>();
        if(mensagens.containsKey(receptor)){
            listaMsg = mensagens.get(receptor);
        }
        listaMsg.add(usuario.getNome() + ": " + msg);
        mensagens.put(receptor, listaMsg);

        System.out.print("Mensagem enviada de " + usuario.getNome() + " para " + receptor);
    }

    @Override
    public void enviaMensagemTodos(UsuarioVO usuario, String msg) throws RemoteException {
        List<String> listaMsg = new ArrayList<>();
        if(mensagens.containsKey("todos")){
            listaMsg = mensagens.get("todos");
        }
        listaMsg.add(usuario.getNome() + ": " + msg+"\n");
        mensagens.put("todos", listaMsg);
        System.out.print("Mensagem enviada de " + usuario.getNome() + " para todos");
    }
    
    @Override
    public Set<String> getNomesUsuariosConectados() throws RemoteException{
        return nomesUsuarios;
    }

    @Override
    public List<String> getMensagensEnvidadasParaUsuario(UsuarioVO usuario) throws RemoteException {
        List<String> msg = new ArrayList<>();
        if (getMensagens().containsKey(usuario.getNome())){
            msg.addAll(getMensagens().get(usuario.getNome()));
            //Remove a mensagem já lida pelo receptor
            getMensagens().remove(usuario.getNome());
        }
        if (getMensagens().containsKey("todos")){
            msg.add("---Mensagens de Todos---");
            msg.addAll(getMensagens().get("todos"));
        }
        return msg;
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
    public Map<String, List<String>> getMensagens() {
        return mensagens;
    }

    /**
     * @return os nomesUsuarios
     */
    public Set<String> getNomesUsuarios() {
        return nomesUsuarios;
    }
	/*public File getArquivo(String arquivo) throws RemoteException {
		File arquivoEnviar = new File(dir+File.separator+arquivo);
		return arquivoEnviar;
	}*/
}