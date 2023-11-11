package eleicao;

import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;

import eleicao.candidato.Candidato;
import eleicao.candidato.Genero;
import eleicao.agremiacao.Federacao;
import eleicao.agremiacao.Partido;

public class Estatisticas {
    public static List<Candidato> getCandidatosEleitosOrdenados(HashMap<String, Partido> partidos) {
        List<Candidato> candidatosEleitos = new ArrayList<Candidato>();
        
        for(Partido p : partidos.values()) {
            List<Candidato> candidatos = p.getCandidatos();
            for(Candidato c : candidatos) { if(c.isEleito()) candidatosEleitos.add(c); }
        }

        Collections.sort(candidatosEleitos, new Candidato.ComparadorVotos());
        return candidatosEleitos;
    }

    public static void printCandidatos(List<Candidato> candidatos, Set<Federacao> federacoes, HashMap<String, Partido> partidos) {
        int i = 1;
        for(Candidato c : candidatos) {
            System.out.print(i + " - ");
            for(Federacao f : federacoes) {
                if(f.getNumeroFederacao() == c.getNumeroFederacao()) {
                    System.out.print("*");
                    break;
                }
            }

            Partido p = partidos.get(c.getNumeroCandidato().substring(0, 2));

            System.out.print(c);
            System.out.printf(Locale.forLanguageTag("pt-BR"), " (%s, %,d votos)\n", p.getSiglaPartido(), c.getQuantidadeVotos());
            i++;
        }
    }

    public static List<Candidato> getCandidatosMaisVotados(HashMap<String, Partido> partidos) {
        List<Candidato> candidatosMaisVotados = new ArrayList<Candidato>();
        
        for(Partido p : partidos.values()) {
            List<Candidato> candidatos = p.getCandidatos();
            for(Candidato c : candidatos)
                candidatosMaisVotados.add(c);
        }

        Collections.sort(candidatosMaisVotados, new Candidato.ComparadorVotos());
        return candidatosMaisVotados;
    }

    private static List<Candidato> getIntersection(List<Candidato> list1, List<Candidato> list2) {
        // https://stackoverflow.com/questions/2400838/efficient-intersection-of-two-liststring-in-java
        List<Candidato> intersection = list1.stream().filter(list2::contains).collect(Collectors.toList());
        return intersection;
    }

    public static void printCandidatosEleitosMajoritaria(List<Candidato> candidatosEleitos, List<Candidato> candidatosMaisVotados, List<Candidato> candidatosMaisVotadosEmVagas, Set<Federacao> federacoes, HashMap<String, Partido> partidos) {
        List<Candidato> intersection = getIntersection(candidatosEleitos, candidatosMaisVotadosEmVagas);
        // https://stackoverflow.com/questions/18644579/getting-the-difference-between-two-sets
        List<Candidato> candidatosEleitosMajoritaria = candidatosMaisVotadosEmVagas.stream().filter(c -> !intersection.contains(c)).collect(Collectors.toList());

        Collections.sort(candidatosEleitosMajoritaria, new Candidato.ComparadorVotos());

        for(Candidato c : candidatosEleitosMajoritaria) {
            int i = 0;
            for(Candidato c2 : candidatosMaisVotados) {
                i++;
                if(c2.getNumeroCandidato().equals(c.getNumeroCandidato()))
                    break;
            }
            System.out.print(i + " - ");
            for(Federacao f : federacoes) {
                if(f.getNumeroFederacao() == c.getNumeroFederacao()) {
                    System.out.print("*");
                    break;
                }
            }

            Partido p = partidos.get(c.getNumeroCandidato().substring(0, 2));

            System.out.print(c);
            System.out.printf(Locale.forLanguageTag("pt-BR"), " (%s, %,d votos)\n", p.getSiglaPartido(), c.getQuantidadeVotos());
        }
    }

