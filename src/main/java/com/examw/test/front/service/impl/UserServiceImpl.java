package com.examw.test.front.service.impl;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

import com.examw.model.Json;
import com.examw.test.front.model.user.FrontUserInfo;
import com.examw.test.front.model.user.User;
import com.examw.test.front.service.IUserService;
import com.examw.test.front.support.HttpUtil;

/**
 * 用户服务接口实现类
 * @author fengwei.
 * @since 2014年9月28日 上午9:58:27.
 */
public class UserServiceImpl implements IUserService{
	private static final Logger logger = Logger.getLogger(UserServiceImpl.class);
	private String api_user_verify_url;
	private String md5Key;
	
	/**
	 * 设置 用户验证地址
	 * @param api_user_verify_url
	 * 
	 */
	public void setApi_user_verify_url(String api_user_verify_url) {
		this.api_user_verify_url = api_user_verify_url;
	}

	/**
	 * 获取 md5加密字串
	 * @return md5Key
	 * 
	 */
	public String getMd5Key() {
		return md5Key;
	}

	/**
	 * 设置 md5加密字串
	 * @param md5Key
	 * 
	 */
	public void setMd5Key(String md5Key) {
		this.md5Key = md5Key;
	}
	/*
	 * 构造用户,并且赋值后端ID
	 * @see com.examw.test.front.service.IUserService#verifyUser(java.lang.String)
	 * userStr like "fw121fw42$462144$2$0$普通会员$10$$$"
	 */
	@Override
	public User verifyUser(String userStr) throws Exception {
		if(logger.isDebugEnabled()) logger.debug(String.format("获取到的Users值为:", userStr));
		if(StringUtils.isEmpty(userStr)) return null;
		//fw121fw42$462144$2$0$普通会员$10$$$
		String[] arr = userStr.split("[$]");
		User user = new User();
		user.setId(arr[1]);
		user.setUsername(arr[0]);
		user.setCoin(Integer.valueOf(arr[2]));
		user.setProductUserId(this.getProductUserId(arr[1], arr[0]));
		return user;
	}
	/**
	 * 远程获取后端产品用户ID
	 * @param code
	 * @param name
	 * @return
	 * @throws Exception
	 */
	private String getProductUserId(final String code,final String name)throws Exception{
		if(logger.isDebugEnabled()) logger.debug(String.format("获取用户[%1$s][%2$s]的后端产品用户ID",code,name));
		Json json = HttpUtil.upload(api_user_verify_url, new FrontUserInfo(){
			private static final long serialVersionUID = 1L;
			@Override
			public String getCode() {return code;}
			public String getName() {return name;}
		});
		if(json.isSuccess()){
			return (String) json.getData();
		}
		return null;
	}
}
