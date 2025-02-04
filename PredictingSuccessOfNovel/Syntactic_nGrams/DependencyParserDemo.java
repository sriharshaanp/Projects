
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
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
 * Demonstrates how to first use the tagger, then use the NN dependency
 * parser. Note that the parser will not work on untagged text.
 *
 * @author Jon Gauthier
 * Modifications made by Sri Harsha : Made the entire process multi-threaded and formatted the final output accodring to out project needs
 */
class MyDParser implements Runnable{
	String  fileName;
	private Thread t;
	private static int numberOfActiveThreads;
	static MaxentTagger tagger;
	static DependencyParser parser;
	String threadName;

	public MyDParser(String threadName,String fileName,MaxentTagger mx, DependencyParser dp){
		if(tagger==null||parser==null){
			tagger = mx;
			parser = dp;
		}
		this.fileName = fileName;
		this.threadName = threadName;
	}

	@Override
	public void run() {
		long startTime = System.currentTimeMillis();
		System.out.println("Start Time :"+startTime);
		//Instantiate the BufferedReader Class
		try {
			BufferedReader bufferReader = new BufferedReader(new FileReader(fileName));
			BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("./final_processed/"+fileName.substring(fileName.lastIndexOf("/"))));
			String line;

			while ((line = bufferReader.readLine()) != null)   {

				DocumentPreprocessor tokenizer = new DocumentPreprocessor(new StringReader(line));
				for (List<HasWord> sentence : tokenizer) {
					List<TaggedWord> tagged = tagger.tagSentence(sentence);
					GrammaticalStructure gs = parser.predict(tagged);
					Collection<TypedDependency> list = gs.allTypedDependencies();
					StringBuffer sb = new StringBuffer();
					for(TypedDependency value : list){
						sb.append(value+",;;");
					}
					bufferedWriter.write(sb.toString()+"\n");
					bufferedWriter.flush();

					//System.out.println(sb.toString());
				}
			}
			bufferReader.close();
			bufferedWriter.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		synchronized (fileName) {
			numberOfActiveThreads = numberOfActiveThreads-1;
		}
		long endOfInsertTime = System.currentTimeMillis();
		System.out.println("End Of Insert Time :"+endOfInsertTime);
		long diff = endOfInsertTime-startTime;
		System.out.println("Difference :"+diff);

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

}

public class DependencyParserDemo{
	public static void main(String[] args) {
		String modelPath = "english_SD.gz";
		String taggerPath = "english-left3words-distsim.tagger";
		String folderPath = "./Processed/";//new String();
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
				MyDParser m1 = new MyDParser("Thread-"+child.getName(),child.getAbsolutePath(),tagger,parser);
				m1.start();
			}
		}
	}
}
