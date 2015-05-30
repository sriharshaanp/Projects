import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.jsoup.Connection;
import org.jsoup.Connection.Method;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

/**
 * 
 */

/**
 * @author sri
 *
 */
public class FreeBooksScrapper {

	/**
	 * This function creates the link to access
	 * In the key make sure you give the genre followed by the number of pages that the link seperated by a colon
	 * @return
	 * R - Raj
	 * H - Harsha
	 * M - Mohit
	 * A - Ajinkya
	 */
	private static Map<String, String> preprocess() {
		Map<String,String> genreUrls=new HashMap<String,String>();
		//genreUrls.put("ADV:15","http://manybooks.net/categories/ADV");//Drama - H
		//genreUrls.put("DRA:30","http://manybooks.net/categories/DRA");//Drama - H
		//genreUrls.put("ART:30","http://manybooks.net/categories/ART");//Art - H
		//genreUrls.put("CLA:6","http://manybooks.net/categories/CLA");//Classic -M
		//genreUrls.put("GHO:30","http://manybooks.net/categories/GHO");//Ghost Stories - M
		//genreUrls.put("FAN:14","http://manybooks.net/categories/FAN");//Fantasy -R
		//genreUrls.put("FIC:30","http://manybooks.net/categories/FIC");//Fiction and Literature -R
		//genreUrls.put("HIS:15","http://manybooks.net/categories/HIS");//History -R
		//genreUrls.put("ROM:30","http://manybooks.net/categories/ROM");//Romance -A
		//genreUrls.put("SHO:30","http://manybooks.net/categories/SHO");//Short Stories -A
		return genreUrls;
	}

