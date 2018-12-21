package cn.service.common.util;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

import javax.imageio.ImageIO;
/**
 * 图片验证码(png格式)工具类
 * @Author maodelong
 * @Time 2016-11-14 10:54:46
 */
public class CaptchaPNG {
	private Random random = new Random();
	// 定义验证码字符.去除了O和I等容易混淆的字母
	/*private char alpha[] = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'G', 'K', 'M', 'N', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 
		'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'm', 'n', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 
		'2', '3', '4', '5', '6', '7', '8', '9' };*/
	private char alpha[] = {'1','2', '3', '4', '5', '6', '7', '8', '9' };
	//public static final char ALPHA[]= {'1','2','3','4','5','6','7','8','9'};
	private int width = 80; // 验证码显示跨度
	private int height = 35; // 验证码显示高度
	private int len = 4; // 验证码随机字符长度
	private Font font = new Font("Verdana", Font.ITALIC | Font.BOLD, 20); // 字体
	//private String chars = null; // 随机字符串
	//private char[] shu;
	
	public CaptchaPNG() {}

	public CaptchaPNG(int len) {
		this.len = len;
	}

	public CaptchaPNG(int width, int height, int len) {
		this.width = width;
		this.height = height;
		this.len = len;
	}

	/** 生成验证码 
	 * @throws IOException */
	public String out(OutputStream out) throws IOException {
		return graphicsImage(out);
	}

	/** 画随机码图
	 * @param strs 文本
	 * @param out 输出流
	 * @return 
	 * @throws IOException */
	private String graphicsImage(OutputStream out) throws IOException {
		//boolean ok = false;
		String code = "";
//		try {
			char[] strs = new char[len];
			for (int i = 0; i < len; i++) {
				strs[i] = alpha[random.nextInt(alpha.length)];
				code += strs[i];
			}
			
			BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			Graphics2D g = (Graphics2D) bi.getGraphics();
			AlphaComposite ac3;
			Color color;
			int len = strs.length;
			g.setColor(Color.WHITE);
			g.fillRect(0, 0, width, height);
			// 随机画干扰的蛋蛋
			for (int i = 0; i < 5; i++) {
				color = new Color(random.nextInt(100), random.nextInt(100), random.nextInt(100)); //获得随机颜色(0-255随机)
				g.setColor(color);
				g.drawOval(random.nextInt(width), random.nextInt(height), 5 + random.nextInt(10), 5 + random.nextInt(10));// 画蛋蛋，有蛋的生活才精彩
				color = null;
			}
			g.setFont(font);
			int h = height - ((height - font.getSize()) >> 1), w = width / len, size = w - font.getSize() + 1;
			/* 画字符串 */
			for (int i = 0; i < len; i++) {
				ac3 = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.7f);// 指定透明度
				g.setComposite(ac3);
				color = new Color(20 + random.nextInt(110), 20 + random.nextInt(110), 20 + random.nextInt(110));// 对每个字符都用随机颜色
				g.setColor(color);
				g.drawString(strs[i] + "", (width - (len - i) * w) + size, h - 4);
				color = null;
				ac3 = null;
			}
			ImageIO.write(bi, "png", out);
//			out.flush();
//		} catch (IOException e) {
//			throw new IOException(e.getMessage());
//		} finally {
//			if (out != null) {
//				try {
//					out.flush();
//					out.close();
//				} catch (IOException ex) {}
//			}
//		}
		return code;
	}
	
}
