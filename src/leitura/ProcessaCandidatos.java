package leitura;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

import eleicao.candidato.Candidato;
import eleicao.candidato.Genero;

import eleicao.agremiacao.Partido;

import java.time.LocalDate;

public class ProcessaCandidatos {
    public static HashMap<String, Partido> processarCandidatos(int cargo, String filePath) throws Exception, FileNotFoundException {
        String nome = "";
        String nomeNaUrna = "";
        String codigoCargo = ""; 
        String codigoSituacaoCandidato = ""; 
        String numeroCandidato = ""; 
        String numeroPartido = "";
        String siglaPartido = "";
        String numeroFederacao = ""; 
        String dataNascimento = ""; 
        String codigoSituacaoTurno = ""; 
        String codigoGenero = ""; 
        String nomeTipoDestVotos = ""; 
        
        HashMap<String, Partido> partidos = new HashMap<>();

        try(FileInputStream entrada = new FileInputStream(filePath); 
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
                    
                    if(!partidos.containsKey(numeroPartido)) {
                        Partido partido = new Partido(siglaPartido, numeroPartido);
                        partidos.put(numeroPartido, partido);
                    }
                    
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd,MM,yyyy");
                    if(Integer.parseInt(codigoCargo)  == cargo && (nomeTipoDestVotos.equals("Válido") || nomeTipoDestVotos.equals("Válido (legenda)"))) {
                        Candidato c = new Candidato(nome, nomeNaUrna, Integer.parseInt(codigoSituacaoCandidato), numeroCandidato,
                            Integer.parseInt(numeroFederacao), LocalDate.parse(dataNascimento, formatter), Integer.parseInt(codigoSituacaoTurno), Genero.getGenero(codigoGenero), nomeTipoDestVotos);
                        partidos.get(numeroPartido).addCandidato(c);
                    }

                } catch(Exception e) { 
                    e.printStackTrace();
                }
            }

        } catch(FileNotFoundException e) {
            throw new FileNotFoundException("Arquivo não encontrado");
        }

        return partidos;
    }
}
