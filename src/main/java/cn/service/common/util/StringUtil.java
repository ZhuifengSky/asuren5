package cn.service.common.util;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
/**
 * 字符串相关工具类
 * @author maodl
 */
public class StringUtil {

	/** 生成UUID(去掉"-"符号)
	 * @return String */
    public static String getUUid() {
        String s = UUID.randomUUID().toString();
        return s.substring(0,8)+s.substring(9,13)+s.substring(14,18)+s.substring(19,23)+s.substring(24);
    }
    
    /** 校验字符串是否为空
	 * @param parm
	 * @return boolean 字符串为null或为空时，返回true */
	public static boolean isEmpty(String str) {
		boolean flag = false;
		if (str == null || str.trim().equals("")) {
			flag = true;
		}
		return flag;
	}
	
	/** 校验数组是否为空
	 * @param parm
	 * @return boolean 字符串为null或为空时，返回true */
	public static boolean isEmpty(List<?> str) {
		boolean flag = false;
		if (str == null || str.size() == 0) {
			flag = true;
		}
		return flag;
	}

    /** 转化为全大写
     * @param str
     * @return */
    public static String toUpperCase(String str) {
        return str.toUpperCase();
    }
    
    /** 校验字符串是否在数组中 (转换为list, 进行判断)
     * @param str
     * @param strArray
     * @return */
    public static boolean contains(String str, String[] strArray) {
        List<String> tempList = Arrays.asList(strArray);
        if (tempList.contains(str.toUpperCase())) {
            return true;
        }
        return false;
    }

    /** 消除重复存入数组  */
    public static String[] splitTo(String to) throws Exception {
		String[] toArray = to.split(",");
		Set<String> set = new LinkedHashSet<String>(Arrays.asList(toArray));
		return set.toArray(new String[set.size()]);
	}
	
	/** 消除重复后按逗号合并 **/
    public static String addToArray(String[] toArray)throws Exception {
		StringBuffer buff = new StringBuffer();
		if(null!=toArray&&toArray.length>0){
			for(int i=0;i<toArray.length;i++){
				if(i==toArray.length-1){
					buff.append(toArray[i]);
				}else{
					buff.append(toArray[i]).append(",");
				}
			}
		}
		return buff.toString();
	}
    
    
   /** @Title: getRootlUrl 
    * @Description: 获得项目web目录
    * @param @param request
    * @param @return  
    * @return String   */
//    public static String getRootlUrl(HttpServletRequest request) {
//        StringBuffer url = new StringBuffer();
//        String scheme = request.getScheme();
//        int port = request.getServerPort();
//        if (port < 0)
//            port = 80; // Work around java.net.URL bug
//        url.append(scheme);
//        url.append("://");
//        url.append(request.getServerName());
//        if ((scheme.equals("http") && (port != 80)) || (scheme.equals("https") && (port != 443))) {
//            url.append(':');
//            url.append(port);
//        }
//        url.append("/newpafs/");
//        //        url.append(request.getRequestURI());
//        //        
//        //        String queryString = request.getQueryString();
//        //        
//        //        if(queryString != null)
//        //               url.append('?')
//        //                .append(queryString);
//
//        return url.toString();
//    }

}
