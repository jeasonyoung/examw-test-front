package com.examw.test.front.junit;

import java.io.IOException;
import java.net.URLEncoder;

import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;

import com.examw.model.Json;
import com.examw.test.front.support.HttpUtil;
import com.examw.utils.MD5Util;

/**
 * 
 * @author fengwei.
 * @since 2014年9月27日 上午10:04:08.
 */
public class SimpleTest {
	@Test
	public void testStr() throws IOException{
//		String s = "/examw-test-front/library/paper";
//		System.out.println(s.matches("\\w*/library/\\w*"));
//		String url = "http://test.examw.com/user/c.asp";
//		String xml = HttpUtil.httpRequest(url, "GET", null, "gbk");
//		System.out.println(xml);
//		String url2 = "http://sc.100xuexi.com/GetBookInfo.ashx?act=GetBookInfo&BookId=40805&PlatNum=1";
//		String xml2 = HttpUtil.httpRequest(url2, "GET", null, "utf-8");
//		System.out.println(xml2);
//		System.out.println(URLEncoder.encode(xml2,"GBK"));
//		//String Md5Key = "4q3i07f12u5i8R1nU";
//		String source = "fw121fw42$462144$2$0$%C6%D5%CD%A8%BB%E1%D4%B1$10$$$";
//		System.out.println(MD5Util.MD5(source));
//		Integer count = 1;
//		add(count);
//		System.out.println(count);
		ObjectMapper mapper = new ObjectMapper();
		String s = (mapper.writeValueAsString(null));
		Json json = mapper.readValue(s, Json.class);
		System.out.println(json);
	}
	private void add(Integer count){
		count = 12;
	}
}
