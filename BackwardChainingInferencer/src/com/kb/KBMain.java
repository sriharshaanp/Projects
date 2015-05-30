/**
 * 
 */
package com.kb;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Stack;

/**
 * @author sriharsha
 *
 */
public class KBMain {
	static Map<KB,Boolean> kbList = new HashMap<KB,Boolean>();
	static KBMain kbMain = new KBMain();
	static String globalProofValues;
	static String globalX;
	/**
	 * 
	 */
	public KBMain() {

	}
	


	//1. Just check if there is a conclusion . If present then simply put its premises on the stack 
		//2. Then taking the premises keep checking in the KB whether you get another conclusion and put that in the stack too
		//3. If the premise leads to a fact then we can backtrack and say that particular path is true
		private static boolean checkResult(List<Map<String, String>> allFacts) {
			boolean proved = false;
			boolean flag = false;
			if(!stack.empty()){
			String topOfTheStack = stack.pop();
			System.out.println("top"+topOfTheStack);
			if(checkForFacts(topOfTheStack, allFacts)){
				return true;
			}else{
				for(Map.Entry<KB,Boolean> currentKB:kbList.entrySet())//for(int i=0;i<(allKnowledgeSentences.size()+1);i++)
				{
					if(!currentKB.getValue()){
						// Setting this if the KB has already been considered
						currentKB.setValue(false);
						if(currentKB.getKey().getConclusionLiterals()!=null){
							String conclusion = currentKB.getKey().getConclusionLiterals().get(topOfTheStack.split("-")[0]);
							System.out.println("conclusion :"+conclusion);
							//Scenario where there is an "x"
							if(conclusion!=null){
							if(conclusion.contains("x")){
								
									checkValue = checkForUnifiedValue(topOfTheStack.split("-")[1],conclusion);
									Boolean checResultRet = true;
									if(!checkValue.equals("")){
										for(Map.Entry<String,String> currentKBPremise:currentKB.getKey().getPremiseLiterals().entrySet()){
											stack.push(createNewQuery(currentKBPremise,globalX));
											System.out.println("New Query To be Inferred "+createNewQuery(currentKBPremise,globalX));
											checResultRet = checResultRet && checkResult(allFacts);
											if(!checResultRet){
												kbList.remove(currentKB);
												kbList.put(currentKB.getKey(),false);
												flag = false;
												break;
											}else{
												flag = true;
											}
										}
										if(flag == true){
											break;
										}
									}else{
										currentKB.setValue(false);
										continue;
									}
								}else{
									//Scenario where there is a constant
									Boolean checResultRet = true;
									if(topOfTheStack.split("-")[1].equals(conclusion)){
										for(Map.Entry<String,String> currentKBPremise:currentKB.getKey().getPremiseLiterals().entrySet()){
											if(globalX!=null){
												stack.push(currentKBPremise.getKey()+"-"+currentKBPremise.getValue().replaceAll("x", globalX));
												System.out.println("New Query To be Inferred "+currentKBPremise.getKey()+"-"+currentKBPremise.getValue().replaceAll("x", globalX));
											}else{
												stack.push(currentKBPremise.getKey()+"-"+currentKBPremise.getValue());
												System.out.println("New Query To be Inferred "+currentKBPremise.getKey()+"-"+currentKBPremise.getValue());
											}
											
											checResultRet = checResultRet && checkResult(allFacts);
											if(!checResultRet){
												/*kbList.remove(currentKB);
												kbList.put(currentKB.getKey(),false);*/
												flag = false;
												break;
											}else{
												flag = true;
											}
										}
										if(flag == true){
											break;
										}
									}else{
										if(topOfTheStack.split("-")[1].contains(",")){
											currentKB.setValue(false);
											if(!conclusion.contains(",")){
												return false;
											}else{
												String conclusionWithX1 = conclusion.split(",")[0];
												String conclusionWithX2 = conclusion.split(",")[1];
												String topString1 = topOfTheStack.split("-")[1].split(",")[0];
												String topString2 = topOfTheStack.split("-")[1].split(",")[1];
												if(conclusionWithX1.equals(topString1)){
													if(topString2.equals("x")){
														stack.push(topOfTheStack.split("-")[0]+"-"+topOfTheStack.split("-")[1].replace("x", conclusionWithX2));
														System.out.println("New Query To be Inferred "+topOfTheStack.split("-")[0]+"-"+topOfTheStack.split("-")[1].replace("x", conclusionWithX2));
														checResultRet = checResultRet && checkResult(allFacts);
														if(!checResultRet){
															return false;
														}else{
															return true;
														}
													}
												}else if(conclusionWithX2.equals(topString2)){
													if(topString1.equals("x")){
														stack.push(topOfTheStack.split("-")[0]+"-"+topOfTheStack.split("-")[1].replace("x", conclusionWithX1));
														System.out.println("New Query To be Inferred "+topOfTheStack.split("-")[0]+"-"+topOfTheStack.split("-")[1].replace("x", conclusionWithX1));
														checResultRet = checResultRet && checkResult(allFacts);
														if(!checResultRet){
															return false;
														}else{
															return true;
														}
													}
												}
											}
										}else{
											return false;
										}
										return false;
									}
								}
							}else{
								currentKB.setValue(false);
								/*kbList.remove(currentKB.getKey());
							kbList.put(currentKB.getKey(),false);*/
								continue;
							}
						}else{
							currentKB.setValue(false);
							/*kbList.remove(currentKB.getKey());
							kbList.put(currentKB.getKey(),false);*/
							continue;
						}
					}else{
						currentKB.setValue(false);
						/*kbList.remove(currentKB.getKey());
						kbList.put(currentKB.getKey(),false);*/
						continue;
					}
					if(flag == false){
						Boolean asdas =false;
						for(Map.Entry<KB,Boolean> currentKB1:kbList.entrySet()){
							if(!currentKB1.getValue()){
								if(currentKB1.getKey().getConclusionLiterals()!=null){
									String conclusion = currentKB1.getKey().getConclusionLiterals().get(topOfTheStack.split("-")[0]);
									if(conclusion!=null){
										asdas = true;
										break;
									}
								}
							}
						}
						if(asdas){
							continue;
						}else{
							return false;
						}
					}
				}
				
			}
			}
			return flag;
		}
		
