// package ocd;

public class Util {
	
	private static Util instance;
	
	private Util() {
		
	}
	
	public static Util getInstance() {
		if (instance == null) {
			instance = new Util();
		}
		return instance;
	}
	
	public String intArrayToString(int[] array) {
		StringBuilder sb = new StringBuilder();
		for (int i : array) {
			sb.append(i);
		}
		return sb.toString();
	}
	
	public int binaryStringToInt(String string) {
		return Integer.parseInt(string, 2);
	}

  public int[] IntegerToArray(int numero){
    int d = numero;
    int b;
		int[] vetor = new int[18];
		int i =0;
      while ( d > 0){
        b = d % 2;
        vetor[17-i]=b;
        d -= (d/2)+b;
        i++;
      }
		return vetor;
  }
}
