
import java.io.*;
import java.util.*;

public class HackAssembler
{
	public static void main (String[] args)
	{
		SymbolTable table = new SymbolTable();

		Parser parse = new Parser("Prog.asm");
		int addressCounter = 16; //variable mem address starts at 16
		String current;

		//first pass
		while (parse.hasMoreLines())
		{
			current = parse.advance();
			if (parse.instructionType().equals("L_INSTRUCTION"))
			{
				table.addEntry(current, addressCounter);
				addressCounter++;
			}
		}
	}
}