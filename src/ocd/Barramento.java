public class Barramento {
    public static int [] endereco;
    public static int dados;

    void porta1() {
        PC = dados;
    }
    porta2(){
        dados = this.PC.get();
    }
    porta3(){
        MAR.set(dados);
    }
    porta4(){
        MBR.set(dados);
    }
    porta5(){
        dados = MBR.get();
    }
    porta12(){
        IR.set(dados)
    }
    porta13(){
        endereco = IR.porta2;
    }
    porta15(){
        endereco = IR.porta1;
    }
    porta18(){
        ULA(dados);
    }
    porta23(){
        dados = AC;        
    }
 }