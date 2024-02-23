// package ocd;

public class ULA {
  public static void send(int [] valor, String operador){
    //para facilitar (já tínhamos conversores prontos), transformamos o vetor das palavras para string, depois transformamos novamente para um inteiro e realizamos as operações matemáticas
    String aux1 = Util.getInstance().intArrayToString(AC.get());
    int ac = Util.getInstance().binaryStringToInt(aux1);
    String aux2 = Util.getInstance().intArrayToString(MBR.get());
    int mbr = Util.getInstance().binaryStringToInt(aux2);
    //realiza operação matemática de acordo com o opcode da palavra
    String opc = operador.substring(2,5);
    int operacao = Util.getInstance().binaryStringToInt(opc);
    if (operacao == 0) {// add
      ac += mbr;
    }
    if (operacao == 1) {// sub
      ac -= mbr;
    }
    if (operacao == 2) {// mul
      ac *= mbr;
    }
    if (operacao == 3) {// div
      ac /= mbr;
    }
    if (operacao == 4) {// inc
      ac++;
    }
    if (operacao == 5) {// dec
      ac--;
    }
    //Após realizada a operação, transformamos o inteiro ac em vetor e colocamos devolta ao registrador AC
    AC.recebeVetor(Util.getInstance().IntegerToArray(ac));
  }
}