import java.util.*;

class Translator
{
	private int currentLine = 0;

	public Vector<String> Translate(Vector<String> instruction)
	{
		Vector<String> translatedInstructions = new Vector<String>();
		if((instruction.size()) >= 1)
		{
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
					break;
				case "neg":
					translatedInstructions = TranslateNegate(instruction);
					break;
				case "eq":
					translatedInstructions = TranslateEquals(instruction);
					break;
				case "gt":
					translatedInstructions = TranslateGreaterThan(instruction);
					break;
				case "lt":
					translatedInstructions = TranslateLessThan(instruction);
					break;
				case "and":
					translatedInstructions = TranslateAndOr(instruction);
					break;
				case "or":
					translatedInstructions = TranslateAndOr(instruction);
					break;
				case "not":
					translatedInstructions = TranslateNot(instruction);
					break;
			}
		}
		currentLine += translatedInstructions.size();
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
			translatedInstructions.add("M = D+M");
		}

		return translatedInstructions;
	}
	
	private Vector<String> TranslateNegate(Vector<String> instruction)
	{
		Vector<String> translatedInstructions = new Vector<String>();

		translatedInstructions.add("@sp");
		translatedInstructions.add("A = M-1");
		translatedInstructions.add("M = M-1");
		translatedInstructions.add("M = !M");

		return translatedInstructions;
	}

	private Vector<String> TranslateEquals(Vector<String> instruction)
	{
		Vector<String> translatedInstructions = new Vector<String>();

		translatedInstructions.add("@sp");
		translatedInstructions.add("M = M-1");
		translatedInstructions.add("A = M");
		translatedInstructions.add("D = M");
		translatedInstructions.add("A = A-1");
		translatedInstructions.add("D = D-M");
		translatedInstructions.add("@" + (currentLine + 13));
		translatedInstructions.add("D;JEQ");
		translatedInstructions.add("@sp");
		translatedInstructions.add("A = M-1");
		translatedInstructions.add("M = 0");
		translatedInstructions.add("@" + (currentLine + 16));
		translatedInstructions.add("0;JMP");
		translatedInstructions.add("@sp");
		translatedInstructions.add("A = M-1");
		translatedInstructions.add("M = -1");

		return translatedInstructions;	
	}

	private Vector<String> TranslateLessThan(Vector<String> instruction)
	{
		Vector<String> translatedInstructions = new Vector<String>();

		translatedInstructions.add("@sp");
		translatedInstructions.add("M = M-1");
		translatedInstructions.add("A = M");
		translatedInstructions.add("D = M");
		translatedInstructions.add("A = A-1");
		translatedInstructions.add("D = D-M");
		translatedInstructions.add("@" + (currentLine + 13));
		translatedInstructions.add("D;JLT");
		translatedInstructions.add("@sp");
		translatedInstructions.add("A = M-1");
		translatedInstructions.add("M = 0");
		translatedInstructions.add("@" + (currentLine + 16));
		translatedInstructions.add("0;JMP");
		translatedInstructions.add("@sp");
		translatedInstructions.add("A = M-1");
		translatedInstructions.add("M = -1");

		return translatedInstructions;	
	}

	private Vector<String> TranslateGreaterThan(Vector<String> instruction)
	{
		Vector<String> translatedInstructions = new Vector<String>();

		translatedInstructions.add("@sp");
		translatedInstructions.add("M = M-1");
		translatedInstructions.add("A = M");
		translatedInstructions.add("D = M");
		translatedInstructions.add("A = A-1");
		translatedInstructions.add("D = D-M");
		translatedInstructions.add("@" + (currentLine + 13));
		translatedInstructions.add("D;JGT");
		translatedInstructions.add("@sp");
		translatedInstructions.add("A = M-1");
		translatedInstructions.add("M = 0");
		translatedInstructions.add("@" + (currentLine + 16));
		translatedInstructions.add("0;JMP");
		translatedInstructions.add("@sp");
		translatedInstructions.add("A = M-1");
		translatedInstructions.add("M = -1");

		return translatedInstructions;	
	}

	private Vector<String> TranslateAndOr(Vector<String> instruction)
	{
		Vector<String> translatedInstructions = new Vector<String>();

		translatedInstructions.add("@sp");
		translatedInstructions.add("M = M-1");
		translatedInstructions.add("A = M");
		translatedInstructions.add("D = M");
		translatedInstructions.add("A = A-1");
		if(instruction.get(0).equals("and"))
		{
			translatedInstructions.add("M = M&D");
		}
		else
		{
			translatedInstructions.add("M = D|M");
		}

		return translatedInstructions;
	}

	private Vector<String> TranslateNot(Vector<String> instruction)
	{
		Vector<String> translatedInstructions = new Vector<String>();

		translatedInstructions.add("@sp");
		translatedInstructions.add("A = M-1");
		translatedInstructions.add("M = !M");

		return translatedInstructions;
	}
}