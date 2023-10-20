package eleicao;
import java.time.LocalDate;

public abstract class Candidato {
    private String nome;
    private String nomeNaUrna;
    private int codigoCargo; //6=federal 7=estadual
    private int codigoSituacaoCandidato; //processar apenas valores 2 ou 16
    private String numeroCandidato; 
    private Partido partido;
    private int numeroFederacao; //-1 representa nao participacao em federacao
    private LocalDate dataNascimento; // tem que ser dia/mes/ano (nao sei se localdate deixa nesse formato)
    private int codigoSituacaoTurno; // 2 ou 3 = eleito
    private int codigoGenero; // 2 = MASCULINO 4 = FEMININO
    private String nomeTipoDestVotos; // nominal ou legenda 

    public Candidato(String nome, String nomeNaUrna, int codigoCargo, int codigoSituacaoCandidato, String numeroCandidato,
        Partido partido, int numeroFederacao, LocalDate dataNascimento, int codigoSituacaoTurno, int codigoGenero,
        String nomeTipoDestVotos) {
        this.nome = nome;
        this.nomeNaUrna = nomeNaUrna;
        this.codigoCargo = codigoCargo;
        this.codigoSituacaoCandidato = codigoSituacaoCandidato;
        this.numeroCandidato = numeroCandidato;
        this.partido = partido;
        this.numeroFederacao = numeroFederacao;
        this.dataNascimento = dataNascimento;
        this.codigoSituacaoTurno = codigoSituacaoTurno;
        this.codigoGenero = codigoGenero;
        this.nomeTipoDestVotos = nomeTipoDestVotos;
    }

    public Partido getPartido() {
        return partido;
    }

    public int getNumeroFederacao() {
        return numeroFederacao;
    }

    @Override
    public String toString() {
        return "Candidato [codigoCargo=" + codigoCargo + ", codigoGenero=" + codigoGenero + ", codigoSituacaoCandidato="
                + codigoSituacaoCandidato + ", codigoSituacaoTurno=" + codigoSituacaoTurno + ", dataNascimento="
                + dataNascimento + ", nome=" + nome + ", nomeNaUrna=" + nomeNaUrna + ", nomeTipoDestVotos="
                + nomeTipoDestVotos + ", numeroCandidato=" + numeroCandidato + ", numeroFederacao=" + numeroFederacao;
    }
}
