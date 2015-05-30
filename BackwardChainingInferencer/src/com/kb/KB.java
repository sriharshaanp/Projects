/**
 * 
 */
package com.kb;

import java.util.Map;

/**
 * @author sriharsha
 *
 */
public class KB {

	private Map<String,String> premiseLiterals;
	private Map<String,String> conclusionLiterals;
	private Map<String,String> factLiterals;
	
	/**
	 * 
	 */
	public KB() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the premiseLiterals
	 */
	public Map<String, String> getPremiseLiterals() {
		return premiseLiterals;
	}

	/**
	 * @param premiseLiterals the premiseLiterals to set
	 */
	public void setPremiseLiterals(Map<String, String> premiseLiterals) {
		this.premiseLiterals = premiseLiterals;
	}

	/**
	 * @return the conclusionLiterals
	 */
	public Map<String, String> getConclusionLiterals() {
		return conclusionLiterals;
	}

	/**
	 * @param conclusionLiterals the conclusionLiterals to set
	 */
	public void setConclusionLiterals(Map<String, String> conclusionLiterals) {
		this.conclusionLiterals = conclusionLiterals;
	}

	/**
	 * @return the factLiterals
	 */
	public Map<String, String> getFactLiterals() {
		return factLiterals;
	}

	/**
	 * @param factLiterals the factLiterals to set
	 */
	public void setFactLiterals(Map<String, String> factLiterals) {
		this.factLiterals = factLiterals;
	}

}
