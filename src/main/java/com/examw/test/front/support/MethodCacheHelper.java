package com.examw.test.front.support;

import java.io.Serializable;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

import org.apache.log4j.Logger;

/**
 * 
 * @author fengwei.
 * @since 2014年9月27日 下午3:34:22.
 */
public class MethodCacheHelper {
	private static final Logger logger = Logger.getLogger(MethodCacheHelper.class);
	private Cache cache;

	/**
	 * 设置 缓存
	 * @param cache
	 * 
	 */
	public void setCache(Cache cache) {
		this.cache = cache;
	}

	public Object getCache(String targetName, String methodName, Object[] arguments) {
		String cacheKey = getCacheKey(targetName, methodName, arguments);//这里得出的是:manager.PersonManagerImpl.getList  
        Element element = cache.get(cacheKey);
        if(element == null) return null;
        if(logger.isDebugEnabled()) logger.debug(cacheKey + " 从缓存中取出...");  ;//完成cache操作  
        return element.getObjectValue();
	}
	
	public void putCache(String targetName, String methodName, Object[] arguments,Object result){
		String cacheKey = getCacheKey(targetName, methodName, arguments);//这里得出的是:manager.PersonManagerImpl.getList  
		if(logger.isDebugEnabled()) logger.debug(cacheKey + " 加入到缓存中...");  
        // cache method result  
        //下面方法执行后，将cacheKey与数据集连起来，cacheKey是用来标识这个element的标志，我们可以有多个element(各自是来自不同的数据访问方法而形成的)，区分它们就是用cacheKey，  
		Element element = new Element(cacheKey, (Serializable) result);//这里的新生成后的element，含有cacheKey，还在element创建时间，访问时间，还有命令次数等cache的属性，我觉得它就像是一个小cache一样，下次要不要更新它就要看它的这些属性来决定。  
        cache.put(element);//放入cache中  
	}

	private String getCacheKey(String targetName, String methodName,
			Object[] arguments) {
		StringBuffer sb = new StringBuffer();
		sb.append(targetName).append(".").append(methodName);
		if ((arguments != null) && (arguments.length != 0)) {
			for (int i = 0; i < arguments.length; i++) {
				sb.append(".").append(arguments[i]);
			}
		}
		return sb.toString();
	}
}
