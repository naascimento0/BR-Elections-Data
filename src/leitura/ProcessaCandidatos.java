package leitura;

import java.io.FileInputStream;

import java.util.Scanner;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

import eleicao.candidato.Candidato;
import eleicao.candidato.Genero;

import eleicao.agremiacao.Partido;


//import eleicao.Situacao;
import java.time.LocalDate;

public class ProcessaCandidatos {
    public static HashMap<String, Partido> processarCandidatos(int cargo) throws Exception {
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
        String nomeTipoDestVotos = ""; // nominal ou legenda  
        
        HashMap<String, Partido> partidos = new HashMap<>();

        try(FileInputStream entrada = new FileInputStream("arquivos/consulta_cand_2022_ES.csv"); //depois colocar para ler o diretorio do arquivo no argv
        Scanner s = new Scanner(entrada, "ISO-8859-1")) {
            s.nextLine();
            while(s.hasNextLine()) {
                String line = s.nextLine();
                try(Scanner lineScanner = new Scanner(line)) {
                    lineScanner.useDelimiter(";");
                    int i = 0;
                    while(lineScanner.hasNext()) {
                        switch(i++) {
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
                                nomeTipoDestVotos = lineScanner.next().replace("\"", "");
                                break;
                            case 68:
                                codigoSituacaoCandidato = lineScanner.next().replace("\"", "");
                                break;
                            default:
                                lineScanner.next();
                                break;
                        }
                    }

                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd,MM,yyyy");
                    
                    Partido partido = partidos.get(numeroPartido);
                    if(partido == null) {
                        partido = new Partido(siglaPartido, numeroPartido);
                        partidos.put(numeroPartido, partido);
                    }
                    //&& (nomeTipoDestVotos.equals("Válido") || nomeTipoDestVotos.equals("Válido (legenda)"))
                    if(Integer.parseInt(codigoCargo) == cargo && (nomeTipoDestVotos.equals("Válido") || nomeTipoDestVotos.equals("Válido (legenda)"))) {
                        Candidato c = new Candidato(nome, nomeNaUrna, Integer.parseInt(codigoSituacaoCandidato), numeroCandidato,
                            Integer.parseInt(numeroFederacao), LocalDate.parse(dataNascimento, formatter), Integer.parseInt(codigoSituacaoTurno), Genero.getGenero(codigoGenero), nomeTipoDestVotos);
                        partido.addCandidato(c);
                    }

                } catch(Exception e){
                    e.printStackTrace();
                }
            }

        } catch(Exception e) {
            e.printStackTrace();
        }

        return partidos;
    }
}
