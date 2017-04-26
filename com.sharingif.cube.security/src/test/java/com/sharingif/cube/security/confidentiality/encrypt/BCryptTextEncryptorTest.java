package com.sharingif.cube.security.confidentiality.encrypt;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


/**
 * TODO description
 * 2015年8月30日 下午8:05:03
 * @author Joly
 * @version v1.0
 * @since v1.0
 */
@RunWith(SpringJUnit4ClassRunner.class) 
@ContextConfiguration(locations="/com/sharingif/cube/security/confidentiality/encrypt/SpringContext.xml") 
public class BCryptTextEncryptorTest {
	
	@Resource
	private BCryptTextEncryptor bCryptTextEncryptor;
	
	@Test
	public void testBCryptTextEncryptor(){
		System.out.println("===>"+bCryptTextEncryptor.encrypt("000000"));
	}

}
