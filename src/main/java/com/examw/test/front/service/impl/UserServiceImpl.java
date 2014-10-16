package com.examw.test.front.service.impl;

import java.io.IOException;

import org.apache.log4j.Logger;

import com.examw.test.front.model.user.User;
import com.examw.test.front.service.IUserService;
import com.examw.test.front.support.HttpUtil;
import com.examw.test.front.support.JSONUtil;

/**
 * 用户服务接口实现类
 * @author fengwei.
 * @since 2014年9月28日 上午9:58:27.
 */
public class UserServiceImpl implements IUserService{
	private static final Logger logger = Logger.getLogger(UserServiceImpl.class);
	private String login_url;
	private String md5Key;
	
	/**
	 * 设置 登录远程地址
	 * @param login_url
	 * 
	 */
	public void setLogin_url(String login_url) {
		this.login_url = login_url;
	}

	@Override
	public User login(User user) throws IOException{
		if(logger.isDebugEnabled()) logger.debug("用户远程登录...");
		String url = String.format(this.login_url, user.getUsername(),user.getPassword());
		String xml = HttpUtil.httpRequest(url, "GET", null);
		return JSONUtil.JsonToObject(xml, User.class);
	}

	/**
	 * 获取 
	 * @return md5Key
	 * 
	 */
	public String getMd5Key() {
		return md5Key;
	}

	/**
	 * 设置 
	 * @param md5Key
	 * 
	 */
	public void setMd5Key(String md5Key) {
		this.md5Key = md5Key;
	}
	
}
