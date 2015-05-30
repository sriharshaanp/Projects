/**
 * 
 */
package com.kb;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Sri Harsha
 *
 */
public class Utilities {
	
	Boolean success = false;
	
	/*public boolean backwardChaining(List<KnowledgeBaseSentence> allKnowledgeSentences,List<String> allFacts,String queryTobeInferred)
	{
		if(allFacts.contains(queryTobeInferred))
			return true;
		else
		{
		if(!predicateStack.empty())
		{
		String queryToBeProved=predicateStack.pop();
		
			String unifiedValue=queryToBeProved.split("/")[1];
		
		KnowledgeBaseSentence knowledgeBaseSentenceUnderConsideration=null;
		int j=0;
		int k=allKnowledgeSentences.size();
		for(int i=0;i<(allKnowledgeSentences.size()+1);i++)
		{
			if(j<allKnowledgeSentences.size())
			{	
			KnowledgeBaseSentence tempSentence=allKnowledgeSentences.get(j);
			if(tempSentence.getConclusionArguments()!=null)
			{
				
			if(tempSentence.getConclusionArguments().get(queryToBeProved.split("/")[0])!=null)
			{
				if(checkKnowledgeBaseSentenceValidity(tempSentence.getConclusionArguments().get(queryToBeProved.split("/")[0]),unifiedValue))
				{	
				knowledgeBaseSentenceUnderConsideration=allKnowledgeSentences.remove(j);
				ValueOfX=unify(knowledgeBaseSentenceUnderConsideration.getConclusionArguments().get(queryToBeProved.split("/")[0]),unifiedValue);
				System.out.println("{x="+ValueOfX+"}");
				if(ValueOfX==null)
				continue;
				for(Map.Entry<String,String> premiseKeyValue:knowledgeBaseSentenceUnderConsideration.getPremiseArguments().entrySet())
				{
					String newQueryToBeInferred;
					if(ValueOfX!="")
					{	
					newQueryToBeInferred=premiseKeyValue.getKey()+"("+premiseKeyValue.getValue().replaceAll("x", ValueOfX)+")";
					predicateStack.push(premiseKeyValue.getKey()+"/"+premiseKeyValue.getValue().replaceAll("x", ValueOfX));
					System.out.println("New Query To be Inferred "+newQueryToBeInferred);
					}
					else
					{
						System.out.println("Premise Key Value"+premiseKeyValue.getValue());
						
						newQueryToBeInferred=premiseKeyValue.getKey()+"("+premiseKeyValue.getValue()+")";
						predicateStack.push(premiseKeyValue.getKey()+"/"+premiseKeyValue.getValue());
						System.out.println("New Query To be Inferred "+newQueryToBeInferred);
							
					}
					if(backwardChaining(allKnowledgeSentences, allFacts, newQueryToBeInferred))
	                   sentenceTrueChance=true;        	//return true;
					else
					{
						sentenceTrueChance=false;
						break;
					}	
					
				}
				
				j=0;
				//break;	
				}
				else
				j++;	
			}
			else
				j++;
			}
			else
				j++;
			}
		}
		//if(knowledgeBaseSentenceUnderConsideration==null)
		if(sentenceTrueChance==false)
		return false;	
		else
		return true;	
		ValueOfX=unify(knowledgeBaseSentenceUnderConsideration.getConclusionArguments().get(queryToBeProved.split("/")[0]),unifiedValue);
		System.out.println("{x="+ValueOfX+"}");
		if(ValueOfX==null)
		return false;	
		//}
		for(Map.Entry<String,String> premiseKeyValue:knowledgeBaseSentenceUnderConsideration.getPremiseArguments().entrySet())
		{
			String newQueryToBeInferred;
			if(ValueOfX!="")
			{	
			newQueryToBeInferred=premiseKeyValue.getKey()+"("+premiseKeyValue.getValue().replaceAll("x", ValueOfX)+")";
			predicateStack.push(premiseKeyValue.getKey()+"/"+premiseKeyValue.getValue().replaceAll("x", ValueOfX));
			System.out.println("New Query To be Inferred "+newQueryToBeInferred);
			}
			else
			{
				newQueryToBeInferred=premiseKeyValue.getKey()+"("+premiseKeyValue.getValue()+")";
				predicateStack.push(premiseKeyValue.getKey()+"/"+premiseKeyValue.getValue());
				System.out.println("New Query To be Inferred "+newQueryToBeInferred);
					
			}
			if(!backwardChaining(allKnowledgeSentences, allFacts, newQueryToBeInferred))
			{
				return false;
			}
		}
		//System.out.println("HERE");
		//return true;
		}
			
		}
		
		return false;
	}*/

