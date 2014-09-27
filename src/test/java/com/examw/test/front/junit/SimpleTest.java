package com.examw.test.front.junit;

import org.junit.Test;

/**
 * 
 * @author fengwei.
 * @since 2014年9月27日 上午10:04:08.
 */
public class SimpleTest {
	@Test
	public void testStr(){
		String s = "/examw-test-front/library/paper";
		System.out.println(s.matches("\\w*/library/\\w*"));
	}
}
