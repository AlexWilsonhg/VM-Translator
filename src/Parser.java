import java.util.*;

class Parser
{
	public Vector<String> ParseLine(String line)
	{
		if(line != null)
		{
			String split[] = line.split(" ", 0);
			Vector<String> parsedLine = RemoveComments(split);
			return parsedLine;
		}
		return null;
	}

	private Vector<String> RemoveComments(String[] lineElements)
	{
		Vector<String> cleanedElements = new Vector<String>();
		for(String s : lineElements)
		{
			if(s.contains("/"))
			{
				break;
			}
			else
			{
				cleanedElements.add(s);
			}
		}
		return cleanedElements;
	}
}