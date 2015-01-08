package com.examw.test.front.junit;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.parsers.ParserConfigurationException;

import org.junit.Test;
import org.springframework.util.StringUtils;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import sun.misc.BASE64Decoder;

import com.examw.utils.XmlUtil;
import com.thoughtworks.xstream.core.util.Base64Encoder;

/**
 * 
 * @author fengwei.
 * @since 2014年12月24日 上午9:55:45.
 */
public class TestBase64 {

	@Test
	public void test64() throws Exception {
		/**
		 * 这个路径就是你存放    下载下来的文件 的目录路径
		 */
		String path = "C:\\Documents and Settings\\Administrator\\ks100\\files";
		/**
		 * 执行就会在 那个目录下 生成一个out目录,生成xml文件,
		 *  还要写一个解析xml的
		 */
		//readDoc(path);
		//解析 然后写文件
		readAndParse(path);
	}
	public void readAndParse(String path) throws Exception{
		File doc = new File(path);
		if(doc.isDirectory())
		{
			File[] files = doc.listFiles();
			if(files.length == 0) return ;
			for(File f:files)
			{
				if(f.isDirectory()) continue;
				if(f.getName().endsWith(".dat"))
				{
					String xml = Des1.decryptDES(readFile(f), "361A6AA9").replaceAll(">",
							">\r\n");
					Parser.parseAndWriteFile(xml, path);
				}
			}
		}
	}
	public void readDoc(String path) throws Exception {
		File doc = new File(path);
		if(doc.isDirectory())
		{
			File[] files = doc.listFiles();
			if(files.length == 0) return ;
			for(File f:files)
			{
				String xml = Des1.decryptDES(readFile(f), "361A6AA9").replaceAll(">",
						">\r\n");
				writeFile(path+ File.separator + "out",f.getName().substring(0,f.getName().lastIndexOf(".")),xml);
			}
		}
	}

	public void writeFile(String path, String fileName, String str)
			throws Exception {
		if (str == null)
			return;
		if(!new File(path).exists())
		{
			new File(path).mkdir();
		}
		FileWriter fw = new FileWriter(path + File.separator + fileName
				+ ".xml");
		fw.write(str);
		fw.flush();
		fw.close();
	}

	public String readFile(File paramString) throws Exception {
		FileInputStream localFileInputStream = new FileInputStream(paramString);
		byte[] arrayOfByte1 = new byte[1024];
		ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
		while (true) {
			int i = localFileInputStream.read(arrayOfByte1);
			if (i == -1) {
				byte[] arrayOfByte2 = localByteArrayOutputStream.toByteArray();
				localByteArrayOutputStream.close();
				localFileInputStream.close();
				return new String(arrayOfByte2);
			}
			localByteArrayOutputStream.write(arrayOfByte1, 0, i);
		}
	}
}

class Base64 {

	public Base64() {
	}

	private static int decode(char c) {
		if (c >= 'A' && c <= 'Z')
			return c - 65;
		if (c >= 'a' && c <= 'z')
			return 26 + (c - 97);
		if (c >= '0' && c <= '9')
			return 26 + (26 + (c - 48));
		switch (c) {
		default:
			throw new RuntimeException((new StringBuilder("unexpected code: "))
					.append(c).toString());

		case 43: // '+'
			return 62;

		case 47: // '/'
			return 63;

		case 61: // '='
			return 0;
		}
	}

	private static void decode(String s, OutputStream outputstream)
			throws IOException {
		int i;
		int j;
		i = 0;
		j = s.length();
		for (; i < j && s.charAt(i) <= ' '; i++) {
			if (i != j) {
				int k;
				k = (decode(s.charAt(i)) << 18)
						+ (decode(s.charAt(i + 1)) << 12)
						+ (decode(s.charAt(i + 2)) << 6)
						+ decode(s.charAt(i + 3));
				outputstream.write(0xff & k >> 16);
				if (s.charAt(i + 2) != '=') {
					outputstream.write(0xff & k >> 8);
					if (s.charAt(i + 3) != '=') {
						outputstream.write(k & 0xff);
						i += 4;
					}
				}
			}
		}
	}

	public static byte[] decode(String s) {
		ByteArrayOutputStream bytearrayoutputstream = new ByteArrayOutputStream();
		byte abyte0[];
		try {
			decode(s, ((OutputStream) (bytearrayoutputstream)));
		} catch (IOException ioexception) {
			throw new RuntimeException();
		}
		abyte0 = bytearrayoutputstream.toByteArray();
		try {
			bytearrayoutputstream.close();
		} catch (IOException ioexception1) {
			System.err.println((new StringBuilder(
					"Error while decoding BASE64: ")).append(
					ioexception1.toString()).toString());
			return abyte0;
		}
		return abyte0;
	}

