import java.util.Hashtable;

public class SymbolTable 
{
	private Hashtable<String, Integer> table = new Hashtable<String, Integer>();

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
