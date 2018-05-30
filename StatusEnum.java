public enum StatusEnum{
    ONLINE("O", "Online"),
    OCUPADO("C", "Ocupado"),
    OFFLINE("F", "Offline");

    private String codigo;

    private String descricao;

    StatusEnum (String codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }   

    /**
     * @return {@link codigo}
     */
    public String getCodigo() {
        return codigo;
    }

    /**
     * @return {@link descricao}
     */
    public String getDescricao() {
        return descricao;
    }

}