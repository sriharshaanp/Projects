import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.List;

import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.parser.lexparser.LexicalizedParser;
import edu.stanford.nlp.process.DocumentPreprocessor;
import edu.stanford.nlp.trees.Tree;


public class ParserCreateTreeBank {
	public static void main(String[] args) {
		String parserModel = "edu/stanford/nlp/models/lexparser/englishPCFG.ser.gz";
		String filename = "";
		String outfile = "";

		for (int argIndex = 0; argIndex < args.length; ) {
			if (args[argIndex].equalsIgnoreCase("-model")) {
				parserModel = args[argIndex + 1];
				argIndex += 2;
			}else if(args[argIndex].equalsIgnoreCase("-file")){
				filename = args[argIndex+1];
				argIndex += 2;
			}else if(args[argIndex].equalsIgnoreCase("-outfile")){
					outfile = args[argIndex+1];
					argIndex += 2;
			}
			else{
				System.err.println("Unknown argument " + args[argIndex + 1]);
				throw new IllegalArgumentException("Unknown argument " + args[argIndex + 1]);
			}
		}

		int count = 0;
		if(filename != null) count++;
		
		if (count < 1){
			System.err.println("Specify filename!");
			return;

		}

		PrintStream pout = null;
		if (outfile != ""){
			FileOutputStream fout = null;
			try {
				fout = new FileOutputStream(outfile);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			pout = new PrintStream(fout);
		}
		else{
			pout = System.out;
		}
		
		printParseTrees(parserModel, filename, pout);
		
		pout.close();

	}

	public static void printParseTrees(String parserModel, String filename, PrintStream pout) {

		LexicalizedParser lp = LexicalizedParser.loadModel(parserModel);

		StringBuilder outBuffer = new StringBuilder();
		
		// You could also create a tokenizer here (as below) and pass it
		// to DocumentPreprocessor
		int i = 1;
		for (List<HasWord> sentence : new DocumentPreprocessor(filename)) {
			Tree parse = lp.parse(sentence);
			String parseString = parse.pennString();
			pout.println(parseString+"\n");
			System.out.println("Tagged Sentence "+i++ );
			//outBuffer.append(parseString).append('\n');
		}

		//pout.println(outBuffer.toString());
		
	}

}
