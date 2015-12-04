package cn.symdata.common;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

import org.apache.commons.codec.binary.Base64;
/**
 *@Copyright:Copyright (c) 2012-2015
 *@Company:symdata
 *@Title:
 *@Description:压缩、解压长字符串的工具类
 *@Author:zhangnan#symdata
 *@Since:2015年12月4日  下午2:12:50
 *@Version:1.0
 */
public class CompressionUtils {
	/**
	 *@param longString
	 *@return
	 *@throws IOException
	 *@Description:压缩长字符串
	 *@Author:zhangnan#symdata
	 *@Since:2015年12月4日  下午2:12:25
	 *@Version:1.0
	 */
	public static String compress(String longString) throws IOException {
		byte[] data = longString.getBytes();
		Deflater deflater = new Deflater();
		deflater.setInput(data);
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream(
				data.length);
		deflater.finish();
		byte[] buffer = new byte[1024];
		while (!deflater.finished()) {
			int count = deflater.deflate(buffer);
			outputStream.write(buffer, 0, count);
		}
		outputStream.close();
		byte[] output = outputStream.toByteArray();
		return new String(Base64.encodeBase64(output));
	}
	/**
	 *@param shortString
	 *@return
	 *@throws IOException
	 *@throws DataFormatException
	 *@Description:解压字符串
	 *@Author:zhangnan#symdata
	 *@Since:2015年12月4日  下午2:12:09
	 *@Version:1.0
	 */
	public static String decompress(String shortString) throws IOException,
			DataFormatException {
		byte[] data = Base64.decodeBase64(shortString);
		Inflater inflater = new Inflater();
		inflater.setInput(data);
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream(
				data.length);
		byte[] buffer = new byte[1024];
		while (!inflater.finished()) {
			int count = inflater.inflate(buffer);
			outputStream.write(buffer, 0, count);
		}
		outputStream.close();
		byte[] output = outputStream.toByteArray();
		return new String(output);
	}
	public static void main(String[] args) throws Exception {
		String from = "http://220.249.15.194:16176/account/notify/payAsync.jhtml?bssCode=01&uId=8dfc59c728576b25&aId=22899fddfdfaff3e&pcCode=001&rType=0&flag=0&sn=2015120432320&pMode=0&bCode=0102&cCode=1000&bName=招商银行股份有限公司上海联洋支行&bCard=6214830103268081";
		String result = compress(from);
		System.out.println(result);
		String out = decompress(result);
		System.out.println(out);
	}
}