    public static void printCandidatosEleitosProporcional(List<Candidato> candidatosEleitos, List<Candidato> candidatosMaisVotados, List<Candidato> candidatosMaisVotadosEmVagas, Set<Federacao> federacoes, HashMap<String, Partido> partidos) {
        List<Candidato> intersection = getIntersection(candidatosEleitos, candidatosMaisVotadosEmVagas);
        
        List<Candidato> candidatosEleitosProporcional = candidatosEleitos.stream().filter(c -> !intersection.contains(c)).collect(Collectors.toList());

        Collections.sort(candidatosEleitosProporcional, new Candidato.ComparadorVotos());

        for(Candidato c : candidatosEleitosProporcional) {
            int i = 0;
            for(Candidato c2 : candidatosMaisVotados) {
                i++;
                if(c2.getNumeroCandidato().equals(c.getNumeroCandidato()))
                    break;
            }
            System.out.print(i + " - ");
            for(Federacao f : federacoes) {
                if(f.getNumeroFederacao() == c.getNumeroFederacao()) {
                    System.out.print("*");
                    break;
                }
            }

            Partido p = partidos.get(c.getNumeroCandidato().substring(0, 2));

            System.out.print(c);
            System.out.printf(Locale.forLanguageTag("pt-BR"), " (%s, %,d votos)\n", p.getSiglaPartido(), c.getQuantidadeVotos());
        }
    }

    public static void printPartidosComVotos(List<Partido> partidos) {
        Collections.sort(partidos, new Partido.ComparadorVotos());

        int i = 1;
        for(Partido p : partidos) {
            int qtdEleitos = 0;
            int qtdVotosNominais = 0, qtdVotosLegenda = 0;

            System.out.print(i + " - ");
            System.out.print(p.getSiglaPartido() + " - " + p.getNumeroPartido() + ", ");
            qtdVotosLegenda = p.getVotosLegenda();
            qtdVotosNominais = p.getVotosNominais();

            for(Candidato c : p.getCandidatos()) {
                if(c.isEleito()) qtdEleitos++;
            }

            if(qtdVotosLegenda + qtdVotosNominais > 1)
                System.out.printf(Locale.forLanguageTag("pt-BR"), "%,d votos ", (qtdVotosLegenda + qtdVotosNominais));
            else
                System.out.printf(Locale.forLanguageTag("pt-BR"), "%,d voto ", (qtdVotosLegenda + qtdVotosNominais));
            
            if(qtdVotosNominais > 1)
                System.out.printf(Locale.forLanguageTag("pt-BR"), "(%,d nominais e ", qtdVotosNominais);
            else
                System.out.printf(Locale.forLanguageTag("pt-BR"), "(%,d nominal e ", qtdVotosNominais);

            System.out.printf(Locale.forLanguageTag("pt-BR"), "%,d de legenda), ", qtdVotosLegenda);

            if(qtdEleitos != 1 && qtdEleitos != 0)
                System.out.printf(Locale.forLanguageTag("pt-BR"), "%d candidatos eleitos\n", qtdEleitos);
            else
                System.out.printf(Locale.forLanguageTag("pt-BR"), "%d candidato eleito\n", qtdEleitos);

            i++;
        }
    }

    public static void printPrimeiroUltimoColocados(List<Partido> partidos) {
        Collections.sort(partidos, new Partido.ComparadorCandidatoMaisVotado());

        int i = 1;
        for(Partido p : partidos) {
            LinkedList<Candidato> candidatos = new LinkedList<>(p.getCandidatos());
            Collections.sort(candidatos, new Candidato.ComparadorVotos());

            if(candidatos.size() == 0) continue;

            Candidato primeiroColocado = candidatos.getFirst();
            Candidato ultimoColocado;
            
            int j = 0;
            int size = candidatos.size();
            do {
                if(++j == size) {
                    ultimoColocado = primeiroColocado;
                    break;
                }
                ultimoColocado = candidatos.get(size - j);
            } while(!ultimoColocado.isCandidaturaDeferida());

            System.out.print(i + " - ");
            System.out.print(p.getSiglaPartido() + " - " + p.getNumeroPartido() + ", " + primeiroColocado.getNomeNaUrna() + " (" + primeiroColocado.getNumeroCandidato() + ", ");

            if(primeiroColocado.getQuantidadeVotos() > 1)
                System.out.printf(Locale.forLanguageTag("pt-BR"), "%,d votos) / %s (%s, ", primeiroColocado.getQuantidadeVotos(), ultimoColocado.getNomeNaUrna(), ultimoColocado.getNumeroCandidato());
            else
                System.out.printf(Locale.forLanguageTag("pt-BR"), "%,d voto) / %s (%s, ", primeiroColocado.getQuantidadeVotos(), ultimoColocado.getNomeNaUrna(), ultimoColocado.getNumeroCandidato());

            if(ultimoColocado.getQuantidadeVotos() > 1)
                System.out.printf(Locale.forLanguageTag("pt-BR"), "%,d votos)\n", ultimoColocado.getQuantidadeVotos());
            else
                System.out.printf(Locale.forLanguageTag("pt-BR"), "%,d voto)\n", ultimoColocado.getQuantidadeVotos());

            i++;
        }
    }
    
