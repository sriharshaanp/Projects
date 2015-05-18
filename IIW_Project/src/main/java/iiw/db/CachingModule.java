/**
 * 
 *//*
package iiw.db;

import org.apache.log4j.Logger;

*//**
 * @author Sri Harsha
 *
 *//*
public class CachingModule{
	final static Logger logger = Logger.getLogger(CachingModule.class);
	private DBManager dBManager;
	 Ensuring singleton factory 
	static private CachingModule cachingModule= new CachingModule();

	private CachingModule(){
		dBManager = new DBManagerImplementation();
		try{
			logger.info("The initialization phase has started");
			if(dBManager.initialize()){
				logger.info("The initialization phase has successfully ended");	
			}else{
				logger.info("The initialization phase has unsuccessfully ended");
				System.exit(0);
			}
		}catch(Exception e){
			logger.error(e.getMessage());
		}
	}

	public static CachingModule getSingletonFactory() {
		return cachingModule;
	}
	
	

	*//**
	 * @param args
	 *//*
	public static void main(String[] args) {
		CachingModule cachingModule = CachingModule.getSingletonFactory();
		Loops through the various queries in the meta-data table and queries and updates them.
		while(true){
			DBManager cm = cachingModule.getCacheManager();
		}
	}

	public DBManager getCacheManager() {
		return dBManager;
	}

	public void setCacheManager(DBManager dBManager) {
		this.dBManager = dBManager;
	}

}
*/