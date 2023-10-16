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
            while(s.hasNextLine()) {
                String line = s.nextLine();
                System.out.println("Processando linha " + line);

                try(Scanner lineScanner = new Scanner(line)){
                    lineScanner.useDelimiter(";");
                    int i = 0;
                    while(lineScanner.hasNext()) {
                        switch(i) {
                            case 14:
                                codigoCargo = lineScanner.next();
                                break;
                            case 17:
                                numeroCandidato = lineScanner.next();
                                break;
                            case 18:
                                nome = lineScanner.next();
                                break;
                            case 19:
                                nomeNaUrna = lineScanner.next();
                                break;
                            case 28:
                                numeroPartido = lineScanner.next();
                                break;
                            case 29:
                                siglaPartido = lineScanner.next();
                                break;
                            case 31:
                                numeroFederacao = lineScanner.next();
                                break;
                            case 43:
                                dataNascimento = lineScanner.next();
                                break;
                            case 46:
                                codigoGenero = lineScanner.next();
                                break;
                            case 57:
                                codigoSituacaoTurno = lineScanner.next();
                                break;
                            case 68:
                                numeroTipoDestVotos = lineScanner.next();
                                break;
                            case 69:
                                codigoSituacaoCandidato = lineScanner.next();
                                break;
                            default:
                                lineScanner.next();
                                break;
                        }
                        i++;
                    }

                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd,mm,yyyy");

                    Partido partido = new Partido(siglaPartido, numeroPartido);

                    CandidatoFederal c = new CandidatoFederal(nome, nomeNaUrna, Integer.parseInt(codigoCargo), Integer.parseInt(codigoSituacaoCandidato), numeroCandidato,
                    partido, Integer.parseInt(numeroFederacao), LocalDate.parse(dataNascimento, formatter), Integer.parseInt(codigoSituacaoTurno), Integer.parseInt(codigoGenero), numeroTipoDestVotos); 
                
                }
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        //"DT_GERACAO";"HH_GERACAO";"ANO_ELEICAO";"CD_TIPO_ELEICAO";"NM_TIPO_ELEICAO";"NR_TURNO";"CD_ELEICAO";"DS_ELEICAO";"DT_ELEICAO";"TP_ABRANGENCIA";"SG_UF";"SG_UE";"NM_UE";PICADURA"CD_CARGO";"DS_CARGO";"SQ_CANDIDATO";PICADURA"NR_CANDIDATO";PICADURA"NM_CANDIDATO";PICADURA"NM_URNA_CANDIDATO";"NM_SOCIAL_CANDIDATO";"NR_CPF_CANDIDATO";"NM_EMAIL";"CD_SITUACAO_CANDIDATURA";"DS_SITUACAO_CANDIDATURA";"CD_DETALHE_SITUACAO_CAND";"DS_DETALHE_SITUACAO_CAND";"TP_AGREMIACAO";PICADURA"NR_PARTIDO";PICADURA"SG_PARTIDO";"NM_PARTIDO";PICADURA"NR_FEDERACAO";"NM_FEDERACAO";"SG_FEDERACAO";"DS_COMPOSICAO_FEDERACAO";"SQ_COLIGACAO";"NM_COLIGACAO";"DS_COMPOSICAO_COLIGACAO";"CD_NACIONALIDADE";"DS_NACIONALIDADE";"SG_UF_NASCIMENTO";"CD_MUNICIPIO_NASCIMENTO";"NM_MUNICIPIO_NASCIMENTO";PICADURA"DT_NASCIMENTO";"NR_IDADE_DATA_POSSE";"NR_TITULO_ELEITORAL_CANDIDATO";PICADURA"CD_GENERO";"DS_GENERO";"CD_GRAU_INSTRUCAO";"DS_GRAU_INSTRUCAO";"CD_ESTADO_CIVIL";"DS_ESTADO_CIVIL";"CD_COR_RACA";"DS_COR_RACA";"CD_OCUPACAO";"DS_OCUPACAO";"VR_DESPESA_MAX_CAMPANHA";PICADURA"CD_SIT_TOT_TURNO";"DS_SIT_TOT_TURNO";"ST_REELEICAO";"ST_DECLARAR_BENS";"NR_PROTOCOLO_CANDIDATURA";"NR_PROCESSO";"CD_SITUACAO_CANDIDATO_PLEITO";"DS_SITUACAO_CANDIDATO_PLEITO";"CD_SITUACAO_CANDIDATO_URNA";"DS_SITUACAO_CANDIDATO_URNA";"ST_CANDIDATO_INSERIDO_URNA";"NM_TIPO_DESTINACAO_VOTOS";PICADURA"CD_SITUACAO_CANDIDATO_TOT";"DS_SITUACAO_CANDIDATO_TOT";"ST_PREST_CONTAS";"ST_SUBSTITUIDO";"SQ_SUBSTITUIDO";"SQ_ORDEM_SUPLENCIA";"DT_ACEITE_CANDIDATURA"

        //14, 17, 18, 19, 28, 29, 31, 43, 46, 57, 69,
    /**
     * "14 CD_CARGO";
     * "17 NR_CANDIDATO";
"18 NM_CANDIDATO";
"19 NM_URNA_CANDIDATO";
28 "NR_PARTIDO";
29 "SG_PARTIDO";
31 "NR_FEDERACAO";
43 "DT_NASCIMENTO";
46 "CD_GENERO";
57 "CD_SIT_TOT_TURNO";
"68 NM_TIPO_DESTINACAO_VOTOS";
69 "CD_SITUACAO_CANDIDATO_TOT";

     */
        

        //Candidato = new Candidato();

    }
}

//java -jar deputados.jar --estadual consulta_cand_2022_SC.csv votacao_secao2022_SC.csv 02/10/2022


//https://drive.google.com/drive/folders/1SgsVnRC_TuY-YmtHumZNfgzUtpa5zwOP?usp=sharing