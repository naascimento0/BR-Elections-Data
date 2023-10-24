package eleicao.agremiacao;

import java.util.LinkedList;

public class Federacao {
    private int numeroFederacao;
    LinkedList<Partido> partidos = new LinkedList<>();

    public Federacao(int numeroFederacao) {
        this.numeroFederacao = numeroFederacao;
    }

    public int getNumeroFederacao() {
        return numeroFederacao;
    }

    public void addPartido(Partido partido){
        partidos.add(partido);
    }

    public void removePartido(Partido partido){
        partidos.remove(partido);
    }

    // @Override
    /*public String toString() {
        String text = "Federacao [partidos= ";
        
        for(Partido partido : partidos){
            text += partido + " ";
        }

        return text;
    }*/

    public String toString() {
        String text = "Federacao [partidos= ";
        
        for(Partido partido : partidos){
            text += partido.getSiglaPartido() + ", ";
        }

        return text;
    }
}
