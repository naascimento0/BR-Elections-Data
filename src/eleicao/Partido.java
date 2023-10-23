package eleicao;
import java.util.HashSet;
import java.util.Set;

public class Partido {
    private String siglaPartido;
    private String numeroPartido;
    private int votosLegenda;
    private Set<CandidatoFederal> candidatosFederais = new HashSet<>();
    private Set<CandidatoEstadual> candidatosEstaduais = new HashSet<>();

    public Partido(String siglaPartido, String numeroPartido){
        this.siglaPartido = siglaPartido;
        this.numeroPartido = numeroPartido;
    }

    public void addCandidatoFederal(CandidatoFederal candidato) {
        if(candidato == null) return;
        candidatosFederais.add(candidato);
    }

    public void addCandidatoEstadual(CandidatoEstadual candidato) {
        if(candidato == null) return;
        candidatosEstaduais.add(candidato);
    }

    public void removeCandidatoFederal(CandidatoFederal candidato) {
        candidatosFederais.remove(candidato);
    }
    
    public void removeCandidatoEstadual(CandidatoEstadual candidato) {
        candidatosEstaduais.remove(candidato);
    }

    public Set<Candidato> getCandidatosFederais() {
        return new HashSet<Candidato>(candidatosFederais);
    }

    public Set<Candidato> getCandidatosEstaduais() {
        return new HashSet<Candidato>(candidatosEstaduais);
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

    public Candidato getCandidatoFederal(String numeroCandidato) {
        for(CandidatoFederal c : candidatosFederais) {
            if(c.getNumeroCandidato().equals(numeroCandidato)) 
                return c;
        }

        return null;
    }

    public Candidato getCandidatoEstadual(String numeroCandidato) {
        for(CandidatoEstadual c : candidatosEstaduais) {
            if(c.getNumeroCandidato().equals(numeroCandidato)) 
                return c;
        }

        return null;
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
