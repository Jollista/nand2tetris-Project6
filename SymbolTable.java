import java.util.Hashtable;

public class SymbolTable 
{
	private Hashtable<String, Integer> table = new Hashtable<String, Integer>();

	SymbolTable()
	{
		//Set predefined symbols
		//R0-R15
		String r;
		for (int i = 0; i < 16; i++)
		{
			r = "R" + i;
			table.put(r, i);
		}
		table.put("SCREEN", 16384);
		table.put("KBD", 24576);
		table.put("SP", 0);
		table.put("LCL", 1);
		table.put("ARG", 2);
		table.put("THIS", 3);
		table.put("THAT", 4);
	}

	public void addEntry(String symbol, int address)
	{
		//Adds <symbol, address> to table
		table.put(symbol, address);
	}

	public boolean contains(String symbol)
	{
		//returns whether or not 
		return table.containsKey(symbol);
	}

	public int getAddress(String symbol)
	{
		return table.get(symbol);
	}
}
