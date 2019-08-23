package com.career.genius.utils;

import org.apache.commons.lang3.RandomStringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * 字符串处理工具类
 */
public class StringUtil {


	/**
	 * 验证字符串是否为空
	 *
	 * @param param
	 * @return
	 */
	public static boolean empty( String param ) {
		return param == null || param.trim().length() < 1;
	}

	// 65~90 97~122
	/**
	 * 判断字符是否为字母
	 *
	 * @param c
	 * @return
	 */
	public static boolean isLetter( char c ) {
		if( c >= 65 && c <= 90 ) {
			return true;
		}
		if( c >= 97 && c <= 122 ) {
			return true;
		}
		return false;
	}

	public static String encode( String str, String code ) {
		try {
			return URLEncoder.encode( str, code );
		}
		catch( Exception ex ) {
			ex.fillInStackTrace();
			return "";
		}
	}

	public static String decode( String str, String code ) {
		try {
			return URLDecoder.decode( str, code );
		}
		catch( Exception ex ) {
			ex.fillInStackTrace();
			return "";
		}
	}

	/**
	 * 去除字符串两边空格 为空返回类空格
	 *
	 * @param param
	 * @return
	 */
	public static String nvl( String param ) {
		return param != null ? param.trim() : "";
	}

	/**
	 * 将String类型的数字转换成int类型 为空或非字符串数字时返回所传默认值d
	 *
	 * @param param
	 * @param d
	 * @return
	 */
	public static int parseInt( String param, int d ) {
		int i = d;
		try {
			i = Integer.parseInt( param );
		} catch( Exception e ) {
		}
		return i;
	}

	/**
	 * 编码转换
	 *
	 * @param s
	 * @return
	 */
	public static String GBToUTF8( String s ) {
		try {
			StringBuffer out = new StringBuffer( "" );
			byte[] bytes = s.getBytes( "unicode" );
			for( int i = 2; i < bytes.length - 1; i += 2 ) {
				out.append( "\\u" );
				String str = Integer.toHexString( bytes[i + 1] & 0xff );
				for( int j = str.length(); j < 2; j++ ) {
					out.append( "0" );
				}
				out.append( str );
				String str1 = Integer.toHexString( bytes[i] & 0xff );
				for( int j = str1.length(); j < 2; j++ ) {
					out.append( "0" );
				}

				out.append( str1 );
			}
			return out.toString();
		}
		catch( UnsupportedEncodingException e ) {
			e.printStackTrace();
			return null;
		}
	}


	/**
	 * 字符串替换
	 *
	 * @param line
	 * @param oldString
	 * @param newString
	 * @return
	 */
	public static final String replace( String line, String oldString, String newString ) {
		if( line == null ) {
			return null;
		}
		int i = 0;
		if( (i = line.indexOf( oldString, i )) >= 0 ) {
			char[] line2 = line.toCharArray();
			char[] newString2 = newString.toCharArray();
			int oLength = oldString.length();
			StringBuffer buf = new StringBuffer( line2.length );
			buf.append( line2, 0, i ).append( newString2 );
			i += oLength;
			int j = i;
			while( (i = line.indexOf( oldString, i )) > 0 ) {
				buf.append( line2, j, i - j ).append( newString2 );
				i += oLength;
				j = i;
			}
			buf.append( line2, j, line2.length - j );
			return buf.toString();
		}
		return line;
	}

	public static final String replaceIgnoreCase( String line, String oldString, String newString ) {
		if( line == null ) {
			return null;
		}
		String lcLine = line.toLowerCase();
		String lcOldString = oldString.toLowerCase();
		int i = 0;
		if( (i = lcLine.indexOf( lcOldString, i )) >= 0 ) {
			char[] line2 = line.toCharArray();
			char[] newString2 = newString.toCharArray();
			int oLength = oldString.length();
			StringBuffer buf = new StringBuffer( line2.length );
			buf.append( line2, 0, i ).append( newString2 );
			i += oLength;
			int j = i;
			while( (i = lcLine.indexOf( lcOldString, i )) > 0 ) {
				buf.append( line2, j, i - j ).append( newString2 );
				i += oLength;
				j = i;
			}
			buf.append( line2, j, line2.length - j );
			return buf.toString();
		}
		return line;
	}

