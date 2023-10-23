package eleicao;
import java.time.LocalDate;

public class CandidatoFederal extends Candidato {
    public CandidatoFederal(String nome, String nomeNaUrna, int codigoSituacaoCandidato, String numeroCandidato,
        Partido partido, int numeroFederacao, LocalDate dataNascimento, int codigoSituacaoTurno, Genero genero,
        String numeroTipoDestVotos) {
            super(nome, nomeNaUrna, codigoSituacaoCandidato, numeroCandidato, partido, numeroFederacao, dataNascimento, codigoSituacaoTurno, genero, numeroTipoDestVotos);
    }

}
