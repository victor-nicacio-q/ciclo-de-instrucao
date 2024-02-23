public class PC {
    static private int valorPc = 4;

    static public void incrementaPc(){
        valorPc++;
    }

    static public int get(){
        return valorPc;
    }
    
    static public void set(int valorEntrada){
        valorPc = valorEntrada;
    }
}