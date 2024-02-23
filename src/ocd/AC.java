// package ocd;

public class AC{
    static private int[] vetor;
    
    static public void recebeVetor(int[] vetorEntrada){
        vetor = vetorEntrada;
    }

    static public int[] get(){
        return vetor;
    }
}