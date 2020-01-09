import java.io.*;

public class LexicalAnalyzer 
{
	public static char[] lexeme = new char[100];
	public static String charClass, lexemeStr="", expressions="", nextToken;
	public static char nextChar;
	public static int lexLen;
	
	public static void main(String[] args) throws IOException
	{
		BufferedReader br = new BufferedReader(new FileReader("front.txt")); 
		StringBuilder sb = new StringBuilder();
		String line = br.readLine(); // Creates a String line and stores the first line from the File into a String line
		while (line != null) 
		{
		   sb.append(line+" "); // add a line to the String Builder
		   line = br.readLine(); // stores a line from the File into the String line
		}
		expressions = sb.toString(); // Gets the string out of String Builder into the String expressions
		getChar(); // Gets first char to initiate the process
		while (!expressions.isEmpty()) // Goes thru all the chars until no chars left in the string
		{
			lex();
		}
	}
	public static String lex() // Gives output in a form of a String line with the break down of lexeme of each item in the expression
	{
		lexLen = 0;
		getNonBlank();
		switch (charClass) 
		{
			case "LETTER": // Parse identifiers
				addChar();
				getChar();
				while (charClass == "LETTER" || charClass == "DIGIT")
				{
					addChar();
					getChar();
				}
				nextToken = "IDENT";
				break;
			case "DIGIT": // Parse integer literals
				addChar();
				getChar();
				while (charClass == "DIGIT")
				{
					addChar();
					getChar();
				}
				nextToken = "INT_LIT";
				break;
			case "UNKNOWN": // Parentheses and operators
				lookup(nextChar);
				getChar();
				break;
			case "EOF": // EOF
				nextToken = "EOF";
				lexeme[0] = 'E';
				lexeme[1] = 'O';
				lexeme[2] = 'F';
				lexeme[3] = 0;
				break;
		} // End of switch
		for (int i=0; i<lexLen; i++)
		{
			lexemeStr = lexemeStr+""+lexeme[i];
		}
		System.out.println("Next token is:"+nextToken+", Next lexeme is "+lexemeStr);
		lexemeStr="";
		return nextToken;
	} // End of function lex
	public static void addChar() // Adds a char into the array of chars
	{
		if (lexLen <= 98)
		{
			lexeme[lexLen] = nextChar;
			lexLen++;
		}
		else
			System.out.println("Error - lexeme is too long \n");
	}
	public static void getChar() // getChar - a function to get the next character of input and determine its character class
	{
		if (expressions.length()>0) 
		{
			nextChar = expressions.charAt(0);
			//System.out.println("System set nextChar to "+nextChar); // Reads a nextChar to test the code
			expressions = expressions.substring(0, 0) + expressions.substring(1);
			if ( (nextChar >= 'a' && nextChar <= 'z') || (nextChar >= 'A' && nextChar <= 'Z'))
				charClass = "LETTER";
			else if (nextChar >= '0' && nextChar <= '9')
				charClass = "DIGIT";
			else 
				charClass = "UNKNOWN";
		}
		else
			charClass = "EOF";
	}
public static String lookup(char ch) // Looks up a correct expression for each token
{
	switch (ch) 
	{
	case '(':
		addChar();
		nextToken = "LEFT_PAREN";
		break;
	case ')':
		addChar();
		nextToken = "RIGHT_PAREN";
		break;
	case '+':
		addChar();
		nextToken = "ADD_OP";
		break;
	case '-':
		addChar();
		nextToken = "SUB_OP";
		break;
	case '*':
		addChar();
		nextToken = "MULT_OP";
		break;
	case '/':
		addChar();
		nextToken = "DIV_OP";
		break;
	default:
		addChar();
		nextToken = "EOF";
		break;
		}
		return nextToken;
	}
	public static void getNonBlank() // getNonBlank - a function to call getChar until it returns a non-whitespace character
	{
		while (nextChar==' ')
			getChar();
	}
}