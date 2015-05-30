/**
 * 
 */
package usc.edu;

import usc.edu.search.BFS;
import usc.edu.search.DFS;
import usc.edu.search.SearchAlgo;
import usc.edu.search.UniformCostSearch;
import usc.edu.utilities.FileData;
import usc.edu.utilities.Utilities;

/**
 * @author sriharsha
 *
 */
public class AppMain {

	private Utilities utils;
	private SearchAlgo searchAlgo;

	public AppMain() {
		utils = new Utilities();
	}

	public static void main(String[] args) {
		AppMain appMain = new AppMain();
		int len = 0;
		if (args[0] == null) {
			System.out.println("Arguments must be specified");
			System.exit(0);
		}
		while (len < args.length) {
			FileData fileData = new FileData();
			appMain.utils.readFromFile(args[len], fileData);
			switch (fileData.getExampleNumber()) {
			case 1:
				// BFS
				appMain.searchAlgo = new BFS();
				fileData.setVisitedMatrix(new int[fileData.getNumberOfNodes()]);
				appMain.searchAlgo.startSearch(fileData);
				len++;
				break;
			case 2:
				// DFS
				appMain.searchAlgo = new DFS();
				fileData.setVisitedMatrix(new int[fileData.getNumberOfNodes()]);
				appMain.searchAlgo.startSearch(fileData);
				len++;
				break;
			case 3:
				// UCS
				appMain.searchAlgo = new UniformCostSearch();
				fileData.setVisitedMatrix(new int[fileData.getNumberOfNodes()]);
				appMain.searchAlgo.startSearch(fileData);
				len++;
				break;
			}
		}
	}

}
