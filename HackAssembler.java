
import java.io.*;
import java.util.*;

public class HackAssembler
{
	public static void main (String[] args) throws IOException
	{
		Scanner kb = new Scanner(System.in);  // Create a Scanner object
    		System.out.print("Enter filename: ");
		String filename = kb.nextLine();

		SymbolTable table = new SymbolTable();

		Parser parse = new Parser(filename);
		int addressCounter = 16; //variable mem address starts at 16
		String current, type;

		//first pass
		while (parse.hasMoreLines())
		{
			current = parse.advance();
			type = parse.instructionType();
			if (type != null && type.equals("L_INSTRUCTION"))
			{
				table.addEntry(current, addressCounter);
				addressCounter++;
			}
		}

		FileWriter writer = new FileWriter("Out.hack");
		parse = new Parser(filename);
		Code code = new Code();
		String symbol, dest, comp, jump, binary = "";
		//second pass
		while(parse.hasMoreLines())
		{
			current = parse.advance();
			type = parse.instructionType();
			if (type != null && type.equals("A_INSTRUCTION"))
			{
				//add symbol to table if not already present
				symbol = parse.symbol();
				if (!table.contains(symbol))
				{
					table.addEntry(symbol, addressCounter);
					addressCounter++;
				}

				//translate symbol to binary value
				binary = symToBin(symbol); //bin val
			}
			else if (type != null && type.equals("C_INSTRUCTION"))
			{
				//translate each of the three fields to binary
				//d
				dest = parse.dest();
				dest = code.dest(dest); //bin

				//c
				comp = parse.comp();
				comp = code.comp(comp); //bin
				
				//111a
				if (current.contains("M"))
					comp = "1" + comp;
				else
					comp = "0" + comp;
				comp = "111" + comp;

				//j
				jump = parse.jump();
				if (jump == null)
					jump = "000";
				else
					jump = code.jump(jump); //bin

				//assemble binary values into a string of sixteen 0's and 1's
				binary = comp + dest + jump;
			}

			//write string to output file
			System.out.println(binary);
			writer.write(binary);
		}
		writer.close();
	}

	/*
	 * convert symbol to binary
	 */
	public static String symToBin(String symbol)
	{
		String bin = "0";
		byte[] infoBin;
        	try {
			infoBin = symbol.getBytes("UTF-8");
			for (byte b : infoBin) 
				bin += Integer.toBinaryString(b);
	
			//add leading zeroes until 16 bit
			while (bin.length() < 16)
				bin = "0"+bin;
			return bin;
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}