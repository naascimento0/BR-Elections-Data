// import java.io.FileOutputStream;
// import java.io.PrintStream;
import java.time.LocalDate;
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
        int cargo = 7;
        // if(args[0].equals("--federal"))
        //     cargo = 6;
        // else if(args[0].equals("--estadual")) 
        //     cargo = 7;
        // else
        //     throw new Exception("Argumento inválido. Use --federal ou --estadual");

        HashMap<String, Partido> partidos = ProcessaCandidatos.processarCandidatos(cargo);
        Set<Federacao> federacoes = criaFederacoes(partidos);
        ProcessaVotos.processarVotos(partidos, cargo);

        // primeiro e segundo relatorio
        List<Candidato> candidatosEleitos = Estatisticas.getCandidatosEleitosOrdenados(partidos);
        System.out.println("Número de vagas: " + candidatosEleitos.size() + "\n");
        String text = cargo == 6 ? "Deputados federais eleitos:" : "Deputados estaduais eleitos:";
        System.out.println(text);
        Estatisticas.printCandidatos(candidatosEleitos, federacoes, partidos);

        System.out.println();
        // terceiro relatorio
        System.out.println("Candidatos mais votados (em ordem decrescente de votação e respeitando número de vagas):");
        List<Candidato> candidatosMaisVotados = Estatisticas.getCandidatosMaisVotados(partidos);
        List<Candidato> candidatosMaisVotadosEmVagas = candidatosMaisVotados.subList(0, candidatosEleitos.size());
        Estatisticas.printCandidatos(candidatosMaisVotadosEmVagas, federacoes, partidos);

        System.out.println();
        // quarto relatorio...
        System.out.println("Teriam sido eleitos se a votação fosse majoritária, e não foram eleitos:");
        System.out.println("(com sua posição no ranking de mais votados)");
        Estatisticas.printCandidatosEleitosMajoritaria(candidatosEleitos, candidatosMaisVotados, candidatosMaisVotadosEmVagas, federacoes, partidos);

        System.out.println();
        // quinto relatorio...
        System.out.println("Eleitos, que se beneficiaram do sistema proporcional:");
        System.out.println("(com sua posição no ranking de mais votados)");
        Estatisticas.printCandidatosEleitosProporcional(candidatosEleitos, candidatosMaisVotados, candidatosMaisVotadosEmVagas, federacoes, partidos);

        System.out.println();
        // sexto relatorio...
        System.out.println("Votação dos partidos e número de candidatos eleitos:");
        Estatisticas.printPartidosComVotos(new ArrayList<Partido>(partidos.values()));

        System.out.println();
        // sétimo relatorio...
        System.out.println("Primeiro e último colocados de cada partido:");
        Estatisticas.printPrimeiroUltimoColocados(new ArrayList<Partido>(partidos.values()));

        System.out.println();
        // oitavo relatorio...
        System.out.println("Eleitos, por faixa etária (na data da eleição):");
        LocalDate date = LocalDate.of(2022, 1, 1);
        Estatisticas.printEleitosPorFaixaEtaria(candidatosEleitos, date);

        System.out.println();
        // oitavo relatorio...
        // nono relatorio...
        System.out.println("Eleitos, por gênero:");
        Estatisticas.printEleitosPorGenero(candidatosEleitos);

        System.out.println();
        // oitavo relatorio...
        // decimo relatorio...
        Estatisticas.printTotalVotos(partidos);

        System.out.println();
        // oitavo relatorio...
        // try {
        //     // Especifique o nome do arquivo para onde deseja redirecionar a saída.
        //     String nomeArquivo = "saida.txt";

        //     // Crie um objeto FileOutputStream para escrever no arquivo.
        //     FileOutputStream arquivoSaida = new FileOutputStream(nomeArquivo);

        //     // Crie um objeto PrintStream que irá escrever no arquivo.
        //     PrintStream printStream = new PrintStream(arquivoSaida);

        //     // Redirecione a saída padrão (System.out) para o arquivo.
        //     System.setOut(printStream);

        //     // Agora, qualquer coisa que você imprima usando System.out.println será escrita no arquivo.
        //     System.out.println("Isso será gravado no arquivo.");

        //     for(Federacao f : federacoes) {
        //         System.out.println(f);
        //         System.out.println();
        //     }

        //     // Lembre-se de fechar o arquivo quando terminar.
        //     arquivoSaida.close();

        // } catch (Exception e) {
        //     e.printStackTrace();
        // }
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


/*

package numberformat;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;
import java.util.Scanner;

public class NumberFormatTest {

	public static void main(String[] args) {
		NumberFormat nf = NumberFormat.getInstance(Locale.GERMAN);

		try (Scanner s = new Scanner(System.in)) {
			System.out.print("Entre com um número: ");
			boolean entradaValida = false;
			do {
				try {
					if (s.hasNext()) {
						int i = nf.parse(s.next()).intValue();
						entradaValida = true;
						System.out.println("O número entrado foi: " + i);
					}
				} catch (ParseException e) {
					System.out.print("Formato incorreto. Entre novamente com um número: ");
				}
			} while (!entradaValida);
		}
	}

}
*/