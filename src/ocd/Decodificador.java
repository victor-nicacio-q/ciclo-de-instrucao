  // package ocd;

  public class Decodificador {
    // define as 6 primeiras posições do vetor como o opcode da palavra
    // as duas primeiras posições determinam o ciclo de instrução executado
    // as próximas três posições determinam a operação (adição subtração, etc...)
    // a sexta posição determina se a instrução é feita na porta 1 ou 2 do IR
    public static int[] adicionaUPCODE(int[] cod, int instrucao, int operacao, int porta) {
      if (instrucao == 0) {// adicionar ao acumulador
        // adiciona nas duas primeiras posicoes 00
        setInstrucao(cod, 0, 0);
      }
      if (instrucao == 1) {// envia para ula
        // adiciona nas duas primeiras posicoes 01
        setInstrucao(cod, 0, 1);
      }
      if (instrucao == 2) {// retornar valor p/ Memoria
        // adiciona nas duas primeiras posicoes 10
        setInstrucao(cod, 1, 0);
      }
      if (instrucao == 3) {// envia endereco p/ uc
        // adiciona nas duas primeiras posicoes 11
        setInstrucao(cod, 1, 1);
      }
      if (operacao == 0) {// add
        // adiciona nas proximas tres posicoes 000
        setOperacao(cod, 0, 0, 0);
      }
      if (operacao == 1) {// sub
        // adiciona nas proximas tres posicoes 001
        setOperacao(cod, 0, 0, 1);
      }
      if (operacao == 2) {// mul
        // adiciona nas proximas tres posicoes 010
        setOperacao(cod, 0, 1, 0);
      }
      if (operacao == 3) {// div
        // adiciona nas proximas tres posicoes 011
        setOperacao(cod, 0, 1, 1);
      }
      if (operacao == 4) {// inc
        // adiciona nas proximas tres posicoes 100
        setOperacao(cod, 1, 0, 0);
      }
      if (operacao == 5) {// dec
        // adiciona nas proximas tres posicoes 101
        setOperacao(cod, 1, 0, 1);
      }
      if (operacao == 7) {//mov (não é jogado na ULA, mas é verificado na execução)
        setOperacao(cod, 1, 1, 1);
      }
      if (porta == 0) {// porta 1
        // adiciona na sexta posicao 0
        setPorta(cod, 0);
      }
      if (porta == 1) {// porta 2
        // adiciona na sexta posicao 1
        setPorta(cod, 1);
      }

      return cod;

    }
    
    //funções auxiliares para o adicionaUPCODE()
    private static void setInstrucao(int[] cod, int pos1, int pos2) {
      cod[0] = pos1;
      cod[1] = pos2;
    }
    
    private static void setOperacao(int[] cod, int pos1, int pos2, int pos3) {
      cod[2] = pos1;
      cod[3] = pos2;
      cod[4] = pos3;
    }
    
    private static void setPorta(int[] cod, int pos) {
      cod[5] = pos;
    }

    // define os próximos endereços do vetor como os
	// endereços de memória para os comandos executados
	// (6 para cada endereço)
    public static int[] insereEnderecos(int[] cod, String instrucao) {
      String registrador1 = instrucao.substring(4,6);
      String registrador2 = instrucao.substring(instrucao.indexOf(',')+1,instrucao.length());

      //define primeiro endereço
      if(registrador1.equalsIgnoreCase("AX")){
        //registrador na posição 0
        for(int i = 6;i < 12;i++){
          cod[i] = 0;
        }
      }
      else if(registrador1.equalsIgnoreCase("BX")){
        //registrador na posição 1
        for(int i = 6;i < 11;i++){
          cod[i] = 0;
        }
        cod[11] = 1;
      }
      else if(registrador1.equalsIgnoreCase("CX")){
        //registrador na posição 2
        for(int i = 6;i < 10;i++){
          cod[i] = 0;
        }
        cod[10] = 1;
        cod[11] = 0;
      }
      else if(registrador1.equalsIgnoreCase("DX")){
        //registrador na posição 3
        for(int i = 6;i < 10;i++){
          cod[i] = 0;
        }
        cod[10] = 1;
        cod[11] = 1;
      }
      else{
        int valor = Integer.parseInt(registrador1);
        String valorBin = Integer.toBinaryString(valor);
        //insere primeiro tudo zero
        for(int i = 6;i < 12;i++){
          cod[i] = 0;
        }
        //depois insere o valor em binario da direita pra esquerda
        int j = 11;
        for(int i = valorBin.length()-1;i >= 0;i--){
          cod[j] = valorBin.charAt(i) - '0';
          j--;
        }
      }

      //define segundo endereço
      if(registrador2.equalsIgnoreCase("AX")){
        //registrador na posição 0
        for(int i = 12;i < cod.length;i++){
          cod[i] = 0;
        }
      }
      else if(registrador2.equalsIgnoreCase("BX")){
        //registrador na posição 1
        for(int i = 12;i < cod.length-1;i++){
          cod[i] = 0;
        }
        cod[cod.length-1] = 1;
      }
      else if(registrador2.equalsIgnoreCase("CX")){
        //registrador na posição 2
        for(int i = 12;i < cod.length-2;i++){
          cod[i] = 0;
        }
        cod[cod.length-2] = 1;
        cod[cod.length-1] = 0;
      }
      else if(registrador2.equalsIgnoreCase("DX")){
        //registrador na posição 3
        for(int i = 12;i < cod.length-2;i++){
          cod[i] = 0;
        }
        cod[cod.length-2] = 1;
        cod[cod.length-1] = 1;
      }
      //caso em que não é colocado um segundo valor (depois da virgula, que nesse caso não existe) na instrução
      else if(registrador2.equalsIgnoreCase(instrucao)){
        for(int i = 12;i < cod.length-2;i++){
          cod[i] = 0;
        }
      }
      //transforma registrador 2 em um valor inteiro, transforma em binário a adiciona na última posição da memória (reservada p/ entrada de valores numéricos)
      else{
        int valor = Integer.parseInt(registrador2);
        int[] constanteNumerica = new int[18];
        constanteNumerica = Util.getInstance().IntegerToArray(valor);
        //adiciona valor na última posição de memória, e passa esse endereço para a IR
        Memoria.add(63,constanteNumerica);
        for(int i = 12;i < cod.length;i++){
          cod[i] = 1;
        }
      }
      return cod;
    }

    //Método principal para decodificar a instrução passada por linha de comando
    @SuppressWarnings("unused")
    public static void decodifica(String s) {
      if (s.substring(0, 3).equalsIgnoreCase("ADD")) {
        int[] codigo1 = new int[18];
        int[] codigo2 = new int[18];
        int[] codigo3 = new int[18];

        // insere o opcode do primeiro item na memória
        codigo1 = adicionaUPCODE(codigo1, 0, 0, 0);
        codigo1 = insereEnderecos(codigo1, s);

        // insere o opcoden do segundo item na memória
        codigo2 = adicionaUPCODE(codigo2, 1, 0, 1);
        codigo2 = insereEnderecos(codigo2, s);

        // insere o opcode do terceiro item na memória
        codigo3 = adicionaUPCODE(codigo3, 2, 0, 0);
        codigo3 = insereEnderecos(codigo3, s);

        Memoria.add(codigo1);
        Memoria.add(codigo2);
        Memoria.add(codigo3);
      } else if (s.substring(0, 3).equalsIgnoreCase("SUB")) {
        int[] codigo1 = new int[18];
        int[] codigo2 = new int[18];
        int[] codigo3 = new int[18];

        codigo1 = adicionaUPCODE(codigo1, 0, 1, 0);
        codigo1 = insereEnderecos(codigo1, s);

        codigo2 = adicionaUPCODE(codigo2, 1, 1, 1);
        codigo2 = insereEnderecos(codigo2, s);

        codigo3 = adicionaUPCODE(codigo3, 2, 1, 0);
        codigo3 = insereEnderecos(codigo3, s);

        Memoria.add(codigo1);
        Memoria.add(codigo2);
        Memoria.add(codigo3);
      } else if (s.substring(0, 3).equalsIgnoreCase("MUL")) {
        int[] codigo1 = new int[18];
        int[] codigo2 = new int[18];
        int[] codigo3 = new int[18];

        codigo1 = adicionaUPCODE(codigo1, 0, 2, 0);
        codigo1 = insereEnderecos(codigo1, s);

        codigo2 = adicionaUPCODE(codigo2, 1, 2, 1);
        codigo2 = insereEnderecos(codigo2, s);

        codigo3 = adicionaUPCODE(codigo3, 2, 2, 0);
        codigo3 = insereEnderecos(codigo3, s);

        Memoria.add(codigo1);
        Memoria.add(codigo2);
        Memoria.add(codigo3);
      } else if (s.substring(0, 3).equalsIgnoreCase("DIV")) {
        int[] codigo1 = new int[18];
        int[] codigo2 = new int[18];
        int[] codigo3 = new int[18];

        codigo1 = adicionaUPCODE(codigo1, 0, 3, 0);
        codigo1 = insereEnderecos(codigo1, s);

        codigo2 = adicionaUPCODE(codigo2, 1, 3, 1);
        codigo2 = insereEnderecos(codigo2, s);

        codigo3 = adicionaUPCODE(codigo3, 2, 3, 0);
        codigo3 = insereEnderecos(codigo3, s);

        Memoria.add(codigo1);
        Memoria.add(codigo2);
        Memoria.add(codigo3);
      } else if (s.substring(0, 3).equalsIgnoreCase("MOV")) {
        int[] codigo1 = new int[18];
        int[] codigo2 = new int[18];
        int[] codigo3 = new int[18];

        codigo1 = adicionaUPCODE(codigo1, 0, 7, 1);
        codigo1 = insereEnderecos(codigo1, s);

        codigo2 = adicionaUPCODE(codigo2, 2, 7, 0);
        codigo2 = insereEnderecos(codigo2, s);

        codigo3 = adicionaUPCODE(codigo3, 0, 7, 0);
        codigo3 = insereEnderecos(codigo3, s);

        Memoria.add(codigo1);
        Memoria.add(codigo2);
        Memoria.add(codigo3);
      } else if (s.substring(0, 3).equalsIgnoreCase("INC")) {
        int[] codigo1 = new int[18];
        int[] codigo2 = new int[18];
        int[] codigo3 = new int[18];

        // insere o opcode do primeiro item na memória
        codigo1 = adicionaUPCODE(codigo1, 0, 4, 0);
        codigo1 = insereEnderecos(codigo1, s);

        // insere o opcoden do segundo item na memória
        codigo2 = adicionaUPCODE(codigo2, 1, 4, 1);
        codigo2 = insereEnderecos(codigo2, s);

        // insere o opcode do terceiro item na memória
        codigo3 = adicionaUPCODE(codigo3, 2, 4, 0);
        codigo3 = insereEnderecos(codigo3, s);

        Memoria.add(codigo1);
        Memoria.add(codigo2);
        Memoria.add(codigo3);
      } else if (s.substring(0, 3).equalsIgnoreCase("DEC")) {
        int[] codigo1 = new int[18];
        int[] codigo2 = new int[18];
        int[] codigo3 = new int[18];

        // insere o opcode do primeiro item na memória
        codigo1 = adicionaUPCODE(codigo1, 0, 5, 0);
        codigo1 = insereEnderecos(codigo1, s);

        // insere o opcoden do segundo item na memória
        codigo2 = adicionaUPCODE(codigo2, 1, 5, 1);
        codigo2 = insereEnderecos(codigo2, s);

        // insere o opcode do terceiro item na memória
        codigo3 = adicionaUPCODE(codigo3, 2, 5, 0);
        codigo3 = insereEnderecos(codigo3, s);

        Memoria.add(codigo1);
        Memoria.add(codigo2);
        Memoria.add(codigo3);
      }// else if (s.substring(0, 3) == "CMP") {

      //}
      else if (s.substring(0, 3).equalsIgnoreCase("JMP")) {
        int[] codigo1 = new int[18];
        int[] codigo2 = new int[18];
        int[] codigo3 = new int[18];

        // insere o opcode do primeiro item na memória
        codigo1 = adicionaUPCODE(codigo1, 3, 0, 0);
        codigo1 = insereEnderecos(codigo1, s);

        // insere o opcoden do segundo item na memória
        codigo2 = adicionaUPCODE(codigo2, 0, 0, 0);
        codigo2 = insereEnderecos(codigo2, s);

        // insere o opcode do terceiro item na memória
        codigo3 = adicionaUPCODE(codigo3, 0, 0, 0);
        codigo3 = insereEnderecos(codigo3, s);

        Memoria.add(codigo1);
        Memoria.add(codigo2);
        Memoria.add(codigo3);
      }// else if (s.substring(0, 3) == "JPE") {

      // } else if (s.substring(0, 3) == "JPG") {

      // } else if (s.substring(0, 3) == "JGE") {

      // } else if (s.substring(0, 3) == "JPL") {

      // } else if (s.substring(0, 3) == "JLE") {

      // } else if (s.substring(0, 3) == "JNE") {

      // }
      else{
        System.out.println("Operador inválido.");
      }
    }

  }