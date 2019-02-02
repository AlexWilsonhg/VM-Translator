import java.util.*;
import java.io.*;

class FileIO
{
	private BufferedWriter writer;
	private BufferedReader reader;

	public FileIO(String fileName)
	{
		try
		{
			reader = new BufferedReader(new FileReader(fileName));
			writer = new BufferedWriter(new FileWriter("asm_" + fileName));
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
	}

	public String ReadLine()
	{
		String line = null;
		try
		{
			line = reader.readLine();
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		return line;
	}

	public void WriteToFile(Vector<String> lines)
	{
		if(lines != null)
		{
			for(String line : lines)
			{
				try
				{
					writer.write(line);
					writer.write('\n');
				}
				catch(Exception e)
				{
					System.out.println(e);
				}
			}
		}
	}

	public void Shutdown()
	{
		try
		{
			writer.close();
			reader.close();
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
	}
}