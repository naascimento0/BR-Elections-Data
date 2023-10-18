import java.io.FileInputStream;
import java.util.Scanner;
import java.time.format.DateTimeFormatter;

import eleicao.Candidato;
import eleicao.CandidatoFederal;
import eleicao.Partido;
import eleicao.Federacao;
import java.time.LocalDate;

public class App {
    public static void main(String[] args) throws Exception {
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

        System.out.println("LENDO CSV:\n"); 

        try(FileInputStream entrada = new FileInputStream("arquivos/consulta_cand_2022_SC.csv");
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

                    //if(siglaPartido ja ta na lista) nao cria;
                    Partido partido = new Partido(siglaPartido, numeroPartido);

                    CandidatoFederal c = new CandidatoFederal(nome, nomeNaUrna, Integer.parseInt(codigoCargo), Integer.parseInt(codigoSituacaoCandidato), numeroCandidato,
                    partido, Integer.parseInt(numeroFederacao), LocalDate.parse(dataNascimento, formatter), Integer.parseInt(codigoSituacaoTurno), Integer.parseInt(codigoGenero), numeroTipoDestVotos); 

                    System.out.println(c);

                    //CandidatoFederal c = new CandidatoFederal(nome, nomeNaUrna, Integer.parseInt(codigoCargo), Integer.parseInt(codigoSituacaoCandidato), numeroCandidato,
                    //partido, Integer.parseInt(numeroFederacao), LocalDate.parse(dataNascimento, formatter), Integer.parseInt(codigoSituacaoTurno), Integer.parseInt(codigoGenero), numeroTipoDestVotos); 

                    // partido.adicionarCandidato(c);
                
                }
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}