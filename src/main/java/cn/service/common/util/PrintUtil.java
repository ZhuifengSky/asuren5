package cn.service.common.util;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

/**
 * @author fanxd
 *
 */
public class PrintUtil {
	/**
	 * 使用PrintWriter方式将信息写入
	 * 
	 * @param response
	 * @param sendMessage
	 * @return
	 */

	public static String printJson(HttpServletResponse response, String code, String total, Object info, String desc) {
		try {
			PrintWriter out = response.getWriter();
			JSONObject json = new JSONObject();
			json.put("code", code);
			json.put("total", total);
			json.put("info", info);
			json.put("desc", desc);
			out.write(json.toString());
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String printJson(HttpServletResponse response, String code, Object info) {
		try {
			PrintWriter out = response.getWriter();
			JSONObject json = new JSONObject();
			json.put("code", code);
			json.put("info", info);
			out.write(json.toString());
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
  
	public static String printJson(String code, Object info) {
		JSONObject json = new JSONObject();
		json.put("code", code);
		json.put("info", info);
		return json.toString();
	}

	public static String printJson(String code, String total, Object info) {
		JSONObject json = new JSONObject();
		json.put("code", code);
		json.put("total", total);
		json.put("info", info);
		return json.toString();
	}
 
	public static void main(String args[]) {
		String json = "{\"flag\":true,\"info\":{\"id\":\"d028114de3e5487da415f52b6c7980d0\",\"fileName\":\"image_213.jpg\",\"ext\":\"jpg\",\"originalUrl\":\"http://7xlsg9.com2.z0.glb.qiniucdn.com/2016/0603/original/d028114de3e5487da415f52b6c7980d0.jpg\",\"originalSize\":\"240x240\",\"size\":18076,\"status\":\"success\"}}";
		JSONObject obj = JSONObject.fromObject(json);
		System.out.println(obj.get("flag"));
		System.out.println(obj.get("info"));
		JSONObject info = (JSONObject) obj.get("info");
		System.out.println(info.get("originalUrl"));
		System.out.println(info.get("fileName"));
		System.out.println(info.get("id"));
	}
}
