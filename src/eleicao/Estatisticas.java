package eleicao;

import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import eleicao.candidato.Candidato;
import eleicao.agremiacao.Federacao;
import eleicao.agremiacao.Partido;


public class Estatisticas {
    public static List<Candidato> getCandidatosEleitos(HashMap<String, Partido> partidos) {
        List<Candidato> candidatosEleitos = new ArrayList<Candidato>();
        
        for(Partido p : partidos.values()) {
            List<Candidato> candidatos = p.getCandidatos();

            for(Candidato c : candidatos) { if(c.isEleito()) candidatosEleitos.add(c); }
        }

        Collections.sort(candidatosEleitos, new Candidato.ComparadorVotos());

        return candidatosEleitos;
    }

    public static void printCandidatos(List<Candidato> candidatos, Set<Federacao> federacoes) {
        int i = 1;
        for(Candidato c : candidatos) {
            System.out.print(i + " - ");
            for(Federacao f : federacoes) {
                if(f.getNumeroFederacao() == c.getNumeroFederacao()) {
                    System.out.print("*");
                    break;
                }
            }
            System.out.print(c);
            System.out.printf(Locale.ITALY, "%,d votos)\n", c.getQuantidadeVotos());
            i++;
        }
    }

    public static List<Candidato> getCandidatosMaisVotados(HashMap<String, Partido> partidos, int vagas) {
        List<Candidato> candidatosMaisVotados = new ArrayList<Candidato>();
        
        for(Partido p : partidos.values()) {
            List<Candidato> candidatos = p.getCandidatos();

            for(Candidato c : candidatos)
                candidatosMaisVotados.add(c);
        }

        Collections.sort(candidatosMaisVotados, new Candidato.ComparadorVotos());

        candidatosMaisVotados = candidatosMaisVotados.subList(0, vagas);

        return candidatosMaisVotados;
    }

    private static List<Candidato> getIntersection(List<Candidato> list1, List<Candidato> list2) {
        List<Candidato> intersection = list1.stream().filter(list2::contains).collect(Collectors.toList());

        return intersection;
    }

    public static List<Candidato> getCandidatosEleitosMajoritaria(List<Candidato> candidatosEleitos, List<Candidato> candidatosMaisVotados) {
        List<Candidato> intersection = getIntersection(candidatosEleitos, candidatosMaisVotados);

        List<Candidato> candidatosEleitosMajoritaria = candidatosMaisVotados.stream().filter(c -> !intersection.contains(c)).collect(Collectors.toList());

        Collections.sort(candidatosEleitosMajoritaria, new Candidato.ComparadorVotos());

        return candidatosEleitosMajoritaria;
    }

    public static List<Candidato> getCandidatosEleitosProporcional(List<Candidato> candidatosEleitos, List<Candidato> candidatosMaisVotados) {
        List<Candidato> intersection = getIntersection(candidatosEleitos, candidatosMaisVotados);

        List<Candidato> candidatosEleitosProporcional = candidatosEleitos.stream().filter(c -> !intersection.contains(c)).collect(Collectors.toList());

        Collections.sort(candidatosEleitosProporcional, new Candidato.ComparadorVotos());

        return candidatosEleitosProporcional;
    }

    public static void printPartidosComVotos(HashMap<String, Partido> partidos) {
        int i = 1;
        for(Partido p : partidos.values()) {
            int qtdEleitos = 0;
            int qtdVotosNominais = 0, qtdVotosLegenda = 0;

            System.out.print(i + " - ");
            System.out.print(p.getSiglaPartido() + " - " + p.getNumeroPartido() + ", ");
            qtdVotosLegenda = p.getVotosLegenda();

            for(Candidato c : p.getCandidatos()) {
                if(c.isEleito()) qtdEleitos++;
                qtdVotosNominais += c.getQuantidadeVotos();
            }

            System.out.printf(Locale.ITALY, "%,d votos (%,d nominais e %,d de legenda), %d candidatos eleitos\n", (qtdVotosLegenda + qtdVotosNominais), qtdVotosNominais, qtdVotosLegenda, qtdEleitos);
        }
    }
}
