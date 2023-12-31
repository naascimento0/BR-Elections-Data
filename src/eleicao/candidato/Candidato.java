package eleicao.candidato;

import java.time.LocalDate;
import java.util.Comparator;

public class Candidato {
    private String nome;
    private String nomeNaUrna;
    private int codigoSituacaoCandidato; // 2 ou 16 = candidato deferido
    private String numeroCandidato; 
    private int numeroFederacao; // -1 representa nao participacao em federacao
    private LocalDate dataNascimento; // dia/mes/ano 
    private int codigoSituacaoTurno; // 2 ou 3 = eleito
    private Genero genero;
    private String nomeTipoDestVotos; // nominal ou legenda 
    private int quantidadeVotos;

    public Candidato(String nome, String nomeNaUrna, int codigoSituacaoCandidato, String numeroCandidato,
        int numeroFederacao, LocalDate dataNascimento, int codigoSituacaoTurno, Genero genero,
        String nomeTipoDestVotos) {
        this.nome = nome;
        this.nomeNaUrna = nomeNaUrna;
        this.codigoSituacaoCandidato = codigoSituacaoCandidato;
        this.numeroCandidato = numeroCandidato;
        this.numeroFederacao = numeroFederacao;
        this.dataNascimento = dataNascimento;
        this.codigoSituacaoTurno = codigoSituacaoTurno;
        this.genero = genero;
        this.nomeTipoDestVotos = nomeTipoDestVotos;
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

    public String getNomeTipoDestVotos() {
        return nomeTipoDestVotos;
    }

    public boolean isCandidaturaDeferida(){
        return codigoSituacaoCandidato == 2 || codigoSituacaoCandidato == 16;
    }

    public boolean isEleito() {
        return codigoSituacaoTurno == 2 || codigoSituacaoTurno == 3;
    }
    
    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public String getNomeNaUrna() {
        return nomeNaUrna;
    }

    public String getNome() {
        return nome;
    }

    public Genero getGenero() {
        return genero;
    }

    public int getCodigoSituacaoCandidato() {
        return codigoSituacaoCandidato;
    }
    
    public void addQuantidadeVotos(int quantidadeVotos) {
        this.quantidadeVotos += quantidadeVotos;
    }

    public String getNumeroPartido() {
        return numeroCandidato.substring(0, 2);                
    }

    @Override
    public String toString() {
        return "" + nomeNaUrna;
    }

    public static class ComparadorVotos implements Comparator<Candidato> {
        @Override
        public int compare(Candidato c1, Candidato c2){
            int diff = c2.getQuantidadeVotos() - c1.getQuantidadeVotos();
            if(diff == 0) 
                return c1.getDataNascimento().compareTo(c2.getDataNascimento());       
            return diff;
        }
    }
}