	public static String encode(byte abyte0[]) {
		int i = abyte0.length;
		StringBuffer stringbuffer = new StringBuffer((3 * abyte0.length) / 2);
		int j = i - 3;
		int k = 0;
		int l = 0;
		do {
			int i1;
			int j1;
			if (k > j) {
				if (k == -2 + (0 + i)) {
					int l1 = (0xff & abyte0[k]) << 16
							| (0xff & abyte0[k + 1]) << 8;
					stringbuffer.append(legalChars[0x3f & l1 >> 18]);
					stringbuffer.append(legalChars[0x3f & l1 >> 12]);
					stringbuffer.append(legalChars[0x3f & l1 >> 6]);
					stringbuffer.append("=");
				} else if (k == -1 + (0 + i)) {
					int k1 = (0xff & abyte0[k]) << 16;
					stringbuffer.append(legalChars[0x3f & k1 >> 18]);
					stringbuffer.append(legalChars[0x3f & k1 >> 12]);
					stringbuffer.append("==");
				}
				return stringbuffer.toString();
			}
			i1 = (0xff & abyte0[k]) << 16 | (0xff & abyte0[k + 1]) << 8 | 0xff
					& abyte0[k + 2];
			stringbuffer.append(legalChars[0x3f & i1 >> 18]);
			stringbuffer.append(legalChars[0x3f & i1 >> 12]);
			stringbuffer.append(legalChars[0x3f & i1 >> 6]);
			stringbuffer.append(legalChars[i1 & 0x3f]);
			k += 3;
			j1 = l + 1;
			if (l >= 14) {
				j1 = 0;
				stringbuffer.append(" ");
			}
			l = j1;
		} while (true);
	}

	private static final char legalChars[] = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/"
			.toCharArray();

}

class Des1 {
	private static String a = "361A6AA9";
	private static byte[] iv = { 3, 6, 1, 11, 6, 11, 11, 9 };

	public static String decryptDES(String paramString1, String paramString2)
			throws Exception {
		new Base64();
		iv = a.getBytes();
		byte[] arrayOfByte = new BASE64Decoder().decodeBuffer(paramString1);
		IvParameterSpec localIvParameterSpec = new IvParameterSpec(iv);
		SecretKeySpec localSecretKeySpec = new SecretKeySpec(
				paramString2.getBytes(), "DES");
		Cipher localCipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
		localCipher.init(2, localSecretKeySpec, localIvParameterSpec);
		return new String(localCipher.doFinal(arrayOfByte));
	}

	public static String encryptDES(String paramString1, String paramString2)
			throws Exception {
		iv = a.getBytes();
		IvParameterSpec localIvParameterSpec = new IvParameterSpec(iv);
		SecretKeySpec localSecretKeySpec = new SecretKeySpec(
				paramString2.getBytes(), "DES");
		Cipher localCipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
		localCipher.init(1, localSecretKeySpec, localIvParameterSpec);
		return new Base64Encoder().encode(localCipher.doFinal(paramString1
				.getBytes()));
	}
}

class Parser{
	private static final String ANSWERTAG = "【答案】";
	private static final String ANALYSISTAG = "【解析】";
	private static final String[] types = {"单选","多选","不定项","判断","填空","问答"};
	private static final String newLine = "\r";
	
	public static void parseAndWriteFile(String xml,String path) throws Exception{
		if (StringUtils.isEmpty(xml))
			return;
		String paperName = "";
		StringBuffer buf = new StringBuffer();
		Document document = XmlUtil.loadDocument(xml);
		Element root = document.getDocumentElement();
		if(root != null)
		{
			NamedNodeMap attributes = root.getAttributes();
			if(null != attributes)  
		    {  
		        for(int i = 0; i < attributes.getLength(); i++)  
		        {  
		                //获得该元素的每一个属性  
		                Attr attr = (Attr)attributes.item(i); 
		                if(attr.getName().equals("title"))
		                {
		                	paperName = attr.getValue();  
		                	buf.append(paperName).append(newLine);
		                	break;
		                }
		            }  
		    }
			NodeList list = XmlUtil.selectNodes(root, ".//question");
			if (list == null || list.getLength() == 0)
				return ;
			for(String type:types)
			{
				buf.append("大题结构:").append(type).append(newLine); 
				for (int i = 0; i < list.getLength(); i++) {
					Node question = list.item(i);
					String qtype = XmlUtil.getNodeStringValue(question, "./type");
					if(qtype.contains(type))
					{
						//标题
						buf.append(XmlUtil.getNodeStringValue(question, "./qsort")).append(".");
						buf.append(XmlUtil.getNodeStringValue(question, "./title").trim()).append(newLine);
						//选项
						if(type.equals("单选")||type.equals("多选")||type.equals("不定项"))
						{
							Node option = XmlUtil.selectSingleNode(question, "./options");
							if(option != null)
							{
								NodeList options = XmlUtil.selectNodes(option, "./content");
								if (options != null && options.getLength()>0)
								{
									for (int j = 0; j < options.getLength(); j++) {
										Node content = options.item(j);
										buf.append(content.getTextContent()).append(newLine);
									}
								}
							}
						}
						if(type.equals("判断"))
						{
							buf.append("A.正确").append(newLine);
							buf.append("B.错误").append(newLine);
						}
						//答案
						buf.append(ANSWERTAG);
						if(type.equals("判断"))
						{
							String answer = XmlUtil.getNodeStringValue(question, "./answer").trim();
							answer = answer.equals("对")?"A":"B";
							buf.append(answer).append(newLine);
						}else
							buf.append(XmlUtil.getNodeStringValue(question, "./answer").trim()).append(newLine);
						//解析
						buf.append(ANALYSISTAG);
						buf.append(XmlUtil.getNodeStringValue(question, "./parse").replaceAll("\\s", "")).append(newLine);
					}else{
						continue;
					}
					buf.append(newLine);
				}
				buf.append(newLine);
			}
		}
		//写文件
		path = path + File.separator+"file";
		if(!new File(path).exists())
		{
			new File(path).mkdir();
		}
		FileWriter fw = new FileWriter(path + File.separator + paperName + ".txt");
		fw.write(buf.toString());
		fw.flush();
		fw.close();
	}
}


