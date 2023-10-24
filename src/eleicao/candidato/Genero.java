package eleicao.candidato;
public enum Genero {
    MASCULINO("2"),
    FEMININO("4");

    private String genero;

    private Genero(String codigo) {
        this.genero = codigo;
    }

    public String getCodigoGenero() {
        return genero;
    }

    public static Genero getGenero(String codigoGenero) {
        if(codigoGenero.equals(MASCULINO.genero))
            return MASCULINO;
        else
            return FEMININO;
    }
}