	public static void main(String[] args) throws IOException {
		
		Document doc = null;
		String fileName = args[0];
		try{
			Map<String,String> genreUrls = readFile(fileName);
			
			Iterator it = genreUrls.entrySet().iterator();
			
			while (it.hasNext()) 
			{
				Map.Entry pair = (Map.Entry)it.next();
				String number_Of_Pages = pair.getKey().toString().split(":")[1];
				String genre = pair.getKey().toString().split(":")[0];
				try {
					for(int i=1;i<Integer.parseInt(number_Of_Pages);i++){
						doc = Jsoup.connect(pair.getValue().toString()+"/"+i).get();
						// Step 1 : Getting only the urls of all the book locations
						getTheBookURls(genre,doc);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				// Step 2 : Getting the info within the urls
				int count = getInfoFromBooks(genre);
				System.out.println(" Total Book info recieved : "+count);
				// Step 3 : Retrieve the actual file
				retrieveTheFile(genre);
			}
		}catch(FileNotFoundException e){
			e.printStackTrace();
		}
	}

	private static Map<String, String> readFile(String fileName) throws IOException 
	{
		BufferedReader br = new BufferedReader(new FileReader(fileName));
		Map<String,String> genreUrls = new HashMap<String, String>();
		try {
			String line = br.readLine().trim();
			
			while (line != null) {
				line=line.trim();
				String[] tokens = line.split("\\s");
				System.out.println(tokens[0] + " "+tokens[1]);
				genreUrls.put(tokens[0], tokens[1]);
				line = br.readLine();
			}
		} finally {
			br.close();
		}
		return genreUrls;
	}

	/**
	 * This function only tries to get the file
	 * @param genre
	 */
	private static void retrieveTheFile(String genre) {
		String fileUrl = null;
		int count = 0;
		try{
			BufferedReader genreFile = new BufferedReader(new FileReader(genre+".txt"));
			while((fileUrl=genreFile.readLine())!=null){
				try{
					count +=1;
					System.out.println("Getting response for "+count+" file : "+retrieveURl(fileUrl));
					Connection.Response res = Jsoup.connect("http://manybooks.net/_scripts/send.php?tid="+retrieveURl(fileUrl)+"&book=1:text:.txt:text")
							.method(Method.POST)
							.timeout(300000)
							.ignoreContentType(true)
							.execute();
					System.out.println("Started download of file : "+retrieveURl(fileUrl));
					BufferedWriter bw = new BufferedWriter(new FileWriter("file_"+retrieveURl(fileUrl)+".txt",true));
					bw.write(res.parse().getElementsByTag("body").text().toString());
					bw.flush();
					bw.close();
					System.out.println("Completed download of file : "+retrieveURl(fileUrl));
					forcedSleep(100000);
					System.out.println("*********************"+count+"*********************");
				}catch(Exception ex){
					System.out.println("Interupted download of file : "+retrieveURl(fileUrl));
					forcedSleep(100000);
					ex.printStackTrace();
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	/**
	 * Function simply returns the exact url for the book
	 * @param fileUrl
	 * @return
	 */
	private static String retrieveURl(String fileUrl) {
		fileUrl = fileUrl.replace("/titles/", "");
		fileUrl = fileUrl.replace(".html", "");
		return fileUrl;
	}

	/**
	 * This function must get the following details
	 * 1. Title of the Book
	 * 2. Author Of the Book
	 * 3. Published Date
	 * 4. Language 
	 * 5. Reading ease index
	 * 6. Downloads 
	 * 7. Excerpt of the file
	 * The file generated has +",:;" as the split for each column
	 * */
	private static int getInfoFromBooks(String genre) {
		String fileUrl = null;
		int count = 1;
		try{
			BufferedReader genreFile = new BufferedReader(new FileReader(genre+".txt"));
			BufferedWriter bw = new BufferedWriter(new FileWriter(genre+"_metadata.txt",true));
			while((fileUrl=genreFile.readLine())!=null){
				StringBuffer dataToInsert = new StringBuffer();
				Document doc = Jsoup.connect("http://manybooks.net"+fileUrl).timeout(500000).get();
				// File name acts as the key to retrieve the file 
				dataToInsert.append(retrieveURl(fileUrl)+",:;");
				// 1. Title of the Book
				dataToInsert.append(doc.getElementsByTag("title").first().text().toString()+",:;");
				List<Element> listOfBookInfo = doc.select("div.bookinfo div.title-info");
				for(Element bookInfo : listOfBookInfo){
					String currentInfoType = bookInfo.getElementsByTag("span").text().toString();
					String currentData = bookInfo.text().toString();
					if(currentInfoType.equalsIgnoreCase("Author:")){
						// 2. Author Of the Book
						currentData = currentData.replace("Author:", "");
						System.out.println(currentData);
						dataToInsert.append(currentData+",:;");
					}else if(currentInfoType.equalsIgnoreCase("Published:")){
						// 3. Published Date
						currentData = currentData.replace("Published:", "");
						System.out.println(currentData);
						dataToInsert.append(currentData+",:;");
					}else if(currentInfoType.equalsIgnoreCase("Language:")){
						// 4. Language
						currentData = currentData.replace("Language:", "");
						System.out.println(currentData);
						dataToInsert.append(currentData+",:;");
					}else if(currentInfoType.equalsIgnoreCase("Flesch-Kincaid Reading Ease:")){
						// 5. Reading ease index
						currentData = currentData.replace("Flesch-Kincaid Reading Ease:", "");
						System.out.println(currentData);
						dataToInsert.append(currentData+",:;");
					}else if(currentInfoType.equalsIgnoreCase("Downloads:")){
						// 6. Downloads
						currentData = currentData.replace("Downloads:", "");
						System.out.println(currentData);
						dataToInsert.append(currentData+",:;");
					}
				}
				dataToInsert.append(genre+",:;");
				if(!doc.select(".average").isEmpty()){
					System.out.println(doc.select(".average").first().text().toString());
					dataToInsert.append(doc.select(".average").first().text().toString()+",:;");
				}else{
					dataToInsert.append("Average : 0,:;");
				}
				if(!doc.select(".votes").isEmpty()){
					System.out.println(doc.select(".votes").first().text().toString());
					dataToInsert.append(doc.select(".votes").first().text().toString()+",:;");
				}else{
					System.out.println("0,:;");
					dataToInsert.append("0,:;");
				}
				dataToInsert.append(doc.select("#excerpt").first().text().toString());
				count +=1;
				System.out.println("######################"+count+"#######################");
				bw.write(dataToInsert.toString());
				bw.newLine();
				bw.flush();
			}
			bw.close();
			genreFile.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		return count;
	}


	private static void getTheBookURls(String genre, Document doc) {
		try{
			BufferedWriter bw = new BufferedWriter(new FileWriter(genre+".txt",true));
			List<Element> listOfBookLinks = doc.select("div.table div.grid_5.smallBook");
			for(Element bookUrl : listOfBookLinks){
				System.out.println(bookUrl.select("a").attr("href"));
				bw.append(bookUrl.select("a").attr("href"));
				bw.flush();
				bw.newLine();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	private static void forcedSleep(Integer number) {
		try {
			long sleepTime = (long) (Math.random() * number);
			System.out.println("Going to sleep for "+sleepTime+" seconds.");
			if(sleepTime>150000){
				Thread.sleep(sleepTime);
			}else{
				Thread.sleep(150000L);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
