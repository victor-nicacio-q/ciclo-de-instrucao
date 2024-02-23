// package ocd;

class IR {

	private static String opcode;
	private static int porta1;
	private static int porta2;

	public static void define(int[] vet) {
		String fullCode = Util.getInstance().intArrayToString(vet);
		String porta1String = fullCode.substring(6, 12);
		String porta2String = fullCode.substring(12);

		opcode = fullCode.substring(0, 6);
		porta1 = Util.getInstance().binaryStringToInt(porta1String);
		porta2 = Util.getInstance().binaryStringToInt(porta2String);
	}

	public static String getOpcode() {
		return opcode;
	}

	public static void setOpcode(String opcode) {
		IR.opcode = opcode;
	}

	public static int get(int flg){
		if(flg == 0){
			return porta1;
		}else{
			return porta2;
		}
	}
}