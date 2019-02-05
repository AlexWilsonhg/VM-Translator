import java.io.*;
import java.util.*;

class VMTranslator
{
	public static void main(String[] args)
	{
		FileIO fileIO = new FileIO(args[0]);
		Parser parser = new Parser();
		Translator translator = new Translator();

		String line = null;
		while((line = fileIO.ReadLine()) != null)
		{
			Vector<String> parsedInstructions = parser.ParseLine(line);
			Vector<String> translatedInstructions = translator.Translate(parsedInstructions);
			fileIO.WriteToFile(translatedInstructions);
		}

		fileIO.Shutdown();
	}
}