import java.util.*;


class Translator
{
	public Vector<String> Translate(Vector<String> instruction)
	{
		Vector<String> translatedInstructions = new Vector<String>();
		switch(instruction.get(0))
		{
			case "push":
				translatedInstructions = TranslatePushPop(instruction);
				break;
			case "pop":
				translatedInstructions = TranslatePushPop(instruction);
				break;
		}
		return translatedInstructions;
	}

	private Vector<String> TranslatePushPop(Vector<String> instruction)
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
				translatedInstructions.add("A = A+D");
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
			translatedInstructions.add("D = D+A");
			translatedInstructions.add("@R15");
			translatedInstructions.add("M = D");
			translatedInstructions.add("@sp");
			translatedInstructions.add("M = M-1");
			translatedInstructions.add("A = M");
			translatedInstructions.add("D = M");
			translatedInstructions.add("@R15");
			translatedInstructions.add("A = M");
			translatedInstructions.add("M = D");
		}
		return translatedInstructions;
	}

	private Vector<String> TranslateAdd(Vector<String> instruction)
	{
		return null;
	}
}