package com.vinbet.mobile.timer.sport;

import java.io.ByteArrayInputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.log.Log;
import org.nutz.log.Logs;

import com.vinbet.mobile.sys.AppsStatusUtil;
import com.vinbet.mobile.sys.PropertiesLoader;
import com.vinbet.mobile.sys.sport.SportFBCache;
import com.vinbet.mobile.util.Constants;
import com.vinbet.mobile.util.GzipUtil;
import com.vinbet.mobile.util.HttpClientUtil;
import com.vinbet.mobile.util.MD5Util;

/**
 * Created by yenn on 15/3/29.
 */
@IocBean
public class FootballRatioDataTask implements Runnable{

    @Inject
    private PropertiesLoader propertiesLoader;

    private Log log = Logs.getLog(FootballRatioDataTask.class);


    @Override
    public void run() {
        ConcurrentHashMap<String, Double[]> rdMap = new ConcurrentHashMap<String, Double[]>();
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser parser = factory.newSAXParser();
            //滚球赔率
            HashMap<String, String> param = new HashMap<String, String>();
            param.put("s", "1");
            param.put("t", "1");
            param.put("v", "-1");
            param.put(Constants.TOKEN_SPORT, MD5Util.md5Hex(propertiesLoader.getTrim("vinbet.token.sport")));
            String xml = GzipUtil.unGzipString(HttpClientUtil.doGetString(propertiesLoader.getTrim("vinbet.sport.url") + "/core/apiData.getRatioData.do", param, false));
            if (xml != null && !xml.trim().equals("")) {
                ConcurrentHashMap<String, Double[]> map = new ConcurrentHashMap<String, Double[]>();
                parser.parse(new ByteArrayInputStream(xml.getBytes("UTF-8")), new FootballRatioDataHandle(map));
                for (Map.Entry<String, Double[]> entry : map.entrySet()) {
                    rdMap.put(entry.getKey(), entry.getValue());
                }
            }
            //今日赔率
            param.put("t", "2");
            xml = GzipUtil.unGzipString(HttpClientUtil.doGetString(propertiesLoader.getTrim("vinbet.sport.url") + "/core/apiData.getRatioData.do", param, false));
            if (xml != null && !xml.trim().equals("")) {
                ConcurrentHashMap<String, Double[]> map = new ConcurrentHashMap<String, Double[]>();
                parser.parse(new ByteArrayInputStream(xml.getBytes("UTF-8")), new FootballRatioDataHandle(map));
                for (Map.Entry<String, Double[]> entry : map.entrySet()) {
                    rdMap.put(entry.getKey(), entry.getValue());
                }
            }
            //早餐赔率
            param.put("t", "3");
            xml = GzipUtil.unGzipString(HttpClientUtil.doGetString(propertiesLoader.getTrim("vinbet.sport.url") + "/core/apiData.getRatioData.do", param, false));
            if (xml != null && !xml.trim().equals("")) {
                ConcurrentHashMap<String, Double[]> map = new ConcurrentHashMap<String, Double[]>();
                parser.parse(new ByteArrayInputStream(xml.getBytes("UTF-8")), new FootballRatioDataHandle(map));
                for (Map.Entry<String, Double[]> entry : map.entrySet()) {
                    rdMap.put(entry.getKey(), entry.getValue());
                }
            }
            SportFBCache.rdMap = rdMap;
            AppsStatusUtil.setSportStatus(true);
            Constants.heartbeat_lasttime_sport = System.currentTimeMillis();
        } catch (Exception e) {
            log.error(e.fillInStackTrace());
        }
    }
}
