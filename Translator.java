import java.util.*;


class Translator
{
	public Vector<String> Translate(Vector<String> instruction)
	{
		Vector<String> translatedInstructions = new Vector<String>();
		switch(instruction.get(0))
		{
			case "push":
				translatedInstructions = TranslatePush(instruction);
				break;
			case "pop":
				translatedInstructions = TranslatePop(instruction);
				break;
			case "add":
				translatedInstructions = TranslateSubAdd(instruction);
				break;
			case "sub":
				translatedInstructions = TranslateSubAdd(instruction);
		}
		return translatedInstructions;
	}

	private Vector<String> TranslatePush(Vector<String> instruction)
	{
		Vector<String> translatedInstructions = new Vector<String>();

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
			translatedInstructions.add("A = D+A");
			translatedInstructions.add("D = M");
		}
			translatedInstructions.add("@sp");
			translatedInstructions.add("A = M");
			translatedInstructions.add("M = D");
			translatedInstructions.add("@sp");
			translatedInstructions.add("M = M+1");

		return translatedInstructions;
	}

	private Vector<String> TranslatePop(Vector<String> instruction)
	{
		Vector<String> translatedInstructions = new Vector<String>();

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

		return translatedInstructions;
	}

	private Vector<String> TranslateSubAdd(Vector<String> instruction)
	{
		Vector<String> translatedInstructions = new Vector<String>();

		translatedInstructions.add("@sp");
		translatedInstructions.add("M = M-1");
		translatedInstructions.add("A = M");
		translatedInstructions.add("D = M");
		translatedInstructions.add("A = A-1");
		if(instruction.get(0).equals("sub"))
		{
			translatedInstructions.add("M = M-D");
		}
		else
		{
			translatedInstructions.add("M = M+D");
		}

		return translatedInstructions;
	}
}