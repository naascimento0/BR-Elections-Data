package eleicao;
import java.util.HashSet;
import java.util.Set;

public class Partido {
    private String siglaPartido;
    private String numeroPartido;
    private Set<Candidato> candidatos = new HashSet<>();

    public Partido(String siglaPartido, String numeroPartido){
        this.siglaPartido = siglaPartido;
        this.numeroPartido = numeroPartido;
    }

    public void addCandidato(Candidato candidato) {
        if(candidato == null) return;
        candidatos.add(candidato);
    }

    public void removeCandidato(Candidato candidato) {
        candidatos.remove(candidato);
    }

    public Set<Candidato> getCandidatos() {
        return new HashSet<Candidato>(candidatos);
    }

    public String getSiglaPartido() {
        return this.siglaPartido;
    }

    @Override
    public String toString() {
        String text = "Partido [siglaPartido=" + siglaPartido + ", numeroPartido=" + numeroPartido + ", candidatos= ";
        
        for(Candidato candidato : candidatos){
            text += candidato + " ";
        }

        return text;
    }
}
