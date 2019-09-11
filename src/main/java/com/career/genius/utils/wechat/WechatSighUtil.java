package com.career.genius.utils.wechat;

import com.career.genius.utils.signcode.MapKeyComparator;
import net.sf.json.JSONObject;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * 请描述该类
 *
 * @author Marker
 * @time 2019-09-10 21:27
 * @discription
 **/
public class WechatSighUtil {

    /**
     * 使用 Map按key进行排序
     *
     * @param map
     * @return
     */
    public static String sortMapByKey(Map<String, Object> map) throws Exception {
        if (map == null || map.isEmpty()) {
            return null;
        }
        StringBuffer signParam = new StringBuffer();
        Map<String, Object> sortMap = new TreeMap<String, Object>(
                new MapKeyComparator());
        sortMap.putAll(map);

        for (Map.Entry<String, Object> entry : sortMap.entrySet()) {
            signParam.append(entry.getKey() + entry.getValue());
        }
        return shaEncode(signParam.toString());
    }

    /**
     * @Author Marker
     * @Date SHA1 加密算法
     * @Discription
     **/
    static String shaEncode(String inStr) throws Exception {
        MessageDigest sha = null;
        try {
            sha = MessageDigest.getInstance("SHA");
        } catch (Exception e) {
            System.out.println(e.toString());
            e.printStackTrace();
            return "";
        }

        byte[] byteArray = inStr.getBytes("UTF-8");
        byte[] md5Bytes = sha.digest(byteArray);
        StringBuffer hexValue = new StringBuffer();
        for (int i = 0; i < md5Bytes.length; i++) {
            int val = ((int) md5Bytes[i]) & 0xff;
            if (val < 16) {
                hexValue.append("0");
            }
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();
    }

}
