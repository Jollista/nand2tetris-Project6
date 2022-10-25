
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
				table.addEntry(parse.symbol(), parse.getLineNumber());
				System.out.println("------CURRENT: " + current + "\n------LINENUM: " + parse.getLineNumber());
				System.out.println("***SYMBOL***: " + parse.symbol());
			}
		}

		FileWriter writer = new FileWriter("Out.hack");
		parse = new Parser(filename);
		Code code = new Code();
		String symbol, dest, comp, jump, binary = "";
		//second pass
		while(parse.hasMoreLines())
		{
			binary = "";
			current = parse.advance();
			type = parse.instructionType();
			if (type != null && (type.equals("A_INSTRUCTION")))
			{
				//add symbol to table if not already present
				symbol = parse.symbol();
				try {
					Integer.parseInt(symbol);	
				} catch (Exception e) {
					if (!(table.contains(symbol)))
					{
						table.addEntry(symbol, addressCounter);
						addressCounter++;
					}
				}

				//translate symbol to binary value
				System.out.println("Symbol: " + symbol);
				binary = symToBin(symbol, type, table); //bin val
			}
			else if (type != null && type.equals("C_INSTRUCTION"))
			{
				//translate each of the three fields to binary
				//d
				dest = parse.dest();
				if (dest == null)
					dest = "000";
				else
					dest = code.dest(dest); //bin

				//c
				comp = parse.comp();
				String temp = comp;
				comp = code.comp(comp); //bin
				//System.out.println("Untranslated Comp: " + temp);
				
				//111a
				if (temp.contains("M"))//if (current.contains("M") && !(current.contains(";") && current.indexOf("M") > current.indexOf(";"))) //contains an M that isn't the only thing left of ;
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
				
				System.out.println("comp: " + comp + "\ndest: " + dest + "\njump: " + jump);

				//assemble binary values into a string of sixteen 0's and 1's
				binary = comp + dest + jump;
			}

			//write string to output file
			if (binary != null && binary != "")
			{
				System.out.println(binary);
				writer.write(binary+"\n");
			}
		}
		writer.close();
	}

	/*
	 * convert symbol to binary
	 */
	public static String symToBin(String symbol, String type, SymbolTable table)
	{
		try {
			//
			int n = Integer.parseInt(symbol);
			return decimalToBinary(n);
		} catch (Exception e) {
			//variable or label
			String temp = decimalToBinary(table.getAddress(symbol));
			return temp;
		}
	}

	public static String decimalToBinary(int num)
	{
		// Creating and assigning binary array size
		String binary = "";
	
		// Number should be positive
		while (num > 0) 
		{
			//System.out.println("Num: " + num);
			binary = num % 2 + binary;
			//System.out.println("Bin: " + binary);
			num = num / 2;
		}

		//add leading zeroes
		while (binary.length() < 16)
			binary = "0" + binary;
	
		return binary;
	}
}