	public static FileData readFromFile(String fileName) {
		FileData fileData = new FileData();
		FileReader in = null;
		String bufferedLine = new String();
		int counter = 1;
		BufferedReader br = null;
		int noOfSentences =0;
		List<String> sentencesInKBList = new ArrayList<String>();
		try {
			in = new FileReader(new File(fileName));
			br = new BufferedReader(in);
			while ((bufferedLine = br.readLine()) != null) {
				switch (counter) {
				case 1:// Reading the Example to Be Executed
					fileData.setToBeProved(bufferedLine.toString());
					counter++;
					break;
				case 2:// Reading the Player
					noOfSentences = Integer.parseInt(bufferedLine);
					fileData.setNoOfSentences(noOfSentences);
					counter++;
					break;
				case 3:// Reading the Max Depth
					sentencesInKBList.add(bufferedLine.toString());
					break;
				}
			}
			fileData.setKbSentences(sentencesInKBList);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		} catch (Exception e) {
			
			e.printStackTrace();
		} finally {
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return fileData;
	}

	/**
	 * Function to print out the matrix
	 * 
	 * @param adjacencyMatrix
	 * @param numberOfNodes
	 */
	public void printMatrix(String[][] adjacencyMatrix, int numberOfNodes) {
		for (int i = 0; i < numberOfNodes; i++) {
			for (int j = 0; j < numberOfNodes; j++) {
				System.out.print("" + adjacencyMatrix[i][j] + "");
			}
			System.out.println("");
		}
	}

	/**
	 * Function to compare the alphabetical order of nodes when the cost is the
	 * same
	 * 
	 * @param fileData
	 * @param node1
	 * @param node2
	 * @return
	 */
	public boolean compareNodes(String node1, String node2) {
		if (node1.compareTo(node2) < 0) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * Function to output to the command line
	 * @param finalPathCost
	 * @param finalPath
	 * @param logPath
	 */
	public void printResult(int finalPathCost, String finalPath,
			String logPath) {
		System.out.println(logPath);
		System.out.println(finalPath);
		System.out.println(finalPathCost);
	}

	public void writeToFile(String[][] bestMatrix, int numberOfNodes) {
		FileWriter out = null;
		BufferedWriter bw = null;
		try {
			out = new FileWriter("./output.txt");
			bw = new BufferedWriter(out);
			for (int i = 0; i < numberOfNodes; i++) {
				for (int j = 0; j < numberOfNodes; j++) {
					bw.write("" + bestMatrix[i][j] + "");
				}
				bw.newLine();
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				bw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
/*	//Check if the proof query is in the facts list , then we can prove it and directly exit
	if(checkForFacts(string,allFacts)){
		return true;
	}else{
		String proofLiteral= null;
		String unifiedValue = null;
		if(!stack.empty()){
			proofLiteral = stack.pop();
			unifiedValue=proofLiteral.split("-")[1];
			 Query under consideration in the KB 
			KB kbUnderConsideration = getKBUnderConsideration(proofLiteral);
			if(kbUnderConsideration==null){
				return false;
			}
			if(kbUnderConsideration.getConclusionLiterals()!=null){
				String conclusion = kbUnderConsideration.getConclusionLiterals().get(proofLiteral.split("-")[0]);
				if(unifiedValue.contains(","))
				{
					String conclusionPartA=conclusion.split(",")[0];
					String conclusionPartB=conclusion.split(",")[1];

					if(conclusionPartA.equals("x") && conclusionPartB.equals("x"))
					{
						if(unifiedValue.split(",")[0].equals(unifiedValue.split(",")[1]))
					{
						checkValue=unifiedValue.split(",")[0];
					}else{
						return false;	
					}
				}
				else 
				if(conclusionPartA.equals("x"))
				{
					if(unifiedValue.split(",")[1].equals(conclusionPartB)){	
						checkValue=unifiedValue.split(",")[0];
					}
				}
				else if(conclusionPartB.equals("x"))
				{
					if(unifiedValue.split(",")[0].equals(conclusionPartA)){	
						checkValue=unifiedValue.split(",")[1];
					}
				}else{
					return false;	
				}
			}else{
				checkValue=unifiedValue;
			}
			}
			System.out.println("{x="+checkValue+"}");
			if(checkValue==null){
					
			}
			if(kbUnderConsideration.getPremiseLiterals()!=null){
			for(Map.Entry<String,String> premiseKeyValue:kbUnderConsideration.getPremiseLiterals().entrySet())
			{
				stack.push(createNewQuery(premiseKeyValue,checkValue));
				System.out.println("New Query To be Inferred "+createNewQuery(premiseKeyValue,checkValue));
				if(!checkResult(createNewQuery(premiseKeyValue,checkValue),allFacts))
				{
					return false;
				}
			}
			}
			return true;
		}*/

}