	/**
	 * 标签转义
	 *
	 * @param input
	 * @return
	 */
	public static final String escapeHTMLTags( String input ) {
		// Check if the string is null or zero length -- if so, return
		// what was sent in.
		if( input == null || input.length() == 0 ) {
			return input;
		}
		// Use a StringBuffer in lieu of String concatenation -- it is
		// much more efficient this way.
		StringBuffer buf = new StringBuffer( input.length() );
		char ch = ' ';
		for( int i = 0; i < input.length(); i++ ) {
			ch = input.charAt( i );
			if( ch == '<' ) {
				buf.append( "&lt;" );
			}
			else if( ch == '>' ) {
				buf.append( "&gt;" );
			}
			else {
				buf.append( ch );
			}
		}
		return buf.toString();
	}

	/**
	 * Returns a random String of numbers and letters of the specified length.
	 * The method uses the Random class that is built-in to Java which is
	 * suitable for low to medium grade security uses. This means that the
	 * output is only pseudo random, i.e., each number is mathematically
	 * generated so is not truly random.
	 * <p>
	 *
	 * For every character in the returned String, there is an equal chance that
	 * it will be a letter or number. If a letter, there is an equal chance that
	 * it will be lower or upper case.
	 * <p>
	 *
	 * The specified length must be at least one. If not, the method will return
	 * null.
	 *
	 * @param length the desired length of the random String to return.
	 * @return a random String of numbers and letters of the specified length.
	 */

	private static Random randGen = null;

	private static Object initLock = new Object();

	private static char[] numbersAndLetters = null;

	public static final String randomString( int length ) {
		if( length < 1 ) {
			return null;
		}
		// Init of pseudo random number generator.
		if( randGen == null ) {
			synchronized( initLock ) {
				if( randGen == null ) {
					randGen = new Random();
					// Also initialize the numbersAndLetters array
					numbersAndLetters = ("0123456789abcdefghijklmnopqrstuvwxyz" + "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ")
					        .toCharArray();
				}
			}
		}
		// Create a char buffer to put random letters and numbers in.
		char[] randBuffer = new char[length];
		for( int i = 0; i < randBuffer.length; i++ ) {
			randBuffer[i] = numbersAndLetters[randGen.nextInt( 71 )];
		}
		return new String( randBuffer );
	}

	/**
	 * 固定长度字符串截取
	 *
	 * @param content
	 * @param i
	 * @return
	 */
	public static String getSubstring( String content, int i ) {
		int varsize = 10;
		String strContent = content;
		if( strContent.length() < varsize + 1 ) {
			return strContent;
		}
		else {
			int max = (int)Math.ceil( (double)strContent.length() / varsize );
			if( i < max - 1 ) {
				return strContent.substring( i * varsize, (i + 1) * varsize );
			}
			else {
				return strContent.substring( i * varsize );
			}
		}
	}

	/**
	 * 日期转String
	 *
	 * @param pattern
	 * @return
	 */
	public static String now( String pattern ) {
		return dateToString( new Date(), pattern );
	}

	public static String dateToString( Date date, String pattern ) {
		if( date == null ) {
			return "";
		}
		else {
			SimpleDateFormat sdf = new SimpleDateFormat( pattern );
			return sdf.format( date );
		}
	}

	public static synchronized String getNow() {
		SimpleDateFormat sdf = new SimpleDateFormat( "yyyyMMddHHmmssSSS" );
		return sdf.format( new Date() );
	}


	/**
	 * 获取当前网站根路径
	 *
	 * @param request
	 * @return
	 */
	public static String getBasePath( HttpServletRequest request ) {
		String path = request.getContextPath();
		String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
		return basePath;
	}


	/**
	 * 随机生成6位数字 结算单号有调用
	 *
	 * @return
	 */
	public static String generatorNo() {
		Random rd = new Random();
		int n = 0;
		while( n < 100000 ) {
			n = rd.nextInt( 999999 );
		}
		return String.valueOf( n );
	}

	/**
	 * 字符串转换unicode
	 */
	public static String string2Unicode( String str ) {

		StringBuffer unicode = new StringBuffer();

		for( int i = 0; i < str.length(); i++ ) {

			// 取出每一个字符
			char c = str.charAt( i );

			// 转换为unicode
			unicode.append( "\\u" + Integer.toHexString( c ) );
		}

		return unicode.toString();
	}

	/**
	 * unicode 转字符串
	 */
	public static String unicode2String( String unicode ) {

		StringBuffer string = new StringBuffer();

		String[] hex = unicode.split( "\\\\u" );

		for( int i = 1; i < hex.length; i++ ) {

			// 转换出每一个代码点
			int data = Integer.parseInt( hex[i], 16 );

			// 追加成string
			string.append( (char)data );
		}

		return string.toString();
	}

