import java.io.*;
import java.util.Scanner;

class Main {
  public static void cicloBusca(){
    System.out.println("Inicia ciclo de busca");
    MAR.set(PC.get());                                  //MAR <- (PC)
    MBR.set(Memoria.getValor(MAR.valor));               //MBR <- (memória)
    IR.define(MBR.get());                               //IR <- (MBR)
    UC(IR.getOpcode());                                 //UC interpreta opcode da IR (Incremento do PC ocorre aqui)
  }

  public static void UC(String opcode){
    System.out.println("Executa instrução");
    int flag = opcode.charAt(5) - '0';   //flag para determinar qual porta da IR será acessada
    PC.incrementaPc();                                  //PC <- (PC) +1
    if(opcode.substring(0,2).equals("00")){
      //instrução 1 - Coloca endereço 1 no acumulador
      System.out.println("Entrou na execução 1");
      MAR.set(IR.get(flag));                            //MAR <- (IR)
      MBR.set(Memoria.getValor(MAR.valor));             //MBR <- (memória)
      AC.recebeVetor(MBR.get());                        //AC <- (MBR)
      Registradores.imprimir();
      cicloBusca();
    }
    else if(opcode.substring(0,2).equals("01")){
      //instrução 2 - Soma endereço 2 ao acumulador
      System.out.println("Entrou na execução 2");
      MAR.set(IR.get(flag));                            //MAR <- (IR)
      MBR.set(Memoria.getValor(MAR.valor));             //MBR <- (memória)
      ULA.send(MBR.get(),IR.getOpcode());               //ULA <- (MBR) *não é necessário passar IR com parâmetro, mas torna o código mais simples
      Registradores.imprimir();
      cicloBusca();
    }
    else if(opcode.substring(0,2).equals("10")){
      System.out.println("Entrou na execução 3");
      //instrução 3 - Coloca acumulador no endereço 1
      MAR.set(IR.get(flag));                            //MAR <- (IR)
      MBR.set(AC.get());                                //MBR <- (AC)
      MBR.insereMemoria(MAR.valor);                     //memória <- (MBR)
      Registradores.imprimir();
      cicloBusca();
    }
    else if(opcode.substring(0,2).equals("11")){
      System.out.println("Entrou na execução 4");
      //instrução 4 - Coloca o endereço 1 no PC
      PC.set(IR.get(flag)+3);                           //PC <- (IR)
      Registradores.imprimir();
      cicloBusca();
    }
    else{
      System.out.println("Algo deu errado");
    }
  }

  public static void main(String[] args) throws IOException{
      Registradores.inicializar();
      boolean continua = true;
      
      InputStream is = new FileInputStream("instrucoes.txt");
      InputStreamReader isn = new InputStreamReader(is);
      BufferedReader br = new BufferedReader(isn);

      while(continua){
        String s = br.readLine();
        Registradores.imprimir();
        System.out.println("");
        System.out.println("*****"+s);
        System.out.println("");
        Scanner ler = new Scanner(System.in);
        System.out.println("Insira operação: (ex: ADD AX,BX)");
        String s = ler.nextLine();
        System.out.println("");
        
        if(s.equalsIgnoreCase("end")){
          System.out.println("Fim da execução");
          continua = false;
        }else {
          Decodificador.decodifica(s);
          cicloBusca();
        }
      }
      
  }
}