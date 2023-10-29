package leitura;

import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Scanner;

import eleicao.agremiacao.Partido;
import eleicao.candidato.Candidato;

public class ProcessaVotos {
    public static void processarVotos(HashMap<String, Partido> partidos, int cargo) throws Exception {
        String codigoCargo = "";
        String numeroCandidato = "";
        String qtdVotos = "";

        try(FileInputStream entrada = new FileInputStream("arquivos/votacao_secao_2022_ES.csv");
        Scanner s = new Scanner(entrada, "ISO-8859-1")) {
            s.nextLine();
            while(s.hasNextLine()) {
                String line = s.nextLine();
                try(Scanner lineScanner = new Scanner(line)){
                    lineScanner.useDelimiter(";");
                    int i = 0;
                    while(lineScanner.hasNext()) {
                        switch(i++) {
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
                    }
                    
                    int numeroCandidatoInt = Integer.parseInt(numeroCandidato);
                    if(numeroCandidatoInt == 95 || numeroCandidatoInt == 96 || numeroCandidatoInt == 97 || numeroCandidatoInt == 98)
                        continue;

                    int votos = Integer.parseInt(qtdVotos);

                    if(Integer.parseInt(codigoCargo) == cargo){
                        if(partidos.containsKey(numeroCandidato))
                            partidos.get(numeroCandidato).addVotosLegenda(votos);
                        else{
                            String numeroPartido = numeroCandidato.substring(0, 2);                
                            if(partidos.containsKey(numeroPartido)){
                                Partido p = partidos.get(numeroPartido);
                                Candidato c = p.getCandidato(numeroCandidato);
                                if(c != null){
                                    if(c.isCandidaturaDeferida()){
                                        c.addQuantidadeVotos(votos);
                                        p.addVotosNominais(votos);
                                    }else if(c.getNomeTipoDestVotos().equals("Válido (legenda)"))
                                        p.addVotosLegenda(votos);                                  
                                }
                            }
                        }
                    }

                    /*if(Integer.parseInt(codigoCargo) == cargo) {
                        Partido p = partidos.get(numeroCandidato.substring(0, 2));
                        Candidato c = p.getCandidato(numeroCandidato);
                        int votos = Integer.parseInt(qtdVotos);

                        if(c == null){
                            p.addVotosLegenda(votos);
                        }
                        else{                                     
                             if(c.isCandidaturaDeferida()){
                                c.addQuantidadeVotos(votos);
                                p.addVotosNominais(votos);
                            }else if(c.getNomeTipoDestVotos().equals("Válido (legenda)"))
                                p.addVotosLegenda(votos);                                 
                        }
                    }*/
                
                } catch(Exception e){
                    e.printStackTrace();
                }
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
