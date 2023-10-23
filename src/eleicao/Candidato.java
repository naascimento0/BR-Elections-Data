package eleicao;
import java.time.LocalDate;
import java.util.Comparator;

public abstract class Candidato {
    private String nome;
    private String nomeNaUrna;
    private int codigoSituacaoCandidato;
    //private Situacao situacao; //processar apenas valores 2 ou 16
    private String numeroCandidato; 
    private Partido partido;
    private int numeroFederacao; //-1 representa nao participacao em federacao
    private LocalDate dataNascimento; // tem que ser dia/mes/ano (nao sei se localdate deixa nesse formato)
    private int codigoSituacaoTurno; // 2 ou 3 = eleito
    private Genero genero;
    // int codigoGenero; // 2 = MASCULINO 4 = FEMININO
    private String nomeTipoDestVotos; // nominal ou legenda 
    private int quantidadeVotos;

    public Candidato(String nome, String nomeNaUrna, int codigoSituacaoCandidato, String numeroCandidato,
        Partido partido, int numeroFederacao, LocalDate dataNascimento, int codigoSituacaoTurno, Genero genero,
        String nomeTipoDestVotos) {
        this.nome = nome;
        this.nomeNaUrna = nomeNaUrna;
        //this.situacao = situacao;
        this.codigoSituacaoCandidato = codigoSituacaoCandidato;
        this.numeroCandidato = numeroCandidato;
        this.partido = partido;
        this.numeroFederacao = numeroFederacao;
        this.dataNascimento = dataNascimento;
        this.codigoSituacaoTurno = codigoSituacaoTurno;
        this.genero = genero;
        //this.codigoGenero = codigoGenero;
        this.nomeTipoDestVotos = nomeTipoDestVotos;
    }

    public Partido getPartido() {
        return partido;
    }

    public int getNumeroFederacao() {
        return numeroFederacao;
    }

    public String getNumeroCandidato() {
        return numeroCandidato;
    }

    public int getQuantidadeVotos() {
        return quantidadeVotos;
    }

    public void addQuantidadeVotos(int quantidadeVotos) {
        this.quantidadeVotos += quantidadeVotos;
    }

    public String getNomeTipoDestVotos() {
        return nomeTipoDestVotos;
    }

    public boolean isEleito() {
        return codigoSituacaoTurno == 2 || codigoSituacaoTurno == 3;
    }

    @Override
    public String toString() {
        return "" + nome + " (" + partido.getSiglaPartido() + ", ";
    }

    public static class ComparadorVotos implements Comparator<Candidato> {
        @Override
        public int compare(Candidato c1, Candidato c2){
            return c2.getQuantidadeVotos() - c1.getQuantidadeVotos();
        }
    }
}
