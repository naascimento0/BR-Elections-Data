package eleicao;

import java.time.LocalDate;

public class CandidatoEstadual extends Candidato {
    public CandidatoEstadual(String nome, String nomeNaUrna, int codigoCargo, int codigoSituacaoCandidato, String numeroCandidato,
        Partido partido, int numeroFederacao, LocalDate dataNascimento, int codigoSituacaoTurno, int codigoGenero,
        String numeroTipoDestVotos) {
            super(nome, nomeNaUrna, codigoCargo, codigoSituacaoCandidato, numeroCandidato, partido, numeroFederacao, dataNascimento, codigoSituacaoTurno, codigoGenero, numeroTipoDestVotos);
    }   
}
