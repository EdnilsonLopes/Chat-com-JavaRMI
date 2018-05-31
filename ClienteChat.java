import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

public class ClienteChat {

    private UsuarioVO meuUsuario;

    private Set<String> usuariosOnline;

    public static void main(String[] args) {
        ClienteChat cliente = new ClienteChat();
        UsuarioVO usuario = new UsuarioVO();
        ServicoChat conServe;
        String mensagem = "";
        try {
            conServe = (ServicoChat) Naming.lookup("//localhost/ServidorChat");
            
            System.out.print("Digite seu nome: ");
            Scanner leia = new Scanner(System.in);
            String nome = leia.nextLine();
            usuario.setNome(nome);
            conServe.conectaCliente(usuario);
            System.out.println("Está Conectado "+ usuario.getNome());
            cliente.setMeuUsuario(usuario);
            System.out.println("Digite \"sair\" a qualquer momento para sair.");
            while (!mensagem.equals("sair")) {
                cliente.setUsuariosOnline(conServe.getNomesUsuariosConectados());
                cliente.mostraUsuariosOnline();
                cliente.recebeMensagem(conServe);
                System.out.print("Digite \"todos\" ou nome do destinatário: ");
                String dest = leia.nextLine();
                if(dest.equals("sair")){
                    conServe.desconectaCliente(usuario);
                    System.exit(1);
                }
                if(cliente.isUsuarioOnline(dest)){
                    System.out.print("Digite sua mensagem: ");
                    mensagem = leia.nextLine();
                    cliente.enviarMensagem(conServe, dest, mensagem);
                }else{
                    System.out.println("O usuário : "+dest+" não está online");
                }
            }
            System.out.println("Você se desconectou");
            conServe.desconectaCliente(usuario);

        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Método que mostra as mensagens pro usuario
     */
    public void recebeMensagem(ServicoChat serv) throws RemoteException {
        String msg = serv.getMensagensEnvidadasParaUsuario(getMeuUsuario());
        if (!msg.equals("")){
            System.out.print("\n----Novas Mensagens----- \n");
            System.out.println(msg);
        }
        //System.out.println(msg);
    }

    /**
     * Método que verifica se o usuário está online
     * @param nomeUsuario Nome do usuario que será verificado.
     * @return true para online false para offline
     */
    public boolean isUsuarioOnline(String nomeUsuario) throws RemoteException {
        if (getUsuariosOnline().contains(nomeUsuario) || nomeUsuario.equals("todos")) {
            return true;
        }
        return false;
    }

    public void enviarMensagem( ServicoChat servico, String nomeDestinatario, String msg) throws RemoteException {
        if(nomeDestinatario.equalsIgnoreCase("todos")){
            servico.enviaMensagemTodos(getMeuUsuario(), msg);
        }else{
            servico.enviaMensagemPrivada(getMeuUsuario(), nomeDestinatario, msg);
        }
    }

    public void mostraUsuariosOnline() throws RemoteException {
        Iterator it = getUsuariosOnline().iterator();
        System.out.print("Usuários Online:\n");
        while (it.hasNext()) {
            String nome = (String) it.next();
            if (!nome.equals(getMeuUsuario().getNome())) {
                System.out.println(nome);
            }
        }

    }

    /**
     * @return o usuario
     */
    public UsuarioVO getMeuUsuario() {
        return meuUsuario;
    }

    /**
     * @param usuario Atualiza o valor de usuario
     */
    public void setMeuUsuario(UsuarioVO meuUsuario) {
        this.meuUsuario = meuUsuario;
    }

    /**
     * @return os usuariosOnline
     */
    public Set<String> getUsuariosOnline() {
        return usuariosOnline;
    }

    /**
     * @param usuariosOnline Atualiza o valor de usuariosOnline
     */
    public void setUsuariosOnline(Set<String> usuariosOnline) {
        this.usuariosOnline = usuariosOnline;
    }

}