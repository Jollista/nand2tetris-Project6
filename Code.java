public class Code 
{
	public String dest(String dest)
	{
		switch (dest) {
			case "M":
				return "001";
			case "D":
				return "010";
			case "DM":
			case "MD":
				return "011";
			case "A":
				return "100";
			case "AM":
				return "101";
			case "AD":
				return "110";
			case "ADM":
				return "111";
			default: //null
				return "000";
		}
	}

	public String comp(String comp)
	{
		System.out.println("Untranslated Comp: [" + comp + "]");
		switch (comp)
		{
			case "1":
				return "111111";
			case "-1":
				return "111010";
			case "D":
				return "001100";
			case "A":
			case "M":
				return "110000";
			case "!D":
				return "001101";
			case "!A":
			case "!M":
				return "110001";
			case "-D":
				return "001111";
			case "-A":
			case "-M":
				return "110011";
			case "D+1":
				return "011111";
			case "A+1":
			case "M+1":
				return "110111";
			case "D-1":
				return "001110";
			case "A-1":
			case "M-1":
				return "110010";
			case "D+A":
			case "D+M":
				return "000010";
			case "D-A":
			case "D-M":
				return "010011";
			case "A-D":
			case "M-D":
				return "000111";
			case "D&A":
			case "D&M":
				return "000000";
			case "D|A":
			case "D|M":
				return "010101";
			default: //0
				return "101010";
		}
	}

	public String jump(String jump)
	{
		switch (jump) {
			case "JGT":
				return "001";
			case "JEQ":
				return "010";
			case "JGE":
				return "011";
			case "JLT":
				return "100";
			case "JNE":
				return "101";
			case "JLE":
				return "110";
			case "JMP":
				return "111";	
			default: //null
				return "000";
		}
	}
}
