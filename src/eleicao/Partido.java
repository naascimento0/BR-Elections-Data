package eleicao;
import java.util.LinkedList;

public class Partido {
    private String siglaPartido;
    private String numeroPartido;
    private LinkedList<Candidato> candidatos = new LinkedList<>();

    public Partido(String siglaPartido, String numeroPartido){
        this.siglaPartido = siglaPartido;
        this.numeroPartido = numeroPartido;
    }

    public void adicionarCandidato(Candidato candidato){
        candidatos.add(candidato);
    }

    public void removerCandidato(Candidato candidato){
        candidatos.remove(candidato);
    }
}
