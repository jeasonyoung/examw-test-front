package com.examw.test.front.service;

import com.examw.test.front.model.user.User;

/**
 * 用户服务接口
 * @author fengwei.
 * @since 2014年9月28日 上午9:58:16.
 */
public interface IUserService {
	/**
	 * 登录验证用户
	 * @param user	用户名,密码
	 * @return 用户详细信息
	 */
	User verifyUser(String userStr)throws Exception;
	/**
	 * 获取md5密钥
	 * @return
	 */
	String getMd5Key();
}
