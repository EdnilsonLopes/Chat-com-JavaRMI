import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.io.File;

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
            while (cliente.nomeEstaEmUso(nome, conServe)) {
                System.out.print("Usuario em uso! Tente outro");
                System.out.print("Digite seu nome: ");
                leia = new Scanner(System.in);
                nome = leia.nextLine();
            }
            usuario.setNome(nome);
            conServe.conectaCliente(usuario);
            System.out.println("Esta conectado ao chat: " + usuario.getNome());
            cliente.setMeuUsuario(usuario);
            System.out.println("Digite \"sair\" a qualquer momento para sair.");
            while (!mensagem.equals("sair")) {
                cliente.setUsuariosOnline(conServe.getNomesUsuariosConectados());
                cliente.mostraUsuariosOnline();
                cliente.recebeMensagem(conServe);
                System.out.print("Digite \"todos\" ou nome do destinatario: ");
                String dest = leia.nextLine();
                // System.out.println("Para enviar arquivo digite: "arquivo" ");
                // if(dest.equals("arquivo")){
                // cliente.requestDownload(conServe);
                // }
                if (dest.equals("sair")) {
                    conServe.desconectaCliente(usuario);
                    System.exit(1);
                }
                if (cliente.isUsuarioOnline(dest)) {
                    System.out.print("Digite sua mensagem: ");
                    mensagem = leia.nextLine();
                    cliente.enviarMensagem(conServe, dest, mensagem);
                } else {
                    System.out.println("O usuario : " + dest + " não esta online");
                }
            }
            System.out.println("Você se desconectou");
            conServe.desconectaCliente(usuario);

        } catch (Exception e) {
            System.out.println("O Servidor não está online... O programa Será Fechado...");
            e.printStackTrace();
        }
    }

    public boolean nomeEstaEmUso(String nome, ServicoChat serv) throws RemoteException {
        return serv.getNomesUsuariosConectados().contains(nome);
    }

    /**
     * Método que mostra as mensagens pro usuario
     */
    public void recebeMensagem(ServicoChat serv) throws RemoteException {
        List<String> msg = serv.getMensagensEnvidadasParaUsuario(getMeuUsuario());
        if (msg.size() > 0) {
            System.out.print("\n----Mensagens----- \n");
            for (String m : msg) {
                System.out.println(m);
            }
        }
        // System.out.println(msg);
    }

    /**
     * Método que verifica se o usuário está online
     * 
     * @param nomeUsuario Nome do usuario que será verificado.
     * @return true para online false para offline
     */
    public boolean isUsuarioOnline(String nomeUsuario) throws RemoteException {
        if (getUsuariosOnline().contains(nomeUsuario) || nomeUsuario.equals("todos")) {
            return true;
        }
        return false;
    }

    public void enviarMensagem(ServicoChat servico, String nomeDestinatario, String msg) throws RemoteException {
        if (nomeDestinatario.equalsIgnoreCase("todos")) {
            servico.enviaMensagemTodos(getMeuUsuario(), msg);
        } else {
            servico.enviaMensagemPrivada(getMeuUsuario(), nomeDestinatario, msg);
        }
    }

    public void mostraUsuariosOnline() throws RemoteException {
        Iterator it = getUsuariosOnline().iterator();
        System.out.print("Usuarios Online:\n");
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

    /*
     * public void requestDownload(IFServidor stub, String arquivo) throws
     * RemoteException, FileNotFoundException, IOException { File diretorio = new
     * File(dir); diretorio.mkdir(); if (stub.getDownload(arquivo) == null) {
     * System.out.println("Arquivo inexistente."); } else { File arquivoRecebido =
     * stub.getDownload(arquivo);
     * System.out.println(arquivoRecebido.getAbsolutePath()); } }
     */

}