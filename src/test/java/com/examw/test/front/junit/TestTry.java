package com.examw.test.front.junit;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.junit.Test;

import com.examw.test.front.support.DigestClientUtil;
import com.examw.test.front.support.JSONUtil;

/**
 * 
 * @author fengwei.
 * @since 2015年1月13日 下午2:30:05.
 */
public class TestTry {
	@Test
	public void testTry(){
		try{
			final char[] abcd = {'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z',
					'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};
			final int[] num = {1,2,3,4,5,6,7,8,9,0};
			StringBuffer buf = new StringBuffer();
			Random r1 = new Random(51);
			for(int i=0;i<8;i++)
			{
				buf.append(abcd[r1.nextInt(52)]).append(num[r1.nextInt(10)]);
			}
			buf.append(" ");
			for(int i=0;i<8;i++)
			{
				buf.append(abcd[r1.nextInt(52)]).append(num[r1.nextInt(10)]);
			}
			System.out.println(buf.toString());
			Map<String, String> headers = new HashMap<String, String>();
			headers.put("Content-type","application/json;charset=UTF-8");
			String some = JSONUtil.ObjectToJson(new Some());
			String callback = DigestClientUtil.sendDigestRequest("digest", "d4i9g7e5s19t20",headers, "POST", "http://localhost:8080/examw-test/api/register/register", some);
			System.out.println(callback);
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
class Some implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String code,machine,userId,productId;
	private Integer terminalCode;
	/**
	 * 获取 
	 * @return terminalId
	 * 
	 */
	public Integer getTerminalCode() {
		return terminalCode;
	}
	/**
	 * 设置 
	 * @param terminalId
	 * 
	 */
	public void setTerminalCode(Integer terminalId) {
		this.terminalCode = terminalId;
	}
	public Some() {
		// TODO Auto-generated constructor stub
		code = "1500 0871 9087 1012";
		machine="000000000000000000";
		userId = "fec3c54e-caaf-49ab-b302-f68a67737b67";
		productId = "60a4eb98-8c95-42d7-9178-d053a17f9e87";
		terminalCode=4;
	}
	/**
	 * 获取 
	 * @return code
	 * 
	 */
	public String getCode() {
		return code;
	}
	/**
	 * 设置 
	 * @param code
	 * 
	 */
	public void setCode(String code) {
		this.code = code;
	}
	/**
	 * 获取 
	 * @return machine
	 * 
	 */
	public String getMachine() {
		return machine;
	}
	/**
	 * 设置 
	 * @param machine
	 * 
	 */
	public void setMachine(String machine) {
		this.machine = machine;
	}
	/**
	 * 获取 
	 * @return userId
	 * 
	 */
	public String getUserId() {
		return userId;
	}
	/**
	 * 设置 
	 * @param userId
	 * 
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}
	/**
	 * 获取 
	 * @return productId
	 * 
	 */
	public String getProductId() {
		return productId;
	}
	/**
	 * 设置 
	 * @param productId
	 * 
	 */
	public void setProductId(String productId) {
		this.productId = productId;
	}
	
	
}
