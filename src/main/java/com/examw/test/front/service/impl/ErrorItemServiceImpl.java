package com.examw.test.front.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.util.StringUtils;

import com.examw.model.DataGrid;
import com.examw.test.front.model.Constant;
import com.examw.test.front.model.library.StructureItemInfo;
import com.examw.test.front.model.record.UserItemRecordInfo;
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
	private ObjectMapper mapper;
	public ErrorItemServiceImpl() {
		this.mapper = new ObjectMapper();
	}
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
	public List<UserItemRecordInfo> loadErrorItemList(String subjectId,String userId) throws IOException {
		if(logger.isDebugEnabled()) logger.debug(String.format("加载科目[%1$s]下所有错题...",subjectId));
		if(StringUtils.isEmpty(userId))
		return null;
		String url = String.format(this.api_error_items_url,userId);
		String data = null;
		if(!StringUtils.isEmpty(subjectId)){
			data = "subjectId="+subjectId;
		}
		String xml = HttpUtil.httpRequest(url,"GET",data,"utf-8");
		if(!StringUtils.isEmpty(xml)){
			return JSONUtil.JsonToCollection(xml, List.class, UserItemRecordInfo.class);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public DataGrid<StructureItemInfo> dataGrid(String productId,StructureItemInfo info, String userId) throws Exception {
		if(logger.isDebugEnabled()) logger.debug("加载错题分页信息列表...");
		Integer page = info.getPage()==null?1:info.getPage();
		Integer rows = info.getRows()==null?10:info.getRows();
		List<StructureItemInfo> list = (List<StructureItemInfo>) this.cacheHelper.getCache(StructureItemInfo.class.getName(), this.getClass().getName(), new Object[]{info.getSubjectId(),userId,productId});
		if(list == null){
			list = this.changeModel(this.loadErrorItemList(info.getSubjectId(),userId));
			if(list!=null)
				this.cacheHelper.putCache(StructureItemInfo.class.getName(), this.getClass().getName(), new Object[]{info.getSubjectId(),userId,productId}, list);
		}
		DataGrid<StructureItemInfo> datagrid = new DataGrid<StructureItemInfo>();
		List<StructureItemInfo> result = new ArrayList<StructureItemInfo>();
		if(list == null){
			datagrid.setTotal(0L);
			datagrid.setRows(result);
			return datagrid;
		}
		for(StructureItemInfo item : list){
			if(logger.isDebugEnabled()) logger.debug(this.mapper.writeValueAsString(item));
			boolean flag = true;
			if(flag && !StringUtils.isEmpty(info.getSubjectId()) && item.getSubjectId()!=null){
				flag = (info.getSubjectId().contains(item.getSubjectId()));
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
	//模型转化
	private List<StructureItemInfo> changeModel(List<UserItemRecordInfo> errorItemList) throws JsonParseException, JsonMappingException, IOException {
		if(errorItemList == null || errorItemList.size() ==0)
		return null;
		 List<StructureItemInfo> result = new ArrayList<StructureItemInfo>();
		 for(UserItemRecordInfo info:errorItemList){
			 if(info == null)continue;
			 StructureItemInfo data = new StructureItemInfo();
			 data = this.mapper.readValue(info.getItemContent(), StructureItemInfo.class);
			 if(StringUtils.isEmpty(info.getAnswer()))
			 {
				 if(info.getItemId().contains("#")){
					if(data.getType().equals(Constant.TYPE_SHARE_TITLE)){
						 setShareTitleUserAnswer(data,info.getItemId(),info.getAnswer());
					}else if(data.getType().equals(Constant.TYPE_SHARE_ANSWER)){
						setShareAnswerUserAnswer(data,info.getItemId(),info.getAnswer());
					}
				 }else{
					 data.setUserAnswer(info.getAnswer());
				 }
			 }
			 result.add(data);
		 }
		 return result;
	}
	//设置共享题干题的用户答案
	private void setShareTitleUserAnswer(StructureItemInfo data, String itemId,
			String answer) {
		Set<StructureItemInfo> children = data.getChildren();
		for(StructureItemInfo child:children){
			if((data.getId()+"#"+child.getId()).equals(itemId)){
				child.setUserAnswer(answer);
				break;
			}
		}
	}
	//设置共享答案题的用户答案
	private void setShareAnswerUserAnswer(StructureItemInfo data,
			String itemId, String answer) {
		TreeSet<StructureItemInfo> set = new TreeSet<StructureItemInfo>();
		set.addAll(data.getChildren());
		Set<StructureItemInfo> children = set.last().getChildren();	//子题目
		for(StructureItemInfo child:children){
			if((data.getId()+"#"+child.getId()).equals(itemId)){
				child.setUserAnswer(answer);
				break;
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> loadItemDetail(String productId,String subjectId, String userId,String itemId)throws Exception {
		List<StructureItemInfo> list = (List<StructureItemInfo>) this.cacheHelper.getCache(StructureItemInfo.class.getName(), this.getClass().getName(), new Object[]{subjectId,userId,productId});
		if(list == null){
			list = this.changeModel(this.loadErrorItemList(subjectId,userId));
			if(list!=null)
				this.cacheHelper.putCache(StructureItemInfo.class.getName(), this.getClass().getName(), new Object[]{subjectId,userId,productId}, list);
		}
		int index = 0;
		StructureItemInfo data = null;
		for(int i=0;i<list.size();i++){
			StructureItemInfo info = list.get(i);
			if(info.getId().equals(itemId))
			{
				index = i;
				data = info;
			}
		}
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("ITEM", data);
		if(index != 0)
		{
			map.put("LAST_ITEM_ID",list.get(index-1).getId());
		}
		if(index != list.size()-1){
			map.put("NEXT_ITEM_ID", list.get(index+1).getId());
		}
		return map;
	}
}
