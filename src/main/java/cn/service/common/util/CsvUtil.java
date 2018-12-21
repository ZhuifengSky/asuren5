package cn.service.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import com.csvreader.CsvReader;
import com.csvreader.CsvWriter;

public class CsvUtil {

	/**
	 * 读取CSV文件
	 */
	public static List<String[]> readeCsv(InputStream is) {
		try {
			ArrayList<String[]> csvList = new ArrayList<String[]>(); // 用来保存数据
			CsvReader reader = new CsvReader(is, ',', Charset.forName("SJIS"));
			reader.readHeaders(); // 跳过表头 如果需要表头的话，不要写这句。
			while (reader.readRecord()) { // 逐行读入除表头的数据
				csvList.add(reader.getValues());
			}
			reader.close();
			return csvList;
			/*for (int row = 0; row < csvList.size(); row++) {
				String cell = csvList.get(row)[0]; // 取得第row行第0列的数据
				System.out.println(cell);
			}*/
		} catch (Exception ex) {
			System.out.println(ex);
		}
		return null;
	}

	/**
	 * 写入CSV文件
	 */
	public static void writeCsv(OutputStream os) {
		try {
			CsvWriter wr = new CsvWriter(os, ',',Charset.forName("SJIS"));
			String[] contents = { "aaaaa", "bbbbb", "cccccc", "ddddddddd" };
			wr.writeRecord(contents, true);;
			wr.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public static void main(String[] args) {
		File files= new File("D://3.0/test.csv");
		try {
			InputStream is = new FileInputStream(files);
			 List<String[]> csvList = readeCsv(is);
			 for (int row = 0; row < csvList.size(); row++) {
					String cell = csvList.get(row)[0]; // 取得第row行第0列的数据
					System.out.println(cell);
			 }
			OutputStream os = new FileOutputStream(files);
			writeCsv(os);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}