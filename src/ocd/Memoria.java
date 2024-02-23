public class Memoria {
    private static int[][] memoria = new int[64][18];
    private static int contIndice = 0;
    public static int[] getValor(int indice){
        return memoria[indice];
    }
    public static void add(int[] palavra){
    	// System.out.println("ADICIONA PALAVRA NA MEMORIA");
        memoria[contIndice] = palavra;
        contIndice++;
    }
    public static void add(int indice, int[] palavra){
    	memoria[indice] = palavra;
    }
    public static void imprime(int i){
        System.out.println(Util.getInstance().intArrayToString(memoria[i]));
    }
}