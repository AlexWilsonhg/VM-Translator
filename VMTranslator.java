import java.io.*;
import java.util.*;

class VMTranslator
{
	public static void main(String[] args)
	{
		FileInput file = new FileInput(args[0]);
		Parser parser = new Parser();
		Translator translator = new Translator("Test.asm");

		Vector<String> parsedLine = parser.ParseLine(file.GetLine());
		translator.TranslatePushPop(parsedLine);
	}
}

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

class FileInput
{
	private BufferedReader br;

	public FileInput(String filename)
	{
		try
		{
			br = new BufferedReader(new FileReader(filename));
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
	}

	public String GetLine()
	{
		String line  = null;
		try
		{
			line = br.readLine();
		}
		catch(Exception e)
		{
			System.out.println(e);
		}

		return line;
	}
}

class Translator
{
	private FileWriter writer;

	public Translator(String filename)
	{
		try
		{
			writer = new FileWriter(filename);
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
	}

	public void TranslatePushPop(Vector<String> instruction)
	{
		Vector<String> translatedInstructions = new Vector<String>();
		// push
		if(instruction.get(0).equals("push"))
		{
			if(instruction.get(1).equals("constant"))
			{
				translatedInstructions.add("@" + instruction.get(2));
				translatedInstructions.add("D = A");
			}
			else
			{
				translatedInstructions.add("@" + instruction.get(1));
				translatedInstructions.add("D = M");
				translatedInstructions.add("@" + instruction.get(2));
				translatedInstructions.add("A = A + D");
				translatedInstructions.add("D = M");
			}
			translatedInstructions.add("@sp");
			translatedInstructions.add("A = M");
			translatedInstructions.add("M = D");
			translatedInstructions.add("@sp");
			translatedInstructions.add("M = M+1");
		// pop
		}
		else
		{
			translatedInstructions.add("@" + instruction.get(1));
			translatedInstructions.add("D = M");
			translatedInstructions.add("@" + instruction.get(2));
			translatedInstructions.add("D = D + A");
			translatedInstructions.add("@R15");
			translatedInstructions.add("M = D");
			translatedInstructions.add("@sp");
			translatedInstructions.add("M = M - 1");
			translatedInstructions.add("A = M");
			translatedInstructions.add("D = M");
			translatedInstructions.add("@R15");
			translatedInstructions.add("A = M");
			translatedInstructions.add("M = D");
		}
		try
		{
			WriteToFile(translatedInstructions);
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
	}

	private void WriteToFile(Vector<String> instructions) throws IOException
	{
		if(instructions != null)
		{
			for(String s : instructions)
			{
				writer.write(s);
				writer.write("\n");
			}
		}
		writer.close();
	}
}