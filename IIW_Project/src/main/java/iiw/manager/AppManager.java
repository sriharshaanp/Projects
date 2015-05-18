/**
 * 
 */
package iiw.manager;

import iiw.db.DBManager;
import iiw.db.DBManagerImplementation;
import iiw.pojo.ApplicationData;
import iiw.sparql.SparQLManager;
import iiw.sparql.SparQLManagerImpl;
import iiw.wrapper.CacheWrapper;
import iiw.wrapper.CacheWrapperImpl;

import java.util.HashMap;
import java.util.List;

/**
 * @author sri
 *
 */
public interface AppManager {
	/* Singleton Initialization of the ApplicationData */
	ApplicationData applicationData = new ApplicationData();
	
	/* DBManager */
	DBManager dbManager=new DBManagerImplementation();
	
	/* */
	CacheWrapper cacheWrapper = new CacheWrapperImpl();
	
	/* */
	SparQLManager sparQLManager = new SparQLManagerImpl(1);
	
	/* Initialization */
	boolean initialize() throws Exception;
	
	/* */
	public ApplicationData getApplicationData();
	
	/* Query Containment Process */
	public List<String> getData(String query);
	
	/* */
	public DBManager getDbManager();
	
	/* */
	public CacheWrapper getWrapper();
	
	/* */
	public SparQLManager getSparQLManager();
}