	/**
	 * 反射机制 将任意java对象转换成Map<String,String>类型
	 * Tips:方法在使用过程中遇到有如下情况：po类中存在final类型的值时，如果想通过此法转换成map
	 * 在另一系统通过反射机制直接把map转换成对象时会报错
	 * ,po类型的final试图被发射改变。（发射也能动态改变fianl值,但如此便违背了设定final的初衷）遇此情况考虑慎用！！！
	 *
	 * @param obj
	 * @author wenhua_Lee
	 * @return
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public static Map<String, String> getMapValue( Object obj ) {
		Map<String, String> map = new HashMap<String, String>();
		// 获取对象中所有的属性
		Field[] fields = obj.getClass().getDeclaredFields();
		if( fields.length > 0 ) {
			for( int i = 0; i < fields.length; i++ ) {
				try {
					// 获取属性名
					String valueName = fields[i].getName();
					// 获得访问权限的boolean值
					boolean isAccessible = fields[i].isAccessible();
//					String type = fields[i].getGenericType().toString();
					// System.out.println( "类型:" + type );
					fields[i].setAccessible( true );
					// 获取当前属性的值
					Object value = fields[i].get( obj );
					if( value != null ) {
						// 时间类型数据的处理 其他若有转换成特定形式数据的可以在此添加类型判断加以格式化后toString
						if( value instanceof Date ) {
							Date data = (Date)value;
							value = new SimpleDateFormat( "yyyy-MM-dd" ).format( data );
						}
						map.put( valueName, value.toString() );
					}
					// 恢复原有的权限访问
					fields[i].setAccessible( isAccessible );
				}
				catch( IllegalArgumentException e ) {
					e.printStackTrace();
				}
				catch( IllegalAccessException e ) {
					e.printStackTrace();
				}

			}
		}
		return map;
	}

	/**
	 * 数组转字符串
	 *
	 * @param ary [1,2,3]
	 * @param prefix 例如:","
	 * @return "1,2,3"
	 */
	public static String arrayToString( String[] ary, String prefix ) {
		if( ary == null || ary.length <= 0 ) {
			return null;
		}
		StringBuilder sb = new StringBuilder();
		for( String string : ary ) {
			sb.append( prefix ).append( string );
		}
		return sb.toString().replaceFirst( prefix, "" );
	}


	/**
	 * 随机生成4位验证码
	 *
	 * @return
	 */
	public static String randomNumber() {
		String s = "0123456789";
		char[] c = s.toCharArray();
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for( int i = 0; i < 4; i++ ) {
			sb.append( c[random.nextInt( c.length )] );
		}
		return sb.toString();
	}


	/**
	 * 判断对象是否为空
	 *
	 * @param str
	 * @return
	 */
	public static boolean isNotEmpty(Object str) {
		boolean flag = true;
		if (str != null && !str.equals("")) {
			if (str.toString().length() > 0) {
				flag = true;
			}
		} else {
			flag = false;
		}
		return flag;
	}

	private static final String ALL_LETTER = "abcdefghijklmnopqrstuvwxyz";
	private static final String ALL_NUMBER = "0123456789";
	private static final int RANDOM_LETTER_NUM = 3;


	public static String getRandomNum(int num) {
		return RandomStringUtils.random(num, ALL_NUMBER);
	} 
	
	/**
	 * 阿拉伯数字转汉字
	 * @param number
	 * @return
	 */
	public static String getNumberToCN(Integer number){
		switch (number) {
			case 0: return "零";
			case 1: return "一";
			case 2: return "二";
			case 3: return "三";
			case 4: return "四";
			case 5: return "五";
			case 6: return "六";
			case 7: return "七";
			case 8: return "八";
			case 9: return "九";
			case 10: return "十"; 
			default: return number+""; 
		} 
	
	}


	/**
	 * 随机生成指定长度字符串
	 * 
	 * @param length 长度
	 * @return
	 */
	private static String createRandomString( int length ) {
		String base = "abcdefghijklmnopqrstuvwxyzQWERTYUIOPASDFGHJKLZXCVBNM0123456789";
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for( int i = 0; i < length; i++ ) {
			int number = random.nextInt( base.length() );
			sb.append( base.charAt( number ) );
		}
		return sb.toString();
	}
	
}
