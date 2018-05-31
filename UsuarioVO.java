import java.io.Serializable;

/**
 * Classe responsável por um usuário
 */
public class UsuarioVO implements Serializable {

    /**
     * Serial
     */
    public static final long serialVersionUID = 1L;

    /**
     * Nome do usário conectado
     */
    private String nome;

    /**
     * Ip do Usuário
     */
    private String ip;

    /**
     * Status do Usuário
     */
    private StatusEnum status;

    /**
     * Construtor
     */
    public UsuarioVO() {

    }

    /**
     * @return {@link ip}
     */
    public String getIp() {
        return ip;
    }

    /**
     * @param ip novo valor de {@link ip}
     */
    public void setIp(String ip) {
        this.ip = ip;
    }

    /**
     * @return {@link nome}
     */
    public String getNome() {
        return nome;
    }

    /**
     * @param nome novo valor de {@link nome}
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * @return {@link status}
     */
    public StatusEnum getStatus() {
        return status;
    }

    /**
     * @param status novo valor de {@link status}
     */
    public void setStatus(StatusEnum status) {
        this.status = status;
    }
}