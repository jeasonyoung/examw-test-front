package com.examw.test.front.junit;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;

import com.examw.model.Json;
import com.examw.test.front.support.DateUtil;
import com.examw.test.front.support.TaoBaoMD5;
import com.examw.utils.MD5Util;

/**
 * 
 * @author fengwei.
 * @since 2014年9月27日 上午10:04:08.
 */
public class SimpleTest {
	@Test
	public void testStr() throws IOException {
		// String s = "/examw-test-front/library/paper";
		// System.out.println(s.matches("\\w*/library/\\w*"));
		// String url = "http://test.examw.com/user/c.asp";
		// String xml = HttpUtil.httpRequest(url, "GET", null, "gbk");
		// System.out.println(xml);
		// String url2 =
		// "http://sc.100xuexi.com/GetBookInfo.ashx?act=GetBookInfo&BookId=40805&PlatNum=1";
		// String xml2 = HttpUtil.httpRequest(url2, "GET", null, "utf-8");
		// System.out.println(xml2);
		// System.out.println(URLEncoder.encode(xml2,"GBK"));
		// String Md5Key = "4q3i07f12u5i8R1nU";
		String source = "nima";
		System.out.println(MD5Util.MD5(source));
		System.out.println(this.md5(source));
		// Integer count = 1;
		// add(count);
		// System.out.println(count);
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
		String ns = new String(ssss.getBytes(), "iso8859-1");
		String text2 = "742c32a0-0082-4bb7-a098-aba293fa826e#sdfsdfsdf--dfsdfsf=-----%3D%3D%3D%3D%3D%3D%23%23%23%23%23%40%40%40%40%40%40%24%24%24%24%24%25%25%25%25%25%25%E4%B8%AD%E6%96%87%E5%93%88%E5%93%88%E5%93%88%E5%93%88&";
		System.out.println(ns);
		System.out.println(URLDecoder.decode(text2, "utf-8").split("&").length);
		System.out.println(URLEncoder.encode(
				"-----=========#######@@@@@@@@我不知道啊", "gbk"));
		System.out.println(URLEncoder.encode("我不知道啊", "unicode"));
		Calendar calendar = Calendar.getInstance();
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
				calendar.get(Calendar.DAY_OF_MONTH) - 7, 0, 0, 0);
		System.out.println(calendar.getTime());
		System.out.println(DateUtil.parse("2014-10-24").compareTo(
				calendar.getTime()));
		BigDecimal score = new BigDecimal(100);
		System.out.println(score.divide(BigDecimal.TEN).doubleValue());
		System.out.println(new BigDecimal(1).compareTo(BigDecimal.ZERO));
		System.out.println(UUID.randomUUID().toString());
		System.out
				.println(score.equals(BigDecimal.TEN.multiply(BigDecimal.TEN)));
		BigDecimal actualRuleTotal = new BigDecimal(0);
		BigDecimal ratio = BigDecimal.ONE;
		BigDecimal paperScore = BigDecimal.TEN.multiply(BigDecimal.TEN);
		actualRuleTotal = actualRuleTotal
				.multiply(ratio)
				.multiply(paperScore)
				.divide(new BigDecimal(3).multiply(BigDecimal.ONE), 2,
						RoundingMode.HALF_UP);
		System.out.println(actualRuleTotal);
		System.out.println(BigDecimal.ZERO.equals(new BigDecimal(0.00)));
		//
		String str5 = "<p style=\"font-size:1.3em;font-weight:bold\">No page <img src=\"sdfsdfsdfsdfsdfsdf\"/>with that title exists.</p> ";
		String regex = "<p.*?>(.*?)</p> ";
		String rex = "</?p[^>]*>";
		Pattern p = Pattern.compile("<\\s*img\\s+([^>]*)\\s*>", Pattern.CASE_INSENSITIVE);
		Matcher m_html = p.matcher(str5);  
        System.out.println(m_html.replaceAll("")); // 过滤html标签  
        String body = "page <img src=\"sdfsdfsdfsdfsdfsdf\" width=\"dd\" height=\"dd\"/>with tha";
        body = body.replaceAll("(<img[^>]+src=\")(\\S+)\"",
        		"$1"+"abdcd/"+"$2\" onClick=\"javascript:mWebViewImageListener.onImageClick('$2')\"");
        System.out.println(body);
        
        String s1 = "\"analysis\""+":\"<p><img src=\"/examw-test/upload/preview/42c7f9f5-6088-4bc8-acc0-59162257a775\" title=\"图片47.png\" alt=\"图片47.png\"/></p>\"";
        String s2 = "<p>Image 1:<img width=\"199\" src=\"_image/12/label\" alt=\"\"/> Image 2: <img width=\"199\" src=\"_image/12/label\" alt=\"\"/><img width=\"199\" src=\"_image/12/label\" alt=\"\"/></p>";
        Pattern ps = Pattern.compile("<img[^>]+src\\s*=\\s*['\"]([^'\"]+)['\"][^>]*>");//<img[^<>]*src=[\'\"]([0-9A-Za-z.\\/]*)[\'\"].(.*?)>");
        // json中的匹配 <img[^>]+src\\s*=\\s*[\\\\][\"]([^\"]+)[\\\\][\"][^>]*>
        Matcher m = ps.matcher(s1);
        //System.out.println(m.find());
        System.out.println(m.groupCount());
        while(m.find()){
        	System.out.println("...................");
            System.out.println(m.group().replaceAll("(<img[^>]+src\\s*=\\s*['\"])([^'\"]+)(/[^/'\"]+)(['\"][^>]*>)", "file://$2/aaaaaaa"));
            System.out.println(m.group(1));
        }
        String a = "/examw-test/upload/preview/42c7f9f5-6088-4bc8-acc0-59162257a775";
        System.out.println(a.replaceAll("(\\S+)(/[\\S]+)", "$1/ddddddddddddddddddd"));
	}

	private String md5(String source) {
		try {
			// 采用MD5算法加密
			MessageDigest md5Code = MessageDigest.getInstance("md5");
			byte[] byteArray = md5Code.digest(source.getBytes());
			// 采用Base64算法将加密后的byte[]转换成string
			StringBuffer md5StrBuff = new StringBuffer();

			for (int i = 0; i < byteArray.length; i++) {
				if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)
					md5StrBuff.append("0").append(
							Integer.toHexString(0xFF & byteArray[i]));
				else
					md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
			}

			return md5StrBuff.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
	}
}
