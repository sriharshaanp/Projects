import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.parser.lexparser.LexicalizedParser;
import edu.stanford.nlp.process.DocumentPreprocessor;
import edu.stanford.nlp.trees.Tree;


public class ParserClassify {

	public static void main(String[] args) {
		String successParserModel = null;
		String nonsuccessParserModel = null;
		String testfilename = null;
		String outfilename = null;
		String foldername = null;
		for (int argIndex = 0; argIndex < args.length; ) {
			if (args[argIndex].equalsIgnoreCase("-succModel")) {
				successParserModel = args[argIndex + 1];
				argIndex += 2;
			}else if(args[argIndex].equalsIgnoreCase("-nonsuccModel")){
				nonsuccessParserModel = args[argIndex+1];
				argIndex += 2;
			}else if(args[argIndex].equalsIgnoreCase("-file")){
				testfilename = args[argIndex+1];
				argIndex += 2;
			}
			else if(args[argIndex].equalsIgnoreCase("-outfile")){
				outfilename = args[argIndex+1];
				argIndex += 2;
			}
			else if(args[argIndex].equalsIgnoreCase("-folder")){
				foldername = args[argIndex+1];
				argIndex += 2;
			}
			else{
				System.err.println("Unknown argument " + args[argIndex + 1]);
				throw new IllegalArgumentException("Unknown argument " + args[argIndex + 1]);
			}
		}
		
		if (successParserModel == null || nonsuccessParserModel == null || (testfilename == null && foldername ==null) || outfilename == null){
			System.err.println("Invalid Arguments!!");
			return;
		}
		
		LexicalizedParser lp_succ = LexicalizedParser.loadModel(successParserModel);
		LexicalizedParser lp_unsucc = LexicalizedParser.loadModel(nonsuccessParserModel);
		
		FileWriter fout = null;
		try {
			fout = new FileWriter(outfilename,true);
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		
		PrintWriter pout = new PrintWriter(new BufferedWriter(fout));
		
		if (foldername != null){
			//Process all files in a folder
			File[] files = (new File(foldername)).listFiles();

			if (files == null || files.length ==0) {
				System.out.println("Error! No files in Folder " + foldername);
			}
			else{
				
				for (File file : files){
					double totalParseScoreSucc = getTotalParseScore(file.getAbsolutePath(), lp_succ);
					double totalParseScoreNonSucc = getTotalParseScore(file.getAbsolutePath(), lp_unsucc);
					if(totalParseScoreSucc > totalParseScoreNonSucc){
						pout.println(1);
					}
					else{
						pout.println(0);
					}
				}
			}
		}else if(testfilename != null){
			double totalParseScoreSucc = getTotalParseScore(testfilename, lp_succ);
			double totalParseScoreNonSucc = getTotalParseScore(testfilename, lp_unsucc);
			if(totalParseScoreSucc > totalParseScoreNonSucc){
				pout.println(1);
			}
			else{
				pout.println(0);
			}
			
		}else{
			System.err.println("Invalid file/foldername!");
		}
		
		pout.close();
		try {
			fout.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static double getTotalParseScore(String filename,
			LexicalizedParser lp) {

		double score = 0;

		// You could also create a tokenizer here (as below) and pass it
		// to DocumentPreprocessor
		for (List<HasWord> sentence : new DocumentPreprocessor(filename)) {
			Tree parse = lp.parse(sentence);
			score += parse.score();
			//outBuffer.append(parseString).append('\n');
		}
		return score;
	}
}
