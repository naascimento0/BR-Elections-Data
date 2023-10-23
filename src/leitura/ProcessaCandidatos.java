package leitura;

import java.io.FileInputStream;
// import java.io.FileOutputStream;
// import java.io.PrintStream;
import java.util.Scanner;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

import eleicao.CandidatoEstadual;
import eleicao.CandidatoFederal;
import eleicao.Partido;
import eleicao.Genero;
//import eleicao.Situacao;
import java.time.LocalDate;

public class ProcessaCandidatos {
    public static HashMap<String, Partido> processarCandidatos() throws Exception {
        String nome = "";
        String nomeNaUrna = "";
        String codigoCargo = ""; //6=federal 7=estadual
        String codigoSituacaoCandidato = ""; //processar apenas valores 2 ou 16
        String numeroCandidato = ""; 
        String numeroPartido = "";
        String siglaPartido = "";
        String numeroFederacao = ""; //-1 representa nao participacao em federacao
        String dataNascimento = ""; // tem que ser dia/mes/ano (nao sei se localdate deixa nesse formato)
        String codigoSituacaoTurno = ""; // 2 ou 3 = eleito
        String codigoGenero = ""; // 2 = MASCULINO 4 = FEMININO
        String numeroTipoDestVotos = ""; // nominal ou legenda  
        
        HashMap<String, Partido> partidos = new HashMap<>();

        try(FileInputStream entrada = new FileInputStream("arquivos/consulta_cand_2022_SC.csv"); //depois colocar para ler o argv
        Scanner s = new Scanner(entrada, "ISO-8859-1")) {
            s.nextLine();
            while(s.hasNextLine()) {
                String line = s.nextLine();
                try(Scanner lineScanner = new Scanner(line)){
                    lineScanner.useDelimiter(";");
                    int i = 0;
                    while(lineScanner.hasNext()) {
                        switch(i) {
                            case 13:
                                codigoCargo = lineScanner.next().replace("\"", "");
                                break;
                            case 16:
                                numeroCandidato = lineScanner.next().replace("\"", "");
                                break;
                            case 17:
                                nome = lineScanner.next().replace("\"", "");
                                break;
                            case 18:
                                nomeNaUrna = lineScanner.next().replace("\"", "");
                                break;
                            case 27:
                                numeroPartido = lineScanner.next().replace("\"", "");
                                break;
                            case 28:
                                siglaPartido = lineScanner.next().replace("\"", "");
                                break;
                            case 30:
                                numeroFederacao = lineScanner.next().replace("\"", "");
                                break;
                            case 42:
                                dataNascimento = lineScanner.next().replace("\"", "").replace("/", ",");
                                break;
                            case 45:
                                codigoGenero = lineScanner.next().replace("\"", "");
                                break;
                            case 56:
                                codigoSituacaoTurno = lineScanner.next().replace("\"", "");
                                break;
                            case 67:
                                numeroTipoDestVotos = lineScanner.next().replace("\"", "");
                                break;
                            case 68:
                                codigoSituacaoCandidato = lineScanner.next().replace("\"", "");
                                break;
                            default:
                                lineScanner.next();
                                break;
                        }
                        i++;
                    }

                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd,MM,yyyy");
                    
                    Partido partido = partidos.get(numeroPartido);
                    if(partido == null) {
                        partido = new Partido(siglaPartido, numeroPartido);
                        partidos.put(numeroPartido, partido);
                    }
        
                    if(codigoCargo.equals("6")){
                        CandidatoFederal c = new CandidatoFederal(nome, nomeNaUrna, Integer.parseInt(codigoSituacaoCandidato), numeroCandidato,
                        partido, Integer.parseInt(numeroFederacao), LocalDate.parse(dataNascimento, formatter), Integer.parseInt(codigoSituacaoTurno), Genero.getGenero(codigoGenero), numeroTipoDestVotos);
                        partido.addCandidatoFederal(c);
                    }
                    else if(codigoCargo.equals("7")){
                        CandidatoEstadual c = new CandidatoEstadual(nome, nomeNaUrna, Integer.parseInt(codigoSituacaoCandidato), numeroCandidato,
                        partido, Integer.parseInt(numeroFederacao), LocalDate.parse(dataNascimento, formatter), Integer.parseInt(codigoSituacaoTurno), Genero.getGenero(codigoGenero), numeroTipoDestVotos); 
                        partido.addCandidatoEstadual(c);
                    }
                }
            }

        } catch(Exception e) {
            e.printStackTrace();
        }

        /*try {
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

            for (Partido p : partidos.values()) {
                System.out.println(p);
            }

            // Lembre-se de fechar o arquivo quando terminar.
            arquivoSaida.close();

        } catch (Exception e) {
            e.printStackTrace();
        }*/

        return partidos;
    }
}
