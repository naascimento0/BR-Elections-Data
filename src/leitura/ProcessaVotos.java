package leitura;

import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Scanner;

import eleicao.Partido;
import eleicao.Candidato;

public class ProcessaVotos {
    public static void processarVotos(HashMap<String, Partido> partidos) throws Exception {
        String codigoCargo = "";
        String numeroCandidato = "";
        String qtdVotos = "";

        try(FileInputStream entrada = new FileInputStream("arquivos/votacao_secao_2022_SC.csv");
        Scanner s = new Scanner(entrada, "ISO-8859-1")) {
            s.nextLine();
            while(s.hasNextLine()) {
                String line = s.nextLine();
                try(Scanner lineScanner = new Scanner(line)){
                    lineScanner.useDelimiter(";");
                    int i = 0;
                    while(lineScanner.hasNext()) {
                        switch(i) {
                            case 17:
                                codigoCargo = lineScanner.next().replace("\"", "");
                                break;
                            case 19:
                                numeroCandidato = lineScanner.next().replace("\"", "");
                                break;
                            case 21:
                                qtdVotos = lineScanner.next().replace("\"", "");
                                break;
                            default:
                                lineScanner.next();
                                break;
                        }
                        i++;
                    }
                    int numeroCandidatoInt = Integer.parseInt(numeroCandidato);
                    if(numeroCandidatoInt == 95 || numeroCandidatoInt == 96 || numeroCandidatoInt == 97 || numeroCandidatoInt == 98){
                        continue;
                    }
                    
                    Partido p = partidos.get(numeroCandidato.substring(0, 2));
                    // System.out.println("SIGLA: " + p.getSiglaPartido());
                    // System.out.println("CODIGO CARGO: " + codigoCargo);
                    // System.out.println("NUMERO CANDIDATO: " + numeroCandidato);

                    Candidato c = null;

                    if(codigoCargo.equals("6"))
                        c = p.getCandidatoFederal(numeroCandidato);
                    else if(codigoCargo.equals("7")) {
                        c = p.getCandidatoEstadual(numeroCandidato);
                        // System.out.println(c);
                    }

                    if(c == null) continue;

                    // System.out.println();

                    if(c.getNomeTipoDestVotos().equals("Válido (legenda)"))
                        p.addVotosLegenda(Integer.parseInt(qtdVotos));
                    else
                        c.addQuantidadeVotos(Integer.parseInt(qtdVotos));
                }
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
