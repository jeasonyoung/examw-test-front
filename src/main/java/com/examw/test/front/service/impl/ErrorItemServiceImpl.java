package com.examw.test.front.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

import com.examw.model.DataGrid;
import com.examw.test.front.model.library.FrontItemInfo;
import com.examw.test.front.model.product.FrontProductInfo;
import com.examw.test.front.service.IErrorItemService;
import com.examw.test.front.support.HttpUtil;
import com.examw.test.front.support.JSONUtil;
import com.examw.test.front.support.MethodCacheHelper;

/**
 * 
 * @author fengwei.
 * @since 2014年10月10日 下午2:51:33.
 */
public class ErrorItemServiceImpl implements IErrorItemService {
	private static final Logger logger = Logger.getLogger(ErrorItemServiceImpl.class);
	private String api_error_items_url;
	private MethodCacheHelper cacheHelper;
	/**
	 * 设置 获取错题集合数据接口
	 * @param api_error_items_url
	 * 
	 */
	public void setApi_error_items_url(String api_error_items_url) {
		this.api_error_items_url = api_error_items_url;
	}
	
	/**
	 * 设置 缓存帮助类
	 * @param cacheHelper
	 * 
	 */
	public void setCacheHelper(MethodCacheHelper cacheHelper) {
		this.cacheHelper = cacheHelper;
	}
	
	@SuppressWarnings("unchecked")
	public List<FrontItemInfo> loadErrorItemList(String productId,String userId) throws IOException {
		if(logger.isDebugEnabled()) logger.debug("加载模拟考场试卷列表...");
		if(StringUtils.isEmpty(productId))
		return null;
		String url = String.format(this.api_error_items_url,productId,userId);
		String xml = HttpUtil.httpRequest(url,"GET",null,"utf-8");
		if(!StringUtils.isEmpty(xml)){
			return JSONUtil.JsonToCollection(xml, List.class, FrontItemInfo.class);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public DataGrid<FrontItemInfo> dataGrid(String productId,
			FrontItemInfo info, String userId) throws IOException {
		if(logger.isDebugEnabled()) logger.debug("加载试卷分页列表信息...");
		Integer page = info.getPage()==null?1:info.getPage();
		Integer rows = info.getRows()==null?10:info.getRows();
		List<FrontItemInfo> list = (List<FrontItemInfo>) this.cacheHelper.getCache(FrontItemInfo.class.getName(), this.getClass().getName()+"loadErrorItemList", new Object[]{info.getExamId(),userId,productId});
		if(list == null){
			list = this.loadErrorItemList(productId,userId);
			if(list!=null)
				this.cacheHelper.putCache(FrontProductInfo.class.getName(), this.getClass().getName()+"loadPaperList", new Object[]{info.getExamId(),userId,productId}, list);
		}
		DataGrid<FrontItemInfo> datagrid = new DataGrid<FrontItemInfo>();
		List<FrontItemInfo> result = new ArrayList<FrontItemInfo>();
		for(FrontItemInfo item : list){
			boolean flag = true;
			if(flag && !StringUtils.isEmpty(info.getSubjectId())){
				flag = item.getSubjectId().equalsIgnoreCase(info.getSubjectId());
			}
			if(flag){
				result.add(item);
			}
		}
		int total = result.size();
		if(total > 0){
			datagrid.setTotal((long) total);
			Integer totalPage = total%rows==0?total/rows:(total/rows+1);
			page = page > totalPage?totalPage:page;
			if(list.size() <= rows)
			{
				datagrid.setRows(result);
			}else
				datagrid.setRows(result.subList((page-1)*rows, page*rows>total?total:page*rows));
		}
		return datagrid;
	}
	
}
