package com.rhb.sas.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {
	public static boolean isExists(String path){
		File file = new File(path);
		return file.exists();
	}
	
	public static void writeTextFile(String path, String content, boolean append) {
		try {
			File file = new File(path);

			if (file.exists() == false) { // 如果文本文件不存在则创建它
				file.createNewFile();
				file = new File(path); // 重新实例化
				FileWriter filewriter = new FileWriter(file, append);
				BufferedWriter bufwriter = new BufferedWriter(filewriter);
				filewriter.write(content);
				filewriter.flush();
			} else {
				System.out.println(path + " has already exist!");
			}

		} catch (Exception d) {
			d.printStackTrace();
		}
	}

	public static String readTextFile(String path) throws IOException {
		InputStream is = new FileInputStream(path);
		StringBuffer buffer = new StringBuffer();
		String line; // 用来保存每行读取的内容
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		line = reader.readLine(); // 读取第一行
		while (line != null) { // 如果 line 为空说明读完了
			buffer.append(line); // 将读到的内容添加到 buffer 中
			// buffer.append("\n"); // 添加换行符
			line = reader.readLine(); // 读取下一行
		}
		return buffer.toString();
	}

	public static List<File> getFiles(String path, String suffix,boolean isdepth){
		List<File> files = new ArrayList();
		getListFiles(files,path,suffix,isdepth);
		return files;
	}
	
	private static void getListFiles(List list, String path, String suffix,boolean isdepth) {
		File dir = new File(path);
		listFile(list, dir, suffix, isdepth);
	}

	private static void listFile(List list, File dir, String suffix,	boolean isdepth) {

		// 是目录，同时需要遍历子目录
		if (dir.isDirectory() && isdepth == true) {
			File[] t = dir.listFiles();
			for (int i = 0; i < t.length; i++) {
				listFile(list, t[i], suffix, isdepth);
			}
		} else {
			String filePath = dir.getAbsolutePath();
			if (suffix != null) {
				int begIndex = filePath.lastIndexOf(".");// 最后一个.(即后缀名前面的.)的索引
				String tempsuffix = "";

				if (begIndex != -1) {// 防止是文件但却没有后缀名结束的文件
					tempsuffix = filePath.substring(begIndex + 1, filePath
							.length());
				}

				if (tempsuffix.equals(suffix)) {
					list.add(filePath);
				}
			} else {
				// 后缀名为null则为所有文件
				list.add(filePath);
			}
		}
	}

}