	private static String checkForUnifiedValue(String unifiedValue,String conclusion) {
		if(unifiedValue.contains(","))
		{
			String conclusionPartA=conclusion.split(",")[0];
			String conclusionPartB=conclusion.split(",")[1];
			if(conclusion.contains("x")){
				if(conclusionPartA.equals("x") && conclusionPartB.equals("x"))
				{
					if(unifiedValue.split(",")[0].equals(unifiedValue.split(",")[1]))
					{
						checkValue=unifiedValue.split(",")[0];
					}else{
						return null;	
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
						return null;	
					}
			}else{
				
			}
		}else{
			checkValue=unifiedValue;
		}
		return checkValue;
	}

	private void getPredicateSymbols(String value,Map<String,String> predicateMap){
		int startIndex = value.indexOf("(");
		int endIndex = value.indexOf(")");
		predicateMap.put(value.substring(0, startIndex),value.substring(startIndex+1, endIndex));
	}

	private void constructKB(String clause){
		KB kb = new KB();
		Map<String,String> factsLiteralMap = new HashMap<String,String>();
		Map<String,String> premiseLiteralsMap = new HashMap<String,String>();
		Map<String,String> conclusionLiteralsMap = new HashMap<String,String>();
		// Check if the clause contains implication
		if(clause.contains("=>")){
			//System.out.println("The Premise");
			String[] implication = clause.split("=>");
			//Check if there are any conjuncts
			String premise = implication[0];

			if(premise.contains("&")){
				String[] premiseLiterals = premise.split("&"); 
				for(int i =0;i<premiseLiterals.length;i++){
					getPredicateSymbols(premiseLiterals[i],premiseLiteralsMap);
					//System.out.println(premiseLiterals[i]);
				}
			}else{
				getPredicateSymbols(premise,premiseLiteralsMap);
				//premiseLiteralsList.add(premise);
				//System.out.println(premise);
			}
			kb.setPremiseLiterals(premiseLiteralsMap);
			//System.out.println("The Conclusion for the premise");
			String conclusion = implication[1];

			if(conclusion.contains("&")){
				String[] conclusionLiterals = conclusion.split("&");
				for(int i =0;i<conclusionLiterals.length;i++){
					getPredicateSymbols(conclusionLiterals[i],conclusionLiteralsMap);
					//conclusionLiteralsList.add(conclusionLiterals[i]);
					//System.out.println(conclusionLiterals[i]);
				}
			}else{
				getPredicateSymbols(conclusion,conclusionLiteralsMap);
				//conclusionLiteralsList.add(conclusion);
				//System.out.println(conclusion);
			}
			kb.setConclusionLiterals(conclusionLiteralsMap);
		}else{
			//System.out.println("The Facts");
			//These are only applicable to the facts
			String facts = clause;

			//Check if there are any Literals
			if(facts.contains("&")){
				String[] factsLiterals = facts.split("&"); 
				for(int i =0;i<factsLiterals.length;i++){
					getPredicateSymbols(factsLiterals[i],factsLiteralMap);
					//factsLiteralList.add(factsLiterals[i]);
					//System.out.println(factsLiterals[i]);
				}
			}else{
				getPredicateSymbols(facts,factsLiteralMap);
				//factsLiteralList.add(facts);
				//System.out.println(facts);
			}
			kb.setFactLiterals(factsLiteralMap);
		}
		kbList.put(kb,false);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		FileData fileData = Utilities.readFromFile("input3.txt");
		
		List<String> clauses = fileData.getKbSentences();
		String proofLiteral = fileData.getToBeProved();
		
		for(String implicationClause : clauses){
			kbMain.constructKB(implicationClause);
		}
		/*for(KB kb : kbMain.kbList){
			if(kb.getPremiseLiterals()!=null){
				System.out.println(" Premises Literals ");
				for (Map.Entry<String, String> entry : kb.getPremiseLiterals().entrySet())
				{
					System.out.println(entry.getKey() + "/" + entry.getValue());
				}
			}
			if(kb.getConclusionLiterals()!=null){
				System.out.println(" Conclusion Literals ");
				for (Map.Entry<String, String> entry : kb.getConclusionLiterals().entrySet())
				{
					System.out.println(entry.getKey() + "/" + entry.getValue());
				}
			}

			if(kb.getFactLiterals()!=null){
				System.out.println(" Facts Literals ");
				for (Map.Entry<String, String> entry : kb.getFactLiterals().entrySet())
				{
					System.out.println(entry.getKey() + "/" + entry.getValue());
				}
			}
			System.out.println("*******************************************************");
		}*/
		List<Map<String,String>> allFacts=new ArrayList<Map<String,String>>();
		for(Entry<KB, Boolean> proofValue:kbMain.kbList.entrySet())
		{
			if(proofValue.getKey().getFactLiterals()!=null)
			{	
				allFacts.add(proofValue.getKey().getFactLiterals());
			}
		}

		//Method to check for the result using backward chaining implementing the DFS
		Map<String,String> proofMap = new HashMap<String,String>();
		kbMain.getPredicateSymbols(proofLiteral,proofMap);
		String proofValues = new String();
		for(Map.Entry<String,String> proofValue:proofMap.entrySet()){
			proofValues = proofValue.getKey()+"-"+proofValue.getValue();
		}
		globalProofValues = proofValues;
		stack.push(proofValues);
		//valueKB = kbMain.kbList;
		/* Start the backward chaining logic */
		System.out.println(kbMain.checkResult(allFacts));
		//System.out.println(kbMain.getPredicateSymbols(proofLiteral));
	}

	static Stack<String> stack = new Stack<String>();
	static int counter =1;
	static List<KB> valueKB = new ArrayList<KB>();
	static String checkValue = new String();
	
	

	/*private static KB getKBUnderConsideration(String proofLiteral) {
		KB kbUnderConsideration = new KB();
		for(KB currentKB:kbMain.kbList)
		{
			if(currentKB.getConclusionLiterals()!=null)
			{
				if(!currentKB.getConclusionLiterals().isEmpty()){
					String conclusionValue = currentKB.getConclusionLiterals().get(proofLiteral.split("-")[0]);
					if(conclusionValue!=null)
					{
						if(kbMain.checkKnowledgeBaseSentenceValidity(currentKB.getConclusionLiterals().get(proofLiteral.split("-")[0]),proofLiteral.split("-")[1]))
						{	
							kbUnderConsideration=currentKB;
							if(kbUnderConsideration.getConclusionLiterals()!=null){
								kbMain.kbList.remove(currentKB);
								break;	
							}
						}
					}
				}
			}
		}
		return kbUnderConsideration;
	}*/

	public static String createNewQuery(Entry<String, String> premiseKeyValue, String checkValue2){
		if(globalX!=null){
			return premiseKeyValue.getKey()+"-"+premiseKeyValue.getValue().replaceAll("x", globalX);
		}else{
			return premiseKeyValue.getKey()+"-"+premiseKeyValue.getValue().replaceAll("x", checkValue);
		}
	}
	public boolean checkKnowledgeBaseSentenceValidity(String KnowledgeBaseSentenceConclusionWithX,String unifiedValue)
	{
		boolean valid=false;
		if(unifiedValue.contains(","))
		{
			String firstArgOfConclusionWithX=KnowledgeBaseSentenceConclusionWithX.split(",")[0];
			String secondArgOfConclusionWithX=KnowledgeBaseSentenceConclusionWithX.split(",")[1];
			if(firstArgOfConclusionWithX.equals("x") && secondArgOfConclusionWithX.equals("x"))
			{
				if(unifiedValue.split(",")[0].equals(unifiedValue.split(",")[1]))
					valid=true;
				else
					valid=false;	
			}
			else if(firstArgOfConclusionWithX.equals("x"))
			{
				if(unifiedValue.split(",")[1].equals(secondArgOfConclusionWithX))	
					valid=true;
			}
			else if(secondArgOfConclusionWithX.equals("x"))
			{
				if(unifiedValue.split(",")[0].equals(firstArgOfConclusionWithX))	
					valid=true;
			}
			else
				valid=false;	
		}
		else
			valid=true;	
		return valid;
	}
	private static boolean checkForFacts(String string, List<Map<String, String>> allFacts) {
		if(!string.split("-")[1].contains("x")){
			for(Map<String,String> currentSetOfFacts: allFacts){
				for (Map.Entry<String, String> entry : currentSetOfFacts.entrySet())
				{
					
					if(string.equals(entry.getKey()+"-"+entry.getValue())){
						return true;
					}
				}
			}
		}else{
			for(Map<String,String> currentSetOfFacts: allFacts){
				for (Map.Entry<String, String> entry : currentSetOfFacts.entrySet())
				{
					if(string.split("-")[0].equals(entry.getKey().toString())){
						if(string.split("-")[1].contains(",")){
							String factsWithX1 = entry.getValue().split(",")[0];
							System.out.println("factsWithX1 : "+factsWithX1);
							String factsWithX2 = entry.getValue().split(",")[1];
							System.out.println("factsWithX2 : "+factsWithX2);
							String topString1 = string.split("-")[1].split(",")[0];
							String topString2 = string.split("-")[1].split(",")[1];
							if(factsWithX1.equals(topString1)){
								if(topString2.equals("x")){
									globalX = factsWithX2;
									return true;
								}
							}else if(factsWithX2.equals(topString2)){
								if(topString1.equals("x")){
									globalX = factsWithX1;
									return true;
								}
							}
						}else{
							globalX = entry.getValue().toString();
							return true;
						}
					}
						
				}
			}
		}
		return false;
	}
}
