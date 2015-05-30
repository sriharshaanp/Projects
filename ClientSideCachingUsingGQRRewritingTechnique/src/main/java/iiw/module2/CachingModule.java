/**
 * 
 *//*
package iiw.module2;

import java.util.List;

import org.apache.log4j.Logger;

import com.mongodb.BasicDBObject;

*//**
 * @author sri
 *
 *//*
public class CachingModule{
	final static Logger logger = Logger.getLogger(CachingModule.class);
	private CacheManager cacheManager;
	 Ensuring singleton factory 
	static private CachingModule cachingModule= new CachingModule();

	private CachingModule(){
		cacheManager = new CacheManagerImplementation();
		try{
			logger.info("The initialization phase has started");
			if(cacheManager.initialize()){
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
			CacheManager cm = cachingModule.getCacheManager();
			List<BasicDBObject> dbObject = cm.readAll(cm.cacheData.getMetaDataCollection());
		}
	}

	public CacheManager getCacheManager() {
		return cacheManager;
	}

	public void setCacheManager(CacheManager cacheManager) {
		this.cacheManager = cacheManager;
	}

}
*/