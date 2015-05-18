/**
 * 
 */
package iiw.pojo;

import java.io.Serializable;
import java.sql.Connection;

/**
 * @author sri
 *
 */
/**
 *  A Pojo that holds only the data about the caches
 *  
 * 	@author Sri Harsha
 *
 */
public class ApplicationData implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/* Connection for the DB */
	private Connection connection;
	
	/* Meta-Data Table */
	private String metaDataTable;

	public Connection getConnection() {
		return connection;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	public String getMetaDataTable() {
		return metaDataTable;
	}

	public void setMetaDataTable(String metaDataTable) {
		this.metaDataTable = metaDataTable;
	}
	
	
}
