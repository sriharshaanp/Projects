
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.Collection;
import java.util.List;

import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.ling.TaggedWord;
import edu.stanford.nlp.parser.nndep.DependencyParser;
import edu.stanford.nlp.process.DocumentPreprocessor;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;
import edu.stanford.nlp.trees.GrammaticalStructure;
import edu.stanford.nlp.trees.TypedDependency;


/**
 * This is a program to get the feature vector 
 * @author Sri Harsha
 *
 */
class FeatureEngineer implements Runnable{
	String  fileName;
	private Thread t;
	private static int numberOfActiveThreads;
	static MaxentTagger tagger;
	static DependencyParser parser;
	String threadName;
	BufferedWriter bufferedWriter = null;
	public FeatureEngineer(String threadName,String fileName,MaxentTagger mx, DependencyParser dp){
		if(tagger==null||parser==null){
			tagger = mx;
			parser = dp;

		}
		try {
			bufferedWriter = new BufferedWriter(new FileWriter("features_FIC.txt",true));
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.fileName = fileName;
		this.threadName = threadName;
	}

	@Override
	public void run() {
		/*long startTime = System.currentTimeMillis();
		System.out.println("Start Time :"+startTime);*/
		//Instantiate the BufferedReader Class
		try {
			BufferedReader bufferReader = new BufferedReader(new FileReader(fileName));

			String line;
			int totalNumberOfSyllables = 0,totalNumberOfWords=0,totalNumberOfLines = 0,nominalForms=0,verbFormDitributions=0,adjectiveFormDist = 0;
			while ((line = bufferReader.readLine()) != null){
				DocumentPreprocessor tokenizer = new DocumentPreprocessor(new StringReader(line));
				totalNumberOfLines += 1;
				for (List<HasWord> sentence : tokenizer) {
					List<TaggedWord> tagged = tagger.tagSentence(sentence);
					/*GrammaticalStructure gs = parser.predict(tagged);
					Collection<TypedDependency> list = gs.allTypedDependencies();*/
					StringBuffer sb = new StringBuffer();

					for(TaggedWord value : tagged){
						totalNumberOfSyllables +=countSyllables(value.toString().substring(0,value.toString().lastIndexOf("/")));
						String temp = value.toString().substring(value.toString().lastIndexOf("/")+1);
						temp=temp.trim();
						if(temp.matches("V.*G")){
							nominalForms+=1;
						}else if(temp.matches("V.*")&&!temp.matches("V.*G")){//temp.equals("VB")||temp.equals("VBZ")||temp.equals("VBD")||temp.equals("VBN")||temp.equals("VBP")){
							verbFormDitributions+=1;
						}else if(temp.matches("JJ.*")){
							adjectiveFormDist+=1;
						}
					}
					totalNumberOfWords +=tagged.size();

					//System.out.println(sb.toString());
				}
			}
			double esi = 206.835 - 1.015 * (totalNumberOfWords/totalNumberOfLines) - 84.6 * (totalNumberOfSyllables/totalNumberOfWords);
			String rich = getRichnessFactor(fileName);
			String m = fileName.substring(fileName.lastIndexOf("/")).replace("/processed_FIC-0-", "");
			m = m.replace("/processed_FIC-1-", "");
			System.out.println(m+",;Esi:"+esi+",;AvgNo:"+(totalNumberOfWords/totalNumberOfLines)+",;YulesNumber:"+rich
					+",;VerbForm:"+verbFormDitributions+",;NominalForm:"+nominalForms+",;AdjDist:"+adjectiveFormDist+",;label:"+getLabel(fileName.substring(fileName.lastIndexOf("/")))+"\n");
			bufferedWriter.append(m+",;Esi:"+esi+",;AvgNo:"+(totalNumberOfWords/totalNumberOfLines)+",;YulesNumber:"+rich
					+",;VerbForm:"+verbFormDitributions+",;NominalForm:"+nominalForms+",;AdjDist:"+adjectiveFormDist+",;label:"+getLabel(fileName.substring(fileName.lastIndexOf("/")))+"\n");
			bufferedWriter.flush();
			bufferReader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		synchronized (bufferedWriter) {
			numberOfActiveThreads = numberOfActiveThreads-1;
		}
		/*long endOfInsertTime = System.currentTimeMillis();
		System.out.println("End Of Insert Time :"+endOfInsertTime);
		long diff = endOfInsertTime-startTime;
		System.out.println("Difference :"+diff);*/

	}

	private String getLabel(String fileName2) {
		if(fileName2.matches("/processed_.*-0-.*")){
			return "0";
		}else{
			return "1";
		}
	}

	private String getRichnessFactor(String fileName2) throws IOException {
		String pythonScriptPath = "/home/sri/workspace/Program/StatisticalStylometryFeatureEngineering/richness_of_text.py";
		String[] cmd = new String[3];
		cmd[0] = "python3";
		cmd[1] = pythonScriptPath;
		cmd[2] = fileName;
		// create runtime to execute external command
		Runtime rt = Runtime.getRuntime();
		Process pr = rt.exec(cmd);

		// retrieve output from python script
		BufferedReader bfr = new BufferedReader(new InputStreamReader(pr.getInputStream()));
		String line1 = "";
		while((line1 = bfr.readLine()) != null) {
			// display each output line form python script
			return line1;
		}
		return null;
	}

	public void start ()
	{
		numberOfActiveThreads = numberOfActiveThreads+1;
		while(numberOfActiveThreads>1){
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println("Starting " +  threadName );
		if (t == null)
		{
			t = new Thread (this, threadName);
			t.start ();
		}
	}

	public int countSyllables(String word) {
		int count = 0;
		word = word.toLowerCase();
		for (int i = 0; i < word.length(); i++) {
			if (word.charAt(i) == '\"' || word.charAt(i) == '\'' || word.charAt(i) == '-' || word.charAt(i) == ',' || word.charAt(i) == ')' || word.charAt(i) == '(') {
				word = word.substring(0,i)+word.substring(i+1, word.length());
			}
		}
		boolean isPrevVowel = false;
		for (int j = 0; j < word.length(); j++) {
			if (word.contains("a") || word.contains("e") || word.contains("i") || word.contains("o") || word.contains("u")) {
				if (isVowel(word.charAt(j)) && !((word.charAt(j) == 'e') && (j == word.length()-1))) {
					if (isPrevVowel == false) {
						count++;
						isPrevVowel = true;
					}
				} else {
					isPrevVowel = false;
				}
			} else {
				count++;
				break;
			}
		}
		return count;
	}

	public boolean isVowel(char c) {
		if (c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u') {
			return true;
		} else {
			return false;
		}
	}

}

public class FeatureEngineeringDemo{
	public static void main(String[] args) {
		String modelPath = "english_SD.gz";
		String taggerPath = "english-left3words-distsim.tagger";
		String folderPath = "./Processed_Fiction/";//new String();
		//String folderPath = "./files/";
		for (int argIndex = 0; argIndex < args.length; ) {
			switch (args[argIndex]) {
			case "-f":
				folderPath = args[argIndex + 1];
				argIndex += 2;
				break;
			case "-model":
				modelPath = args[argIndex + 1];
				argIndex += 2;
				break;
			default:
				throw new RuntimeException("Unknown argument " + args[argIndex]);
			}
		}
		MaxentTagger tagger = new MaxentTagger(taggerPath);
		DependencyParser parser = DependencyParser.loadFromModelFile(modelPath);

		File dir = new File(folderPath);
		File[] directoryListing = dir.listFiles();
		if (directoryListing != null) {
			for (File child : directoryListing) {
				FeatureEngineer m1 = new FeatureEngineer("Thread-"+child.getName(),child.getAbsolutePath(),tagger,parser);
				m1.start();
			}
		}
	}
}