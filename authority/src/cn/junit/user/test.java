package cn.junit.user;


import java.text.ParseException;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import cn.symdata.common.exception.DatabaseException;
import cn.symdata.common.utils.RemoteContent;
import cn.symdata.web.user.UserController;

import com.google.common.collect.Maps;

public class test {

	@Autowired
	private UserController userCon;
	
	
	@Test
	public void testLogin() throws DatabaseException, ParseException {
		String contentUrl = "http://localhost:8090/zeus/re/login";
		Map<String, String> map = Maps.newHashMap();
		map.put("mobile", "15010894023");
		map.put("loginPwd", "1234");
		String data = RemoteContent.sendPostByParams(map, contentUrl);
		System.out.println("data=" + data.toString());
	}
}