    public static void printEleitosPorFaixaEtaria(List<Candidato> candidatosEleitos, LocalDate date) {
        int menor30 = 0, menor40 = 0, menor50 = 0,  menor60 = 0, maior60 = 0;
        for(Candidato c : candidatosEleitos) {
            int idade = Period.between(c.getDataNascimento(), date).getYears();
            if(idade < 30)
                menor30++;
            else if(idade < 40)
                menor40++;
            else if(idade < 50)
                menor50++;
            else if(idade < 60)
                 menor60++;
            else
                maior60++;
        }

        System.out.printf(Locale.forLanguageTag("pt-BR"), "      Idade < 30: %,d (%.2f%%)\n", menor30, (menor30 * 100.0 / candidatosEleitos.size()));
        System.out.printf(Locale.forLanguageTag("pt-BR"), "30 <= Idade < 40: %,d (%.2f%%)\n", menor40, (menor40 * 100.0 / candidatosEleitos.size()));
        System.out.printf(Locale.forLanguageTag("pt-BR"), "40 <= Idade < 50: %,d (%.2f%%)\n", menor50, (menor50 * 100.0 / candidatosEleitos.size()));
        System.out.printf(Locale.forLanguageTag("pt-BR"), "50 <= Idade < 60: %,d (%.2f%%)\n", menor60, (menor60 * 100.0 / candidatosEleitos.size()));
        System.out.printf(Locale.forLanguageTag("pt-BR"), "60 <= Idade     : %,d (%.2f%%)\n", maior60, (maior60 * 100.0 / candidatosEleitos.size()));
    }

    public static void printEleitosPorGenero(List<Candidato> candidatosEleitos) {
        int qtdMasculino = 0, qtdFeminino = 0;
        for(Candidato c : candidatosEleitos) {
            if(c.getGenero() == Genero.MASCULINO)
                qtdMasculino++;
            else
                qtdFeminino++;
        }

        System.out.printf(Locale.forLanguageTag("pt-BR"), "Feminino:  %,d (%.2f%%)\n", qtdFeminino, (qtdFeminino * 100.0 / candidatosEleitos.size()));
        System.out.printf(Locale.forLanguageTag("pt-BR"), "Masculino: %,d (%.2f%%)\n", qtdMasculino, (qtdMasculino * 100.0 / candidatosEleitos.size()));
    }

    public static void printTotalVotos(HashMap<String, Partido> partidos) {
        int qtdVotosLegenda = 0, qtdVotosNominais = 0;
        for(Partido p : partidos.values()) {
            qtdVotosLegenda += p.getVotosLegenda();
            qtdVotosNominais += p.getVotosNominais();
        }

        System.out.printf(Locale.forLanguageTag("pt-BR"), "Total de votos válidos:    %,d\n", qtdVotosLegenda + qtdVotosNominais);
        System.out.printf(Locale.forLanguageTag("pt-BR"), "Total de votos nominais:   %,d (%.2f%%)\n", qtdVotosNominais, (qtdVotosNominais * 100.0 / (qtdVotosLegenda + qtdVotosNominais)));
        System.out.printf(Locale.forLanguageTag("pt-BR"), "Total de votos de legenda: %,d (%.2f%%)\n", qtdVotosLegenda, (qtdVotosLegenda * 100.0 / (qtdVotosLegenda + qtdVotosNominais)));
    }
}
