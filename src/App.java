import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.List;
import java.util.Locale;
import java.util.ArrayList;
import java.util.Collections;

import leitura.ProcessaCandidatos;
import leitura.ProcessaVotos;
import eleicao.Candidato;
import eleicao.CandidatoEstadual;
import eleicao.CandidatoFederal;
import eleicao.Partido;
import eleicao.Candidato.ComparadorVotos;
import eleicao.Federacao;

public class App {
    public static void main(String[] args) throws Exception {
        HashMap<String, Partido> partidos = ProcessaCandidatos.processarCandidatos();
        
        Set<Federacao> federacoes = new HashSet<>();
        
        for(Partido p : partidos.values()) {
            Set<Candidato> candidatos = p.getCandidatosFederais() == null ?  p.getCandidatosEstaduais() : p.getCandidatosFederais();
            // if(candidatos == null)
            //     candidatos = p.getCandidatosEstaduais();
            Candidato c = candidatos.stream().findFirst().get();

            if(c.getNumeroFederacao() != -1) {
                boolean federacaoExiste = false;
                for(Federacao f : federacoes) {
                    if(f.getNumeroFederacao() == c.getNumeroFederacao()){
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

        ProcessaVotos.processarVotos(partidos);

        List<Candidato> candidatosEleitos = new ArrayList<>();
        for(Partido p : partidos.values()) {
            Set<Candidato> candidatoFederais = p.getCandidatosFederais();
            Set<Candidato> candidatosEstaduais = p.getCandidatosEstaduais();

            for(Candidato c : candidatoFederais) { if(c.isEleito()) candidatosEleitos.add(c); }

            for(Candidato c : candidatosEstaduais) { if(c.isEleito()) candidatosEleitos.add(c); }
        }

        System.out.println("Número de vagas: " + candidatosEleitos.size());
        System.out.println();

        Collections.sort(candidatosEleitos, new Candidato.ComparadorVotos());

        int i = 1;
        for(Candidato c : candidatosEleitos) {
            System.out.print(i + " - " + c);
            System.out.printf(Locale.ITALY, "%,d votos)\n", c.getQuantidadeVotos());
            i++;
        }

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
}