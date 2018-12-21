package cn.service.common.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

/**
 * 
 * Title:   GeneSignUtil.java
 * Description:接口sign生成工具   
 * Company:   www.edu24ol.com
 * @author   pc-zw
 * @date     2016年11月15日下午2:10:54
 * @version  1.0
 */
public class GeneSignUtil {

	private static String key=Global.getConfig("sign.encodeKey");
	
	/**
	 * 生成sign
	 * @param params
	 * @return
	 */
	public static String sign(MultiValueMap<String, String> params){
		List<String> paramsStr = new ArrayList<String>();
		for (String key : params.keySet()) {
			paramsStr.add(key);
		}
		Collections.sort(paramsStr);
		StringBuilder sb = new StringBuilder();
		for (String kk : paramsStr) {
			String value = params.get(kk).get(0);
			sb.append(value);
		}
        String signStr=MD5Utils.getMD5(sb.toString()+key);
		return signStr;		
	}
	
	
	public static void main(String[] args) {
		MultiValueMap<String, String> params= new LinkedMultiValueMap<String, String>();
		params.add("deptId", "23");
		params.add("serviceType", "studyCard");
		params.add("agentId", "12");		
	    String  s = sign(params);
	    System.out.println(s);
	}
}
