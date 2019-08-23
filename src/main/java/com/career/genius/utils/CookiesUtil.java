package com.career.genius.utils;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

public class CookiesUtil {

    private static Logger logger = LoggerFactory.getLogger(CookiesUtil.class);
	
	
	/**
     * 添加cookie
     * @param name cookie的key
     * @param value cookie的value
     * @param domain domain
     * @param  path path 
     * @param maxage  最长存活时间 单位为秒
     * @param response
     */
    public static void addCookie(String name ,String value,String domain,
            int maxage,String path, HttpServletResponse response){
        Cookie cookie = new Cookie(name,value);
        if(domain!=null){
            cookie.setDomain(domain);
        }
        cookie.setMaxAge(maxage);
        cookie.setPath(path);
        response.addCookie(cookie);
    }
     
    /**
     * 往根下面存一个cookie
     * * @param name cookie的key
     * @param value cookie的value
     * @param domain domain
     * @param maxage  最长存活时间 单位为秒
     * @param response
     */
    public static void addCookie(String name ,String value,String domain,
            int maxage, HttpServletResponse response){
        addCookie(name, value, domain, maxage, "/" , response);
    }

	/**
	 * 删除cookie
	 * 
	 */
	public static void removeCookie( HttpServletRequest request,HttpServletResponse response,String name ,String domain) {
		Cookie[] cookies = request.getCookies();
		if( cookies != null && cookies.length > 0 ) {
			for( Cookie cookie : cookies ) {
                logger.info("cookie:{}", JSON.toJSON(cookie));
                String cookieName = cookie.getName();
                if (cookieName.equals(name)){
                    logger.info("cookie name :{} name exists.", name);
                }
                Cookie co = new Cookie( cookieName, null );
                co.setMaxAge( 0 );
                co.setPath("/");
                co.setDomain( domain );
                response.addCookie( co );
			}
		}
	}

	/**
	 * 根据名字获取cookie的值
	 * 
	 * @param request
	 * @param name cookie名字
	 * @return
	 */
	public static String getValueByCookieName( HttpServletRequest request, String name ) {
		Cookie cookie = getCookieByName( request, name );
		return cookie == null ? null : cookie.getValue();
	}

	/**
	 * 根据名字获取cookie
	 * 
	 * @param request
	 * @param name cookie名字
	 * @return
	 */
	public static Cookie getCookieByName( HttpServletRequest request, String name ) {
		Map<String, Cookie> cookieMap = readCookieMap( request );
		if( cookieMap.containsKey( name ) ) {
			Cookie cookie = (Cookie)cookieMap.get( name );
			return cookie;
		}
		else {
			return null;
		}
	}

	/**
	 * 将cookie封装到Map里面
	 * 
	 * @param request
	 * @return
	 */
	private static Map<String, Cookie> readCookieMap( HttpServletRequest request ) {
		Map<String, Cookie> cookieMap = new HashMap<String, Cookie>();
		Cookie[] cookies = request.getCookies();
		if( null != cookies ) {
			for( Cookie cookie : cookies ) {
				cookieMap.put( cookie.getName(), cookie );
			}
		}
		return cookieMap;
	}

}
