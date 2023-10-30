package eleicao.agremiacao;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import eleicao.candidato.Candidato;

public class Partido {
    private String siglaPartido;
    private String numeroPartido;
    private Map<String, Candidato> candidatos = new HashMap<String, Candidato>();
    private int votosLegenda;
    private int votosNominais;

    public Partido(String siglaPartido, String numeroPartido) {
        this.siglaPartido = siglaPartido;
        this.numeroPartido = numeroPartido;
    }

    public List<Candidato> getCandidatos() {
        return new LinkedList<Candidato>(candidatos.values());
    }

    public String getSiglaPartido() {
        return this.siglaPartido;
    }

    public int getVotosLegenda() {
        return this.votosLegenda;
    }

    public String getNumeroPartido() {
        return numeroPartido;
    }

    public Candidato getCandidato(String numeroCandidato) {
        return candidatos.get(numeroCandidato);
    }

    public int getVotosNominais() {
        return votosNominais;
    }

    public int getVotosTotais() {
        return votosLegenda + votosNominais;
    }

    public void addCandidato(Candidato c) {
        if(c == null) return;
        candidatos.put(c.getNumeroCandidato(), c);
    }

    public void addVotosLegenda(int votosLegenda) {
        this.votosLegenda += votosLegenda;
    }

    public void addVotosNominais(int qtdVotos) {
        this.votosNominais += qtdVotos;
    }

    public Candidato getCandidatoMaisVotado() {
        Candidato maisVotado = null;
        int i = 0;
        for(Candidato c : candidatos.values()) {
            if(i++ == 0) {
                maisVotado = c;
                if(maisVotado == null) return null;
                continue;
            }
            if(c.getQuantidadeVotos() > maisVotado.getQuantidadeVotos())
                maisVotado = c;
        }

        return maisVotado;
    }

    public Candidato getCandidatoMenosVotado() {
        Candidato menosVotado = candidatos.values().stream().findFirst().get();
        for(Candidato c : candidatos.values()) {
            if(c.getQuantidadeVotos() < menosVotado.getQuantidadeVotos() && c.isCandidaturaDeferida())
                menosVotado = c;
        }
        
        return menosVotado;
    }

    public static class ComparadorVotos implements Comparator<Partido> {
        @Override
        public int compare(Partido p1, Partido p2){
            int diff = p2.getVotosTotais() - p1.getVotosTotais();
            if(diff == 0) 
                return p1.getNumeroPartido().compareTo(p2.getNumeroPartido());       
            return diff;
        }
    }

    public static class ComparadorCandidatoMaisVotado implements Comparator<Partido> {
        @Override
        public int compare(Partido p1, Partido p2) {
            Candidato c1 = p1.getCandidatoMaisVotado();
            Candidato c2 = p2.getCandidatoMaisVotado();
            if(c1 == null)
                return 1;
            if(c2 == null)
                return -1;

            int diff = c2.getQuantidadeVotos() - c1.getQuantidadeVotos();
            if(diff == 0) 
                return c1.getNumeroCandidato().compareTo(c2.getNumeroCandidato());       
            return diff;
        }
    }
}
