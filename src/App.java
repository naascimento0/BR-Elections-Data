import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.List;

import leitura.ProcessaCandidatos;
import leitura.ProcessaVotos;

import eleicao.candidato.Candidato;
import eleicao.agremiacao.Partido;
import eleicao.agremiacao.Federacao;
import eleicao.Estatisticas;

public class App {
    public static void main(String[] args) throws Exception {
        if(args.length != 4)
            throw new Exception("Número de argumentos inválido. Use: java App <cargo> <candidatos.csv> <votacao.csv> <data_eleicao>");

        int cargo = 0;
        if(args[0].equals("--federal"))
            cargo = 6;
        else if(args[0].equals("--estadual")) 
            cargo = 7;
        else
            throw new Exception("Argumento inválido. Use --federal ou --estadual");

        LocalDate dataEleicao;
        try {
            dataEleicao = LocalDate.parse(args[3], DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        } catch(DateTimeParseException e) {
            throw new Exception("Data inválida. Use o formato dd/MM/yyyy");
        }

        String candidatosFilePath = args[1];
        String votacaoFilePath = args[2];

        HashMap<String, Partido> partidos = ProcessaCandidatos.processarCandidatos(cargo, candidatosFilePath);
        Set<Federacao> federacoes = criaFederacoes(partidos);
        ProcessaVotos.processarVotos(partidos, cargo, votacaoFilePath);

        // primeiro e segundo relatorio...
        List<Candidato> candidatosEleitos = Estatisticas.getCandidatosEleitosOrdenados(partidos);
        System.out.println("Número de vagas: " + candidatosEleitos.size() + "\n");
        String text = cargo == 6 ? "Deputados federais eleitos:" : "Deputados estaduais eleitos:";
        System.out.println(text);
        Estatisticas.printCandidatos(candidatosEleitos, federacoes, partidos);

        // terceiro relatorio...
        System.out.println("\nCandidatos mais votados (em ordem decrescente de votação e respeitando número de vagas):");
        List<Candidato> candidatosMaisVotados = Estatisticas.getCandidatosMaisVotados(partidos);
        List<Candidato> candidatosMaisVotadosEmVagas = candidatosMaisVotados.subList(0, candidatosEleitos.size());
        Estatisticas.printCandidatos(candidatosMaisVotadosEmVagas, federacoes, partidos);

        // quarto relatorio...
        System.out.println("\nTeriam sido eleitos se a votação fosse majoritária, e não foram eleitos:");
        System.out.println("(com sua posição no ranking de mais votados)");
        Estatisticas.printCandidatosEleitosMajoritaria(candidatosEleitos, candidatosMaisVotados, candidatosMaisVotadosEmVagas, federacoes, partidos);

        // quinto relatorio...
        System.out.println("\nEleitos, que se beneficiaram do sistema proporcional:");
        System.out.println("(com sua posição no ranking de mais votados)");
        Estatisticas.printCandidatosEleitosProporcional(candidatosEleitos, candidatosMaisVotados, candidatosMaisVotadosEmVagas, federacoes, partidos);

        // sexto relatorio...
        System.out.println("\nVotação dos partidos e número de candidatos eleitos:");
        Estatisticas.printPartidosComVotos(new ArrayList<Partido>(partidos.values()));

        // sétimo relatorio...
        System.out.println("\nPrimeiro e último colocados de cada partido:");
        Estatisticas.printPrimeiroUltimoColocados(new ArrayList<Partido>(partidos.values()));

        // oitavo relatorio...
        System.out.println("\nEleitos, por faixa etária (na data da eleição):");
        Estatisticas.printEleitosPorFaixaEtaria(candidatosEleitos, dataEleicao);

        // nono relatorio...
        System.out.println("\nEleitos, por gênero:");
        Estatisticas.printEleitosPorGenero(candidatosEleitos);
        System.out.println();

        // decimo relatorio...
        Estatisticas.printTotalVotos(partidos);
        System.out.println();
    }

    public static Set<Federacao> criaFederacoes(HashMap<String, Partido> partidos) {
        Set<Federacao> federacoes = new HashSet<>();

        for (Partido p : partidos.values()) {
            List<Candidato> candidatos = p.getCandidatos();
            if(candidatos.isEmpty()) continue;
            Candidato c = candidatos.stream().findFirst().get();

            if(c.getNumeroFederacao() != -1) {
                boolean federacaoExiste = false;
                for(Federacao f : federacoes) {
                    if(f.getNumeroFederacao() == c.getNumeroFederacao()) {
                        f.addPartido(p);
                        federacaoExiste = true;
                        break;
                    }
                }
                if(!federacaoExiste) {
                    Federacao f = new Federacao(c.getNumeroFederacao());
                    federacoes.add(f);
                    f.addPartido(p);
                }
            }
        }

        return federacoes;
    }
}