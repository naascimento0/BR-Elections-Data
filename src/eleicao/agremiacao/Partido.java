package eleicao.agremiacao;
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

    public Partido(String siglaPartido, String numeroPartido) {
        this.siglaPartido = siglaPartido;
        this.numeroPartido = numeroPartido;
    }

    public void addCandidato(Candidato c) {
        if(c == null) return;
        candidatos.put(c.getNumeroCandidato(), c);
    }

    public List<Candidato> getCandidatos() {
        return new LinkedList<Candidato>(candidatos.values());
    }

    public String getSiglaPartido() {
        return this.siglaPartido;
    }

    public void addVotosLegenda(int votosLegenda) {
        this.votosLegenda += votosLegenda;
    }

    public int getVotosLegenda() {
        return this.votosLegenda;
    }

    public Candidato getCandidato(String numeroCandidato) {
        return candidatos.get(numeroCandidato);
    }

    public String getNumeroPartido() {
        return numeroPartido;
    }

    /*@Override
    public String toString() {
        String text = "Partido [siglaPartido=" + siglaPartido + ", numeroPartido=" + numeroPartido + ", candidatos= ";
        
        for(Candidato candidato : candidatos){
            text += candidato + " ";
        }

        return text;
    }*/
}
