package cn.service.common.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * 表单验证工具类
 * @Author maodelong
 * @Time 2016-11-14 10:54:46
 */
public class FormValidateUtil {

	/** 验证中文真实姓名
	 * @param truename */
	public static boolean checkTruename(String truename) {
		if (truename == null || truename.trim().equals("")) return false;
		String check = "[\u4E00-\u9FA5]{2,5}(?:·[\u4E00-\u9FA5]{2,5})*";
		Pattern regex = Pattern.compile(check);
		Matcher matcher = regex.matcher(truename);
		boolean flag = matcher.matches();
		return flag;
	}
	
	/** 验证用户名
	 * @param username */
	public static boolean checkUsername(String name) {
		if (name == null || name.trim().equals("")) return false;
		String check = "^[0-9a-zA-Z_]+$";
		Pattern regex = Pattern.compile(check);
		Matcher matcher = regex.matcher(name);
		boolean flag = matcher.matches();
		return flag;
	}

	/** 验证邮箱
	 * @param email */
	public static boolean checkEmail(String email) {
		if (email == null || email.trim().equals("")) return false;
		String check = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
		Pattern regex = Pattern.compile(check);
		Matcher matcher = regex.matcher(email);
		boolean flag = matcher.matches();
		return flag;
	}

	/** 验证手机号码
	 * @param mobiles */
	public static boolean checkMobile(String mobile) {
		if (mobile == null || mobile.trim().equals("")) return false;
		Pattern regex = Pattern.compile("^(((13[0-9])|(15([0-9]))|(18[0-9])|(17[0-9]))\\d{8})$");
		Matcher matcher = regex.matcher(mobile);
		boolean flag = matcher.matches();
		return flag;
	}

	/** 验证身份证
	 * @param idCard */
	public static boolean checkIdCard(String idcard) {
		if (idcard == null || idcard.trim().equals("")) return false;
		Pattern isCard = Pattern.compile("(\\d{14}[0-9a-zA-Z])|(\\d{17}[0-9a-zA-Z])");
		Matcher matcher = isCard.matcher(idcard);
		if (matcher.matches()) {
			Pattern birthDatePattern = Pattern.compile("\\d{6}(\\d{4})(\\d{2})(\\d{2}).*");// 身份证上的前6位以及出生年月日
			Matcher birthDateMather = birthDatePattern.matcher(idcard);
			if (birthDateMather.find()) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	public static void main(String[] args) {
		System.out.println(checkEmail("14_8@qw.df"));
		System.out.println(checkMobile("010-01215017"));
		System.out.println(checkMobile("17701215017"));
		System.out.println(checkIdCard("142202198406132875"));
		System.out.println(checkTruename("阿沛·阿旺晋美"));
	}
	
}
