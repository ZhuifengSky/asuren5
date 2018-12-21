package cn.service.common.util;

import java.text.DateFormat;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GsonUtil {

	// private static GsonBuilder gsonBuilder = new GsonBuilder();
	private static Gson gson_underscores = new Gson();
	private static Gson gson = new Gson();

	private static Map<Integer, Gson> usGsonWithStratMap = new HashMap<Integer, Gson>();
	private static Map<Integer, Gson> gsonWithStratMap = new HashMap<Integer, Gson>();
	static {
		gson_underscores = new GsonBuilder()
				.setFieldNamingPolicy(
						FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
				.registerTypeAdapter(java.util.Date.class,
						new DateSerializerUtils())
				.registerTypeAdapter(java.util.Date.class,
						new DateDeserializerUtils())						
				.setDateFormat(DateFormat.LONG).create();
		gson = new GsonBuilder()
				.registerTypeAdapter(java.util.Date.class,
						new DateSerializerUtils())
				.registerTypeAdapter(java.util.Date.class,
						new DateDeserializerUtils())				
				.setDateFormat(DateFormat.LONG).create();
	}
	/**
	 * �»��߷��
	 * @desc
	 * @author lynn
	 * @date ����11:57:33
	 * @param maxField
	 * @return
	 * @return 
	 * @throws
	*
	 */
	public static Gson getUSFieldFilter(final int maxField){
		if(maxField <= 0){
			return gson_underscores;
		}
		Gson gf = usGsonWithStratMap.get(maxField);
		if(null == gf){
			synchronized (usGsonWithStratMap) {
				gf = usGsonWithStratMap.get(maxField);
				if(null == gf){
					gf = new GsonBuilder()
					.setFieldNamingPolicy(
							FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
					.registerTypeAdapter(java.util.Date.class,
							new DateSerializerUtils())
					.registerTypeAdapter(java.util.Date.class,
							new DateDeserializerUtils())		
					.setExclusionStrategies(new HqExclusionStrategy(maxField))
					.setDateFormat(DateFormat.LONG).create();
					usGsonWithStratMap.put(maxField, gf);
				}
			}
			
		}
		return gf;
	}
	/**
	 * ���»��߷��
	 * @desc
	 * @author lynn
	 * @date ����11:57:33
	 * @param maxField
	 * @return
	 * @return 
	 * @throws
	*
	 */
	public static Gson getFieldFilter(final int maxField){
		if(maxField <= 0){
			return gson;
		}
		Gson gf = gsonWithStratMap.get(maxField);
		if(null == gf){
			synchronized (gsonWithStratMap) {
				gf = gsonWithStratMap.get(maxField);
				if(null == gf){
					gf = new GsonBuilder()
					.registerTypeAdapter(java.util.Date.class,
							new DateSerializerUtils())
					.registerTypeAdapter(java.util.Date.class,
							new DateDeserializerUtils())		
					.setExclusionStrategies(new HqExclusionStrategy(maxField))
					.setDateFormat(DateFormat.LONG).create();
					gsonWithStratMap.put(maxField, gf);
				}
			}
			
		}
		return gf;
	}
	/**
	 * �»��߷��
	 * */
	public static Gson getGson() {
		return gson_underscores;
	}
	
	/**
	 * �շ���
	 * */
	public static Gson getGenericGson() {
		return gson;
	}

	/**
	 * 6���»���
	 * 7���շ�
	 * */
	public static Gson getGson(int appId) {
		if(appId == 7){
		return gson;
		}else{
			return gson_underscores;
		}
	}
	
	public static String toJson(Object src, int appid) {
		if (appid == 6) { //php
			return gson_underscores.toJson(src);
		} else { // non php
			return gson.toJson(src);
		}
	}
	/**
	 * ֻ��ʽ��û��HqFiledAnnotation��ǩ����HqFiledAnnotation��ǩlevel<=maxField���ֶ�
	 * @param src
	 * @param appId
	 * @param maxField
	 * @return
	 * @return 
	 * @throws
	*
	 */
	public static String toJson(final Object src, int appId, int maxField){
		if (appId == 6) { //php
			return getUSFieldFilter(maxField).toJson(src);
		} else { // non php
			return getFieldFilter(maxField).toJson(src);
		}
	}
	
	public static String toJson(Object src, int appid, ExclusionStrategy exStra) {
		if (appid == 6) { //php
		
			return gson_underscores.toJson(src);
		} else { // non php
			return gson.toJson(src);
		}
	}

	public static String toJson(Object src) {
		return gson_underscores.toJson(src);
	}

	public static void main(String[] args) {
		System.out.println(GsonUtil.toJson(3, 0));
	}
}
