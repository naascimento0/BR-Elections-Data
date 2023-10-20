import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import leitura.ProcessaDados;

import eleicao.Candidato;
import eleicao.Partido;
import eleicao.Federacao;

public class App {
    public static void main(String[] args) throws Exception {
        HashMap<String, Partido> partidos = ProcessaDados.processarDados();
        
        Set<Federacao> federacoes = new HashSet<>();
        
        for(Partido p : partidos.values()) {
            Set<Candidato> candidatos = p.getCandidatos();
            Candidato c = candidatos.stream().findFirst().get();

            if(c.getNumeroFederacao() != -1) {
                Boolean federacaoExiste = false;
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

        try {
            // Especifique o nome do arquivo para onde deseja redirecionar a saída.
            String nomeArquivo = "saida.txt";

            // Crie um objeto FileOutputStream para escrever no arquivo.
            FileOutputStream arquivoSaida = new FileOutputStream(nomeArquivo);

            // Crie um objeto PrintStream que irá escrever no arquivo.
            PrintStream printStream = new PrintStream(arquivoSaida);

            // Redirecione a saída padrão (System.out) para o arquivo.
            System.setOut(printStream);

            // Agora, qualquer coisa que você imprima usando System.out.println será escrita no arquivo.
            System.out.println("Isso será gravado no arquivo.");

            for(Federacao f : federacoes) {
                System.out.println(f);
                System.out.println();
            }

            // Lembre-se de fechar o arquivo quando terminar.
            arquivoSaida.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}