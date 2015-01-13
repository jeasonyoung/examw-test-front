package com.examw.test.front.junit;

import org.junit.Test;

/**
 * 
 * @author fengwei.
 * @since 2015年1月13日 下午2:30:05.
 */
public class TestTry {
	@Test
	public void testTry(){
		try{
			int a = 0;
			if(a == 0)
			{
				throw new Exception("a == 0");
			}
		}catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
	}
}
