package cn.service.common.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;

public class Sha256Util {

	/***
     *  利用Apache的工具类实现SHA-256加密返回String
     * @param str 
     * @return
     */
    public static String getStrSHA256Str(String str){
        MessageDigest messageDigest;
        String encdeStr = "";
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
            byte[] hash = messageDigest.digest(str.getBytes("UTF-8"));
            encdeStr = Hex.encodeHexString(hash);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return encdeStr;
    }
    
    /***
     *  利用Apache的工具类实现SHA-256加密返回字节数组
     * @param str
     * @return
     */
    public static byte[] getByteSHA256Str(String str){
        MessageDigest messageDigest;
        byte[] hash=null;
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
            hash = messageDigest.digest(str.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return hash;
    }
    
    /***
     *  利用Apache的工具类实现SHA-256加密返回字节数组参数也是数组
     * @param str
     * @return
     */
	public static byte[] GetByteSHA256Byte(byte[] byteArray) throws Exception {
		MessageDigest sha = null;
		byte[] md5Bytes = null;
		try {
			sha = MessageDigest.getInstance("SHA-256");
			md5Bytes = sha.digest(byteArray);
		} catch (Exception e) {
			System.out.println(e.toString());
			e.printStackTrace();
		}
		return md5Bytes;
	}
    
    public static String Salt(){
	    String val = "";
        Random random = new Random();
        for (int i = 0; i < 20; i++) {
            // 输出字母还是数字
            String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num"; 
            // 字符串
            if ("char".equalsIgnoreCase(charOrNum)) {
                // 取得大写字母还是小写字母
                int choice = random.nextInt(2) % 2 == 0 ? 65 : 97; 
                val += (char) (choice + random.nextInt(26));
            } else if ("num".equalsIgnoreCase(charOrNum)) { // 数字
                val += String.valueOf(random.nextInt(10));
            }
        }
        return val;
    }
    
    /**
	 * 两个byte数组合并
	 * @author fanxd
	 * @param data1
	 * @param data2
	 * @return
	 */
	public static byte[] byteAdd(byte[] data1, byte[] data2) {
		byte[] data3 = new byte[data1.length + data2.length];
		System.arraycopy(data1, 0, data3, 0, data1.length);
		System.arraycopy(data2, 0, data3, data1.length, data2.length);
		return data3;
	}
    
    /**
     * 密码加盐加密先sha256然后结果和salt合并再sha256最后base64
     * @param password
     * @param salt
     * @return
     */
	public static String encipher(String password, String salt) {
		String salted = password + "{" + salt + "}";
		byte[] digest = null;
		int iterations = 5000;
		try {
			digest = getByteSHA256Str(salted);
		} catch (Exception e) {
			e.printStackTrace();
		}
		for (int i = 1; i < iterations; i++) {
			digest = byteAdd(digest, salted.getBytes());
			try {
				digest = GetByteSHA256Byte(digest);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return Base64.encodeBase64String(digest);
	}
}
