package com.examw.test.front.junit;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.Calendar;

import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;

import com.examw.model.Json;
import com.examw.test.front.support.DateUtil;
import com.examw.test.front.support.TaoBaoMD5;

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
		String ss = "sdfsdf*1111*3333";
		System.out.println(ss.replaceAll("[*]", ""));
		String sr = "111,1222,333";
		System.out.println(sr.split(",").length);
		String md5key = "4q3i07f12u5i8R1nU";
		String users = "fw121fw41$265384$0$0$普通会员$10$$$";
		System.out.println(TaoBaoMD5.sign(users, md5key, "gbk"));
		String str = "fw121fw42$462144$2$0$普通会员$10$$$";
		System.out.println(Arrays.toString(str.split("[$]")));
		String ssss = "2871046%5B-%5DC%5B-%5D1%5B%7C%5D2870231%5B-%5DA%5B-%5D1%5B%7C%5D2868493%5B-%5D%3D%3D%3D%3D%3D%3D%3D%3D%3D%3D%3D@@@@@@@@%23%23%23%23%23%23%23%23%23%23%23%23%23%23%7C%7C%7C%7C%7C%7C%7C%26%26%26%26%26%26%26%26%26%26%26%u4E0D%u77E5%u9053%u7B54%u6848dddddddddddd%5B-%5D7";
		String ns=new String(ssss.getBytes(),"iso8859-1");
		String text2 = "742c32a0-0082-4bb7-a098-aba293fa826e#sdfsdfsdf--dfsdfsf=-----%3D%3D%3D%3D%3D%3D%23%23%23%23%23%40%40%40%40%40%40%24%24%24%24%24%25%25%25%25%25%25%E4%B8%AD%E6%96%87%E5%93%88%E5%93%88%E5%93%88%E5%93%88&";
		System.out.println(ns);
		System.out.println(URLDecoder.decode(text2,"utf-8").split("&").length);
		System.out.println(URLEncoder.encode("-----=========#######@@@@@@@@我不知道啊","gbk"));
		System.out.println(URLEncoder.encode("我不知道啊", "unicode"));
		Calendar calendar = Calendar.getInstance();
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)-7, 0, 0, 0);
		System.out.println(calendar.getTime());
		System.out.println(DateUtil.parse("2014-10-24").compareTo(calendar.getTime()));
		BigDecimal score = new BigDecimal(1);
		System.out.println(score.divide(BigDecimal.TEN).doubleValue());
	}
}
