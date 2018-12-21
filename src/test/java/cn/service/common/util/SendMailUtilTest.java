package cn.service.common.util;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class SendMailUtilTest {

	@Test
	public void senMail() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("username", "嘿嘿嘿");
		String templatePath = "/mailtemplate/regSayHI.ftl";
		SendMailUtil.sendFtlMail("843820873@qq.com", "sendemail test!",true, templatePath, map);
	}

	public static void main(String[] args) {
		System.out.print("我是中午");
	}
}
